package com.ringov.yatrnsltr.translation_module.view.ui_entity;

import com.ringov.yatrnsltr.translation_module.entity.TranslationData;

import java.util.List;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class UITranslation {

    private List<String> translations;
    private String originalText;

    public UITranslation(TranslationData translationData) {
        this.originalText = translationData.getOriginal();
        this.translations = translationData.getTranslation();
    }

    public List<String> getTranslations() {
        return translations;
    }

    public String getOriginalText() {
        return originalText;
    }

    public boolean isEmpty(){
        return translations.size() == 0;
    }
}
