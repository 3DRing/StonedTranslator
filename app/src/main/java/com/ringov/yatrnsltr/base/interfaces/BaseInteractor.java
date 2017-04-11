package com.ringov.yatrnsltr.base.interfaces;

import com.ringov.yatrnsltr.translation_module.view.ui_entity.UITranslation;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface BaseInteractor {
    Observable<UITranslation> translate(String text);
}
