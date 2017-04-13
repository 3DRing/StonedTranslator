package com.ringov.yatrnsltr.translation_module.interactor;

import com.ringov.yatrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.yatrnsltr.data.TranslationRepositoryProvider;
import com.ringov.yatrnsltr.translation_module.entity.LangPairData;
import com.ringov.yatrnsltr.translation_module.entity.TranslationData;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UILangPair;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UITranslation;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationInteractorImpl extends BaseInteractorImpl implements TranslationInteractor {

    private LangPairData crtLangPair;

    public Observable<UITranslation> translate(String text) {
        return TranslationRepositoryProvider.getTranslationRepository()
                .translate(text, crtLangPair)
                .map(this::convertTranslation);
    }

    @Override
    public Observable<UILangPair> swapLanguage() {
        crtLangPair.swap();
        return Observable.just(crtLangPair)
                .map(langPairData -> new UILangPair(langPairData.getSourceLang(), langPairData.getTargetLang()));
    }

    @Override
    public Observable<UILangPair> loadLastLangPair() {
        return TranslationRepositoryProvider.getTranslationRepository().loadLastLangPair()
                .map(langPairData -> {
                    crtLangPair = langPairData;
                    return new UILangPair(crtLangPair.getSourceLang(), crtLangPair.getTargetLang());
                });
    }

    @Override
    public void saveLastLangPair() {
        TranslationRepositoryProvider.getTranslationRepository().saveLastLangPair(crtLangPair);
    }

    private UITranslation convertTranslation(TranslationData translationData) {
        return new UITranslation(translationData);
    }

}
