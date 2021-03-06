package com.ringov.stonedtrnsltr.translation_module.view;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ringov.stonedtrnsltr.R;
import com.ringov.stonedtrnsltr.base.implementations.BaseFragment;
import com.ringov.stonedtrnsltr.base.implementations.ContextAdapter;
import com.ringov.stonedtrnsltr.translation_module.interactor.TranslationInteractorImpl;
import com.ringov.stonedtrnsltr.translation_module.presenter.TranslationPresenter;
import com.ringov.stonedtrnsltr.translation_module.router.TranslationRouterImpl;
import com.ringov.stonedtrnsltr.ui_entities.UILangPair;
import com.ringov.stonedtrnsltr.ui_entities.UITranslation;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public class TranslateFragment extends BaseFragment<TranslationPresenter>
        implements TranslationView,
        TranslateViewCallback {

    private static final long OPEN_KEYBOARD_DELAY = 200;
    @BindView(R.id.et_input)
    EditText mEtOriginalText;
    @BindView(R.id.inc_more_options)
    ViewGroup mLlMoreOptions;
    @BindView(R.id.tv_source_lang)
    TextView mTvSourceLang;
    @BindView(R.id.tv_target_lang)
    TextView mTvTargetLang;

    @BindView(R.id.fl_output_field)
    ViewGroup mFlOutputField;
    @BindView(R.id.tv_original)
    TextView mTvOriginal;
    @BindView(R.id.tv_translation)
    TextView mTvTranslation;
    @BindView(R.id.tv_lang_pair)
    TextView mTvLangPair;

    @BindView(R.id.tv_yandex_badge)
    TextView mYandexBadge;

    @BindView(R.id.pb_loading_stoned)
    ProgressBar mPbLoadingStoned;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    @BindView(R.id.tv_translate)
    TextView mTvTranslate;

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

    @OnClick(R.id.iv_delete)
    void onDeleteClick() {
        mPresenter.deleteClicked();
    }

    @OnClick(R.id.iv_more_options)
    void onMoreOptionsClick() {
        mPresenter.moreOptionsClicked();
    }

    @OnClick(R.id.btn_translate)
    void onTranslateClick() {
        mPresenter.translateClicked(mEtOriginalText.getText().toString());
    }

    @OnLongClick(R.id.fl_output_field)
    boolean onOutputFieldLongClick() {
        mPresenter.onOutputLongClicked(mTvTranslation.getText().toString());
        return true;
    }

    @OnClick(R.id.fl_output_field)
    void onOutputFieldClick() {
        mPresenter.onOutputClicked(mTvTranslation.getText().toString(),
                (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE));
    }

    @Override
    protected TranslationPresenter providePresenter() {
        return new TranslationPresenter(this, new TranslationRouterImpl(new ContextAdapter(getActivity())),
                new TranslationInteractorImpl());
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
    }

    private void showOutputField() {
        mFlOutputField.setVisibility(View.VISIBLE);
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
            mPbLoading.setVisibility(View.INVISIBLE);
        } else {
            mPbLoadingStoned.setVisibility(View.INVISIBLE);
            mPbLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (stonedModeEnabled) {
            mPbLoadingStoned.setVisibility(View.INVISIBLE); // invisible in order not to collapse and expand layout every time
            mPbLoading.setVisibility(View.INVISIBLE);
        } else {
            mPbLoadingStoned.setVisibility(View.INVISIBLE);
            mPbLoading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setStonedMode(boolean enable) {
        stonedModeEnabled = enable;

        mTvTranslate.setText(enable ? R.string.translate_button_text_stoned : R.string.translate_button_text);
        mEtOriginalText.setHint(enable ? R.string.input_hint_text_stoned : R.string.input_hint_text);
        mYandexBadge.setText(enable ? R.string.yandex_badge_text_stoned : R.string.yandex_badge_text);

        // refresh output field
        if (crtTranslation != null) {
            showTranslation(crtTranslation);
        }
    }

    @Override
    public void showTranslationAndInputText(UITranslation translation) {
        showTranslation(translation);
        mEtOriginalText.setText(translation.getOriginalText());
        mEtOriginalText.requestFocus();
    }

    @Override
    public void showCopiedToClipBoard() {
        Toast.makeText(getActivity(), getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestInputFocus() {
        mEtOriginalText.requestFocus();
        mEtOriginalText.postDelayed(() -> {
                    InputMethodManager keyboard =
                            (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.showSoftInput(mEtOriginalText, 0);
                }
                , OPEN_KEYBOARD_DELAY);
    }

    @Override
    public void requestTranslate() {
        onTranslateClick();
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
