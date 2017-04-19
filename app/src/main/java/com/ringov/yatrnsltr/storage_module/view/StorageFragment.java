package com.ringov.yatrnsltr.storage_module.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.BaseFragment;
import com.ringov.yatrnsltr.base.implementations.ContextAdapter;
import com.ringov.yatrnsltr.storage_module.interactor.StorageInteractorImpl;
import com.ringov.yatrnsltr.storage_module.presenter.StoragePresenter;
import com.ringov.yatrnsltr.storage_module.router.StorageRouterImpl;
import com.ringov.yatrnsltr.ui_entities.UITranslation;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public class StorageFragment extends BaseFragment<StoragePresenter> implements StorageView {

    @BindView(R.id.rv_storage)
    RecyclerView mRvStorage;
    @BindView(R.id.storage_container)
    ViewGroup mStorageContainer;
    @BindView(R.id.tv_history_title)
    TextView mTvHistoryTitle;

    StorageAdapter mAdapter;

    private boolean stonedModeEnabled;

    public StorageType getStorageType() {
        return StorageType.HISTORY;
    }

    @Override
    protected StoragePresenter providePresenter() {
        return new StoragePresenter(this, new StorageRouterImpl(new ContextAdapter(getContext())),
                new StorageInteractorImpl());
    }

    @Override
    protected void initializeViewsBeforeRestoreState() {
        super.initializeViewsBeforeRestoreState();
        initializeRecycler();
    }

    private void initializeRecycler() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setReverseLayout(true);
        mRvStorage.setLayoutManager(llm);

        // adding divider for list
        DividerItemDecoration divider = new DividerItemDecoration(mRvStorage.getContext(),
                llm.getOrientation());
        mRvStorage.addItemDecoration(divider);

        mAdapter = new StorageAdapter(translation -> {
            // todo open separate screen with full size text and translation (?)
            // Toast.makeText(getContext(), translation.getOriginalText(), Toast.LENGTH_SHORT).show();
        });
        mRvStorage.setAdapter(mAdapter);

        // swipe for deleting works with both sides
        DoubleSideSwipeItemTouchHelper swipeHelper = new DoubleSideSwipeItemTouchHelper(itemPosition -> {
            mPresenter.onItemsSwiped(itemPosition);
            mAdapter.remove(itemPosition);

            // what strings will be pick depends on mode
            Snackbar.make(mStorageContainer,
                    stonedModeEnabled ? R.string.deleted_from_history_stoned : R.string.deleted_from_history,
                    Snackbar.LENGTH_LONG)
                    .setAction(stonedModeEnabled ? R.string.restore_item_stoned : R.string.restore_item,
                            v -> mPresenter.onUndoDeletion(itemPosition))
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
        mStorageContainer.setVisibility(View.VISIBLE);
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
    }

    private void hideHistoryField() {
        mStorageContainer.setVisibility(View.GONE);
    }

    @Override
    public void setStonedMode(boolean enable) {
        stonedModeEnabled = enable;

        mAdapter.setStonedMode(enable);
        mTvHistoryTitle.setText(enable ? R.string.history_title_stoned : R.string.history_title);
    }

    private static class DoubleSideSwipeItemTouchHelper {

        ItemTouchHelper itmLeft;
        ItemTouchHelper itmRight;

        DoubleSideSwipeItemTouchHelper(DoubleSideSwipeItemTouchHelper.Callback callback) {
            itmLeft = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    callback.onSwiped(viewHolder.getAdapterPosition());
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
                    callback.onSwiped(viewHolder.getAdapterPosition());
                }
            });
        }

        void attachToRecyclerView(RecyclerView rv) {
            itmRight.attachToRecyclerView(rv);
            itmLeft.attachToRecyclerView(rv);
        }

        public interface Callback {
            void onSwiped(int itemPosition);
        }
    }
}
