package com.ringov.yatrnsltr.translation_module.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.BaseFragment;
import com.ringov.yatrnsltr.base.implementations.ContextAdapter;
import com.ringov.yatrnsltr.custom_views.FavoriteButton;
import com.ringov.yatrnsltr.custom_views.StonedModeButton;
import com.ringov.yatrnsltr.translation_module.interactor.TranslationInteractorImpl;
import com.ringov.yatrnsltr.translation_module.presenter.TranslationPresenter;
import com.ringov.yatrnsltr.translation_module.router.TranslationRouterImpl;
import com.ringov.yatrnsltr.ui_entities.UILangPair;
import com.ringov.yatrnsltr.ui_entities.UITranslation;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public class TranslateFragment extends BaseFragment<TranslationPresenter>
        implements TranslationView {

    @BindView(R.id.et_input)
    EditText mEtOriginalText;
    @BindView(R.id.inc_more_options)
    ViewGroup mLlMoreOptions;
    @BindView(R.id.tv_source_lang)
    TextView mTvSourceLang;
    @BindView(R.id.tv_target_lang)
    TextView mTvTargetLang;
    @BindView(R.id.space_near_output)
    Space mSpace;

    @BindView(R.id.fl_output_field)
    ViewGroup mFlOutputField;
    @BindView(R.id.tv_original)
    TextView mTvOriginal;
    @BindView(R.id.tv_translation)
    TextView mTvTranslation;
    @BindView(R.id.tv_lang_pair)
    TextView mTvLangPair;
    @BindView(R.id.fb_favorite)
    FavoriteButton mFb;
    @BindView(R.id.tmb_changed)
    StonedModeButton mTmbMode;

    @BindView(R.id.tv_translate)
    TextView mBtnTranslate;
    @BindView(R.id.tv_yandex_badge)
    TextView mYandexBedge;

    @BindView(R.id.pb_loading_stoned)
    ProgressBar mPbLoadingStoned;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    private boolean stonedModeEnabled;

    private UITranslation crtTranslation;

    @OnClick(R.id.tv_yandex_badge)
    void onYandexClick() {
        mPresenter.onYandexClicked();
    }

    @OnClick(R.id.ll_swap_lang)
    void onSwapLangClick() {
        mPresenter.swapLangClicked();
    }

    @OnLongClick(R.id.ll_swap_lang)
    boolean onSwapLangLongClick() {
        mPresenter.swapLangLongClicked();
        return true;
    }

    @OnClick(R.id.iv_delete)
    void onDeleteClick() {
        mPresenter.deleteClicked();
    }

    @OnClick(R.id.iv_more_options)
    void onMoreOptionsClick() {
        mPresenter.moreOptionsClicked();
    }

    @OnClick(R.id.tv_translate)
    void onTranslateClick() {
        mPresenter.translateClicked(mEtOriginalText.getText().toString());
    }

    @Override
    protected TranslationPresenter providePresenter() {
        return new TranslationPresenter(this, new TranslationRouterImpl(new ContextAdapter(getContext())),
                new TranslationInteractorImpl());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @Override
    protected int getLayoutRes() {
        return R.layout.translate_fragment;
    }

    @Override
    public void showTranslation(UITranslation translation) {
        crtTranslation = translation;
        showOutputField();

        mTvOriginal.setText(stonedModeEnabled ? translation.getChangedOriginal() : translation.getOriginalText());
        mTvTranslation.setText(stonedModeEnabled ? translation.getChangedTranslations().get(0)
                : translation.getTranslations().get(0));
        UILangPair langPair = translation.getLangPair();
        mTvLangPair.setText(String.format(getString(R.string.lang_pair_item),
                langPair.getSourceLang().getShortName(), langPair.getTargetLang().getShortName()));
        mFb.setChecked(translation.isFavorite());
        mTmbMode.setChecked(translation.isChanged());
    }

    private void showOutputField() {
        mFlOutputField.setVisibility(View.VISIBLE);
        mSpace.setVisibility(View.VISIBLE);
        mFlOutputField.requestFocus();
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
        mTvSourceLang.setText(langPair.getSourceLang().getShortName());
        mTvTargetLang.setText(langPair.getTargetLang().getShortName());
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        // two views for the sake of not using deprecated or not supported methods for getting drawables
        if (stonedModeEnabled) {
            mPbLoadingStoned.setVisibility(View.VISIBLE);
            mPbLoading.setVisibility(View.GONE);
        } else {
            mPbLoadingStoned.setVisibility(View.GONE);
            mPbLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (stonedModeEnabled) {
            mPbLoadingStoned.setVisibility(View.GONE);
            mPbLoading.setVisibility(View.GONE);
        } else {
            mPbLoadingStoned.setVisibility(View.GONE);
            mPbLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void setStonedMode(boolean enable) {
        stonedModeEnabled = enable;

        mBtnTranslate.setText(enable ? R.string.translate_button_text_stoned : R.string.translate_button_text);
        mEtOriginalText.setHint(enable ? R.string.input_hint_text_stoned : R.string.input_hint_text);
        mYandexBedge.setText(enable ? R.string.yandex_badge_text_stoned : R.string.yandex_badge_text);

        // refresh output field
        if (crtTranslation != null) {
            showTranslation(crtTranslation);
        }
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
