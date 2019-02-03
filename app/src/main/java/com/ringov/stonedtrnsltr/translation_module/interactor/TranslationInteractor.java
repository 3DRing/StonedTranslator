package com.ringov.stonedtrnsltr.translation_module.interactor;

import com.ringov.stonedtrnsltr.base.interfaces.BaseInteractor;
import com.ringov.stonedtrnsltr.ui_entities.UILangPair;
import com.ringov.stonedtrnsltr.ui_entities.UITranslation;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface TranslationInteractor extends BaseInteractor {
    Observable<UITranslation> translate(String text);

    Observable<UILangPair> swapLanguage();

    Observable<UILangPair> subscribeToLangPairChanging();

    Observable<UITranslation> subscribeToPreviousTranslationPicking();
}
