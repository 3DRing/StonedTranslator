package com.ringov.yatrnsltr.translation_module.interactor;

import com.ringov.yatrnsltr.base.interfaces.BaseInteractor;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UITranslation;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface TranslationInteractor extends BaseInteractor {
    Observable<UITranslation> translate(String text);
}
