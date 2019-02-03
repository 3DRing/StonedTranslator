package com.ringov.stonedtrnsltr.storage_module.view;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.ringov.stonedtrnsltr.R;
import com.ringov.stonedtrnsltr.base.implementations.BaseFragment;
import com.ringov.stonedtrnsltr.base.implementations.ContextAdapter;
import com.ringov.stonedtrnsltr.storage_module.interactor.StorageInteractorImpl;
import com.ringov.stonedtrnsltr.storage_module.presenter.StoragePresenter;
import com.ringov.stonedtrnsltr.storage_module.router.StorageRouterImpl;
import com.ringov.stonedtrnsltr.ui_entities.UITranslation;

import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public abstract class StorageFragment extends BaseFragment<StoragePresenter> implements StorageView {

    @BindView(R.id.rv_storage)
    RecyclerView mRvStorage;

    StorageAdapter mAdapter;

    private boolean stonedModeEnabled;

    @Override
    protected StoragePresenter providePresenter() {
        return new StoragePresenter(this, new StorageRouterImpl(new ContextAdapter(getActivity())),
                new StorageInteractorImpl());
    }

    @Override
    protected void initializeViewsBeforeRestoreState() {
        super.initializeViewsBeforeRestoreState();
        initializeRecycler();
    }

    private void initializeRecycler() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRvStorage.setLayoutManager(llm);

        // adding divider for list
        DividerItemDecoration divider = new DividerItemDecoration(mRvStorage.getContext(),
                llm.getOrientation());
        mRvStorage.addItemDecoration(divider);

        mAdapter = new StorageAdapter(new StorageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UITranslation translation) {
                mPresenter.onItemClicked(translation);
            }

            @Override
            public void onFavoriteClick(UITranslation translation, boolean isFavorite) {
                mPresenter.onFavoriteClicked(translation, isFavorite);
            }
        });
        mRvStorage.setAdapter(mAdapter);

        // swipe for deleting works with both sides
        DoubleSideSwipeItemTouchHelper swipeHelper =
                new DoubleSideSwipeItemTouchHelper(mAdapter, (translation, position) -> {
                    mPresenter.onItemsSwiped(translation, position);
                    mAdapter.remove(position);

                    // what strings will be pick depends on mode
                    Snackbar.make(mRvStorage,
                            stonedModeEnabled ? R.string.deleted_from_history_stoned : R.string.deleted_from_history,
                            Snackbar.LENGTH_SHORT)
                            .setAction(stonedModeEnabled ? R.string.restore_item_stoned : R.string.restore_item,
                                    v -> mPresenter.onUndoDeletion())
                            .show();
                });
        swipeHelper.attachToRecyclerView(mRvStorage);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.storage_fragment;
    }

    @Override
    protected void restoreState(Bundle bundle) {
        // no need to save this view, it restores from presenter
    }

    @Override
    protected Bundle saveState(Bundle bundle) {
        // no need to save this view, it restores from presenter
        return bundle;
    }

    private void showHistoryField() {
        mRvStorage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTranslations(List<UITranslation> translations) {
        if (translations.size() != 0) {
            showHistoryField();
            mAdapter.setTranslations(translations);
        }
    }

    @Override
    public void addToStorage(UITranslation transaction) {
        showHistoryField();
        mAdapter.addTransaction(transaction);
        mRvStorage.smoothScrollToPosition(0);
    }

    @Override
    public void itemDeleted() {
        if (mAdapter.getItemCount() == 0) {
            hideHistoryField();
        }
    }

    @Override
    public void returnItemBack(UITranslation translation, int position) {
        showHistoryField();
        mAdapter.insertTranslation(translation, position);
        mRvStorage.smoothScrollToPosition(position);
    }

    private void hideHistoryField() {
        mRvStorage.setVisibility(View.GONE);
    }

    @Override
    public void setStonedMode(boolean enable) {
        stonedModeEnabled = enable;

        mAdapter.setStonedMode(enable);
    }

    private static class DoubleSideSwipeItemTouchHelper {

        ItemTouchHelper itmLeft;
        ItemTouchHelper itmRight;
        private StorageAdapter mAdapter;

        DoubleSideSwipeItemTouchHelper(StorageAdapter adapter, DoubleSideSwipeItemTouchHelper.Callback callback) {
            this.mAdapter = adapter;
            itmLeft = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    UITranslation crtTranslation = mAdapter.getTranslation(viewHolder.getAdapterPosition());
                    callback.onSwiped(crtTranslation, viewHolder.getAdapterPosition());
                }
            });
            itmRight = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    UITranslation crtTranslation = mAdapter.getTranslation(viewHolder.getAdapterPosition());
                    callback.onSwiped(crtTranslation, viewHolder.getAdapterPosition());
                }
            });
        }

        void attachToRecyclerView(RecyclerView rv) {
            itmRight.attachToRecyclerView(rv);
            itmLeft.attachToRecyclerView(rv);
        }

        public interface Callback {
            void onSwiped(UITranslation translation, int position);
        }
    }
}
