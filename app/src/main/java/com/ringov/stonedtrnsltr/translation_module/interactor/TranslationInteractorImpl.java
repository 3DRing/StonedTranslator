package com.ringov.stonedtrnsltr.translation_module.interactor;

import com.ringov.stonedtrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.stonedtrnsltr.data.common_repo.CommonRepositoryProvider;
import com.ringov.stonedtrnsltr.data.stoned_service.StonedConvertingService;
import com.ringov.stonedtrnsltr.data.storage_repo.StorageRepositoryProvider;
import com.ringov.stonedtrnsltr.data.translation_repo.TranslationRepositoryProvider;
import com.ringov.stonedtrnsltr.storage_module.interactor.StorageInteractorImpl;
import com.ringov.stonedtrnsltr.translation_module.entities.LangPairData;
import com.ringov.stonedtrnsltr.translation_module.entities.TranslationData;
import com.ringov.stonedtrnsltr.ui_entities.UILangPair;
import com.ringov.stonedtrnsltr.ui_entities.UITranslation;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationInteractorImpl extends BaseInteractorImpl implements TranslationInteractor {

    private LangPairData crtLangPair;

    @Override
    public Observable<UILangPair> subscribeToLangPairChanging() {
        return CommonRepositoryProvider.getCommonRepository()
                .subscribeToLangPairChanging()
                .doOnNext(langPair -> crtLangPair = langPair)
                .map(LangPairData::toUILangPair);
    }

    @Override
    public Observable<UITranslation> subscribeToPreviousTranslationPicking() {
        return StorageRepositoryProvider.getStorageRepository().subscribeToPreviousTranslationPicking()
                .map(StorageInteractorImpl::applyStonedMode);
    }

    public Observable<UITranslation> translate(String text) {
        return TranslationRepositoryProvider.getTranslationRepository()
                .translate(text, crtLangPair)
                .map(this::convertTranslation);
    }

    @Override
    public Observable<UILangPair> swapLanguage() {
        crtLangPair.swap();
        return CommonRepositoryProvider.getCommonRepository()
                .changeLangPair(crtLangPair)
                .map(LangPairData::toUILangPair);
    }

    private UITranslation convertTranslation(TranslationData translationData) {
        UITranslation translation = new UITranslation(translationData, crtLangPair);
        TranslationData stoned = StonedConvertingService.convert(translationData);
        translation.setChangedData(stoned.getOriginal(), stoned.getTranslations());
        return translation;
    }

}
