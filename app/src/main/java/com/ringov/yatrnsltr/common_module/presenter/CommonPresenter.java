package com.ringov.yatrnsltr.common_module.presenter;

import com.ringov.yatrnsltr.Utils;
import com.ringov.yatrnsltr.base.BasePresenter;
import com.ringov.yatrnsltr.common_module.interactor.CommonInteractor;
import com.ringov.yatrnsltr.common_module.router.CommonRouter;
import com.ringov.yatrnsltr.common_module.view.CommonView;
import com.ringov.yatrnsltr.ui_entities.UILangPair;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class CommonPresenter extends BasePresenter<CommonView, CommonRouter, CommonInteractor> {

    public CommonPresenter(CommonView view, CommonRouter router, CommonInteractor interactor) {
        super(view, router, interactor);

        loadStonedMode();
    }

    @Override
    public void onViewResumed() {
        mSubscription.add(getInteractor().loadLastLangPair()
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::showLanguagePair, this::handleError));
    }

    @Override
    public void onViewPaused() {
        getInteractor().saveLastLangPair();
    }

    /**
     * each view can be switched from one mode to another
     */
    public void onStonedModeChangedClicked() {
        mSubscription.add(getInteractor().changeStonedMode()
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::setStonedMode, this::handleError));
    }

    /**
     * at start of any view we call this method
     * to understand which mode we should apply
     */
    private void loadStonedMode() {
        mSubscription.add(getInteractor().loadStonedMode()
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::setStonedMode, this::handleError));
    }

    public void loadAllLanguages() {
        mSubscription.add(getInteractor().loadAllLanguages()
                .compose(Utils.setRxSchedulers())
                .doOnSubscribe(getView()::showLoading)
                .doOnTerminate(getView()::hideLoading)
                .doOnError(throwable -> {
                    getView().hideLoading();
                })
                .subscribe(getView()::showAllLanguages, this::handleError));
    }

    public void onLangPairChanged(UILangPair langPair) {
        mSubscription.add(getInteractor().changeLangPair(langPair)
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::showLanguagePair, this::handleError));
    }

    public void onLanguagesClicked() {
        getRouter().openChooseLanguageScreen();
    }
}
