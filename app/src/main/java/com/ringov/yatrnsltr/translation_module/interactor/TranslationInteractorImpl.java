package com.ringov.yatrnsltr.translation_module.interactor;

import com.ringov.yatrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.yatrnsltr.data.TranslationRepositoryProvider;
import com.ringov.yatrnsltr.translation_module.entity.TranslationData;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UITranslation;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationInteractorImpl extends BaseInteractorImpl implements TranslationInteractor {

    public Observable<UITranslation> translate(String text){
        return TranslationRepositoryProvider.getTranslationRepository()
                .translate(text)
                .map(this::convertTranslation);
    }

    private UITranslation convertTranslation(TranslationData translationData) {
        return new UITranslation(translationData);
    }

}
