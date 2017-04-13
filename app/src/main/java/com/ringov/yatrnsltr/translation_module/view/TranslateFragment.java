package com.ringov.yatrnsltr.translation_module.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.BaseFragment;
import com.ringov.yatrnsltr.translation_module.interactor.TranslationInteractorImpl;
import com.ringov.yatrnsltr.translation_module.presenter.TranslationPresenter;
import com.ringov.yatrnsltr.translation_module.router.TranslationRouter;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UILangPair;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UITranslation;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public class TranslateFragment extends BaseFragment<TranslationPresenter>
        implements TranslationView {

    @BindView(R.id.et_input)
    EditText mEtOriginalText;
    @BindView(R.id.rv_output)
    RecyclerView mRvOutput;
    @BindView(R.id.fl_output_field)
    ViewGroup mCvOutputCard;
    @BindView(R.id.inc_more_options)
    ViewGroup mLlMoreOptions;

    @BindView(R.id.tv_source_lang)
    TextView mTvSourceLang;
    @BindView(R.id.tv_target_lang)
    TextView mTvTargetLang;

    @OnClick(R.id.ll_swap_lang)
    void onSwapLangClick() {
        mPresenter.onSwapLangClicked();
    }

    @OnClick(R.id.iv_delete)
    void onDeleteClick() {
        mPresenter.onDeleteClicked();
    }

    @OnClick(R.id.iv_more_options)
    void onMoreOptionsClick() {
        mPresenter.onMoreOptionsClicked();
    }

    TranslationAdapter mAdapter;

    @OnClick(R.id.tv_translate)
    void onTranslateClick() {
        mPresenter.translateClicked(mEtOriginalText.getText().toString());
    }

    @Override
    protected TranslationPresenter providePresenter() {
        return new TranslationPresenter(new TranslationRouter() {
        }, new TranslationInteractorImpl());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onViewResumed();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onViewPaused();
    }

    private void initializeRecycler() {
        mRvOutput.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TranslationAdapter();
        mRvOutput.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.translate_fragment;
    }

    @Override
    public void showTranslation(UITranslation translation) {
        mCvOutputCard.setVisibility(View.VISIBLE);
        mCvOutputCard.requestFocus();
        mAdapter.setTranslation(translation);
        hideKeyboard();
    }

    @Override
    public void showMoreOptions() {
        mLlMoreOptions.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMoreOptions() {
        mLlMoreOptions.setVisibility(View.GONE);
    }

    @Override
    public void clearInputField() {
        mEtOriginalText.setText("");
    }

    @Override
    public void showLanguagePair(UILangPair langPair) {
        mTvSourceLang.setText(langPair.getSourceLang());
        mTvTargetLang.setText(langPair.getTargetLang());
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
