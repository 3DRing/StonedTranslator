package com.ringov.yatrnsltr.storage_module.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    StorageAdapter mAdapter;

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

        mAdapter = new StorageAdapter(new StorageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UITranslation translation) {
                // todo open separate screen with full size text and translation
                Toast.makeText(getContext(), translation.getOriginalText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFooterClick() {

            }
        });
        mRvStorage.setAdapter(mAdapter);

        // swipe for deleting works with both sides
        DoubleSideSwipeItemTouchHelper swipeHelper = new DoubleSideSwipeItemTouchHelper(itemPosition -> {
            mPresenter.onItemsSwiped(itemPosition);
            mAdapter.remove(itemPosition);
        });
        swipeHelper.attachToRecyclerView(mRvStorage);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.storage_fragment;
    }

    @Override
    protected void restoreState(Bundle bundle) {
        // todo restore ViewState
    }

    @Override
    protected Bundle saveState(Bundle bundle) {
        // todo save ViewState
        return bundle;
    }

    private void showHistoryField() {
        mStorageContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showHistory(List<UITranslation> translations) {
        if (translations.size() != 0) {
            showHistoryField();
            mAdapter.setTranslations(translations);
        }
    }

    @Override
    public void addToHistory(UITranslation transaction) {
        if (transaction != null) {
            showHistoryField();
            mAdapter.addTransaction(transaction);
        }
    }

    @Override
    public void itemDeleted() {
        if (mAdapter.getItemCount() == 0) {
            hideHistoryField();
        }
    }

    private void hideHistoryField() {
        mStorageContainer.setVisibility(View.GONE);
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
