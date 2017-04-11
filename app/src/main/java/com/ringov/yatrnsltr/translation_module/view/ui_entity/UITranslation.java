package com.ringov.yatrnsltr.translation_module.view.ui_entity;

import com.ringov.yatrnsltr.translation_module.entity.TranslationData;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class UITranslation {

    private String translation;

    public UITranslation(TranslationData translationData) {
        this.translation = translationData.getTranslation();
    }

    public String getTranslation() {
        return translation;
    }
}
