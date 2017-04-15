package com.ringov.yatrnsltr.translation_module.presenter;

import com.ringov.yatrnsltr.Utils;
import com.ringov.yatrnsltr.base.BasePresenter;
import com.ringov.yatrnsltr.translation_module.interactor.TranslationInteractor;
import com.ringov.yatrnsltr.translation_module.router.TranslationRouter;
import com.ringov.yatrnsltr.translation_module.view.TranslationView;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationPresenter extends BasePresenter<TranslationView, TranslationRouter, TranslationInteractor> {

    private boolean moreOptionsShown;

    public TranslationPresenter(TranslationView view, TranslationRouter router, TranslationInteractor interactor) {
        super(view, router, interactor);
    }

    @Override
    public void onViewResumed() {
        getInteractor().loadLastLangPair()
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::showLanguagePair, this::handleError);
    }

    @Override
    public void onViewPaused() {
        getInteractor().saveLastLangPair();
    }

    public void translateClicked(String text) {
        if (text == null || text.equals("")) {
            return;
        }
        getInteractor().translate(text)
                .compose(Utils.setRxSchedulers())
                .doOnSubscribe(getView()::showLoading)
                .doOnTerminate(getView()::hideLoading)
                .doOnTerminate(getView()::hideKeyboard)
                .subscribe(getView()::showTranslation, this::handleError);
    }

    public void moreOptionsClicked() {
        if (moreOptionsShown) {
            getView().hideMoreOptions();
        } else {
            getView().showMoreOptions();
        }
        moreOptionsShown = !moreOptionsShown;
    }

    public void deleteClicked() {
        getView().clearInputField();
    }

    public void swapLangClicked() {
        getInteractor().swapLanguage()
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::showLanguagePair, this::handleError);
    }

    public void onTranslationFooterClicked() {
        getRouter().openYandexTranslatePage();
    }
}
