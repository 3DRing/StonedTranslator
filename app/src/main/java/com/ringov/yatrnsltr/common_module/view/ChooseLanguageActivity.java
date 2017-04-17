package com.ringov.yatrnsltr.common_module.view;


import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.BaseActivity;
import com.ringov.yatrnsltr.base.implementations.ContextAdapter;
import com.ringov.yatrnsltr.common_module.interactor.CommonInteractorImpl;
import com.ringov.yatrnsltr.common_module.presenter.CommonPresenter;
import com.ringov.yatrnsltr.common_module.router.CommonRouterImpl;

/**
 * Created by Sergey Koltsov on 18.04.2017.
 */

public class ChooseLanguageActivity extends BaseActivity<CommonPresenter> implements CommonView {
    @Override
    public void setStonedMode(boolean enable) {
        // nothing so far
    }

    @Override
    protected CommonPresenter providePresenter() {
        return new CommonPresenter(this, new CommonRouterImpl(new ContextAdapter(this)), new CommonInteractorImpl());
    }

    @Override
    protected int getLayout() {
        return R.layout.choose_language_layout;
    }
}
