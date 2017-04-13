package com.ringov.yatrnsltr.translation_module.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
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
    TranslationAdapter mAdapter;
    private UITranslation crtTranslation;

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
    }

    @Override
    protected void initializeViewsBeforeRestoreState() {
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

    @Override
    protected void restoreState(Bundle bundle) {
        if (bundle != null) {
            ViewState state = bundle.getParcelable(ViewState.class.getCanonicalName());
            if (state != null) {
                mEtOriginalText.setText(state.getInputText());
                if (!state.getTranslation().getTranslations().isEmpty()) {
                    showTranslation(state.getTranslation());
                }
            }
        }
    }

    @Override
    protected Bundle saveState(Bundle bundle) {
        ViewState state = new ViewState.Builder()
                .setInputText(mEtOriginalText.getText().toString())
                .setCrtUITranslation(crtTranslation)
                .build();
        bundle.putParcelable(ViewState.class.getCanonicalName(), state);
        return bundle;
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
        crtTranslation = translation;
        mCvOutputCard.setVisibility(View.VISIBLE);
        mCvOutputCard.requestFocus();
        mAdapter.setTranslation(crtTranslation);
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

    private static class ViewState extends BaseViewState {
        public static final Creator<ViewState> CREATOR = new Creator<ViewState>() {
            @Override
            public ViewState createFromParcel(Parcel in) {
                return new ViewState(in);
            }

            @Override
            public ViewState[] newArray(int size) {
                return new ViewState[size];
            }
        };
        private String inputText;
        private UITranslation crtTranslation;

        public ViewState() {

        }

        protected ViewState(Parcel in) {
            inputText = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(inputText);
        }

        public String getInputText() {
            return inputText;
        }

        public UITranslation getTranslation() {
            return crtTranslation;
        }

        public static class Builder extends BaseViewState.Builder<ViewState, ViewState.Builder> {
            public Builder() {
                state = new ViewState();
                state.inputText = "";
                state.crtTranslation = UITranslation.EMPTY;
            }

            public Builder setInputText(String text) {
                state.inputText = text;
                return this;
            }

            public Builder setCrtUITranslation(UITranslation translation) {
                state.crtTranslation = translation != null ? translation : UITranslation.EMPTY;
                return this;
            }
        }
    }
}
