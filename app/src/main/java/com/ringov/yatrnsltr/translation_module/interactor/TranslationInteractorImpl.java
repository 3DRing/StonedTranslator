package com.ringov.yatrnsltr.translation_module.interactor;

import com.ringov.yatrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.yatrnsltr.data.common_repo.CommonRepositoryProvider;
import com.ringov.yatrnsltr.data.stoned_service.StonedConvertingService;
import com.ringov.yatrnsltr.data.translation_repo.TranslationRepositoryProvider;
import com.ringov.yatrnsltr.translation_module.entities.LangPairData;
import com.ringov.yatrnsltr.translation_module.entities.TranslationData;
import com.ringov.yatrnsltr.ui_entities.UILangPair;
import com.ringov.yatrnsltr.ui_entities.UITranslation;

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
                .map(LangPairData::toUILangPair);
    }

    @Override
    public Observable<UILangPair> loadLastLangPair() {
        return CommonRepositoryProvider.getCommonRepository().loadLastLangPair()
                .map(langPairData -> {
                    crtLangPair = langPairData;
                    return crtLangPair.toUILangPair();
                });
    }

    @Override
    public void saveLastLangPair() {
        CommonRepositoryProvider.getCommonRepository().saveLastLangPair(crtLangPair);
    }

    private UITranslation convertTranslation(TranslationData translationData) {
        UITranslation translation = new UITranslation(translationData, crtLangPair);
        TranslationData stoned = StonedConvertingService.convert(translationData);
        translation.setChangedData(stoned.getOriginal(), stoned.getTranslations());
        return translation;
    }

}
