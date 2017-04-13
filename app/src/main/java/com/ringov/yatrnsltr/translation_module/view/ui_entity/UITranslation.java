package com.ringov.yatrnsltr.translation_module.view.ui_entity;

import com.ringov.yatrnsltr.translation_module.entity.LangPairData;
import com.ringov.yatrnsltr.translation_module.entity.TranslationData;

import java.util.List;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class UITranslation {

    private List<String> translations;
    private String originalText;
    private UILangPair langPair;

    public UITranslation(TranslationData translationData, LangPairData langPair) {
        this.originalText = translationData.getOriginal();
        this.translations = translationData.getTranslation();
        this.langPair = langPair.toUILangPair();
    }

    public List<String> getTranslations() {
        return translations;
    }

    public String getOriginalText() {
        return originalText;
    }

    public UILangPair getLangPair() {
        return langPair;
    }
}
