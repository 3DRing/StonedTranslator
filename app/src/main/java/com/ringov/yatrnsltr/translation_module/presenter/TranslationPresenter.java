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
    private boolean loading;

    public TranslationPresenter(TranslationView view, TranslationRouter router, TranslationInteractor interactor) {
        super(view, router, interactor);
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

    public void translateClicked(String text) {
        if (text == null || text.equals("")) {
            return;
        }
        if (loading) {
            return;
        }
        mSubscription.add(getInteractor().translate(text)
                .compose(Utils.setRxSchedulers())
                .doOnSubscribe(() -> loading = true)
                .doOnSubscribe(getView()::showLoading)
                .doOnTerminate(getView()::hideLoading)
                .doOnTerminate(getView()::hideKeyboard)
                .doOnTerminate(() -> loading = false)
                .subscribe(getView()::showTranslation, this::handleError));
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
        mSubscription.add(getInteractor().swapLanguage()
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::showLanguagePair, this::handleError));
    }

    public void onYandexClicked() {
        getRouter().openYandexTranslatePage();
    }
}
