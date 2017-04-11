package com.ringov.yatrnsltr.translation_module.view.ui_entity;

import com.ringov.yatrnsltr.translation_module.entity.TranslationData;

import java.util.List;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class UITranslation {

    private List<String> translations;

    public UITranslation(TranslationData translationData) {
        this.translations = translationData.getTranslation();
    }

    public List<String> getTranslations() {
        return translations;
    }
}
