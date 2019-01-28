package com.ringov.yatrnsltr.common_module.view;


import android.os.Bundle;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.BaseActivity;
import com.ringov.yatrnsltr.base.implementations.ContextAdapter;
import com.ringov.yatrnsltr.common_module.entities.UILanguage;
import com.ringov.yatrnsltr.common_module.interactor.CommonInteractorImpl;
import com.ringov.yatrnsltr.common_module.presenter.CommonPresenter;
import com.ringov.yatrnsltr.common_module.router.CommonRouterImpl;
import com.ringov.yatrnsltr.ui_entities.UILangPair;

import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

/**
 * Created by Sergey Koltsov on 18.04.2017.
 */

public class ChooseLanguageActivity extends BaseActivity<CommonPresenter> implements CommonView {

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    @BindView(R.id.rv_languages_list)
    RecyclerView mRvLanguageList;

    LanguagesAdapter mAdapter;

    @Override
    public void setStonedMode(boolean enable) {
        // nothing so far
    }

    @Override
    public void showAllLanguages(List<UILanguage> languages) {
        mAdapter.setLanguages(languages);
    }

    @Override
    public void showLanguagePair(UILangPair langPair) {
        mAdapter.setLanguagePair(langPair);
    }

    @Override
    public void requestInputFocus() {
        // not used
        // todo refactor, to avoid this
    }

    @Override
    public void requestTranslate() {
        // not used
        // todo refactor, to avoid this
    }

    @Override
    protected CommonPresenter providePresenter() {
        return new CommonPresenter(this, new CommonRouterImpl(new ContextAdapter(this)), new CommonInteractorImpl());
    }

    @Override
    protected int getLayout() {
        return R.layout.choose_language_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeRecyclerView();
        mPresenter.loadAllLanguages();
        // not allowed to refresh manually so far
        mSrlRefresh.setOnRefreshListener(() -> mSrlRefresh.setRefreshing(false));
    }

    private void initializeRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);

        mRvLanguageList.setLayoutManager(llm);
        DividerItemDecoration divider = new DividerItemDecoration(mRvLanguageList.getContext(),
                llm.getOrientation());
        mRvLanguageList.addItemDecoration(divider);

        mAdapter = new LanguagesAdapter(mPresenter::onLangPairChanged);
        mRvLanguageList.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mSrlRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mSrlRefresh.setRefreshing(false);
    }
}
