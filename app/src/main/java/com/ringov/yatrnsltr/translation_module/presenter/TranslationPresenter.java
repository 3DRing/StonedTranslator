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
    public TranslationPresenter(TranslationRouter router, TranslationInteractor interactor) {
        super(router, interactor);
    }

    private boolean moreOptionsShown;

    public void translateClicked(String text) {
        getInteractor().translate(text)
                .compose(Utils.setRxSchedulers())
                .doOnSubscribe(getView()::showLoading)
                .doOnTerminate(getView()::hideLoading)
                .subscribe(getView()::showTranslation, this::handleError);
    }

    public void onMoreOptionsClicked() {
        if (moreOptionsShown) {
            getView().hideMoreOptions();
        } else {
            getView().showMoreOptions();
        }
        moreOptionsShown = !moreOptionsShown;
    }
}
