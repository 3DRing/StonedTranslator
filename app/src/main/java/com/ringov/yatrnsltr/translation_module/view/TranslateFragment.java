package com.ringov.yatrnsltr.translation_module.view;

import android.widget.TextView;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.BaseFragment;
import com.ringov.yatrnsltr.translation_module.interactor.TranslationInteractorImpl;
import com.ringov.yatrnsltr.translation_module.presenter.TranslationPresenter;
import com.ringov.yatrnsltr.translation_module.router.TranslationRouter;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UITranslation;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public class TranslateFragment extends BaseFragment<TranslationPresenter>
        implements TranslationView, TranslationRouter {

    @BindView(R.id.tv_translation)
    TextView mTvTranslation;

    @OnClick(R.id.btn_translate)
    void onTranslateClick() {
        mPresenter.translateClicked("Корова!");
    }

    @Override
    protected TranslationPresenter providePresenter() {
        return new TranslationPresenter(this, new TranslationInteractorImpl());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.translate_fragment;
    }

    @Override
    public void showTranslation(UITranslation translation) {
        if (translation.getTranslations().size() > 0) {
            mTvTranslation.setText(translation.getTranslations().get(0));
        }
    }
}
