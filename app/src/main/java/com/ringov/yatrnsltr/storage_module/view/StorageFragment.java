package com.ringov.yatrnsltr.storage_module.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    public void showHistory(List<UITranslation> translations) {
        if (translations.size() != 0) {
            mAdapter.setTranslations(translations);
        }
    }

    @Override
    public void addToHistory(UITranslation transaction) {
        if (transaction != null) {
            mAdapter.addTransaction(transaction);
        }
    }
}
