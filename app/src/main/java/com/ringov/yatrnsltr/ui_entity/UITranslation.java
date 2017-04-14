package com.ringov.yatrnsltr.ui_entity;

import com.ringov.yatrnsltr.translation_module.entity.LangPairData;
import com.ringov.yatrnsltr.translation_module.entity.TranslationData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class UITranslation {

    public static UITranslation EMPTY = new UITranslation();

    private List<String> translations;
    private String originalText;
    private UILangPair langPair;

    public UITranslation(TranslationData translationData, LangPairData langPair) {
        this.originalText = translationData.getOriginal();
        this.translations = translationData.getTranslation();
        this.langPair = langPair.toUILangPair();
    }

    /**
     * service constructor
     */
    private UITranslation() {
        this.translations = new ArrayList<>();
        this.originalText = "";
        this.langPair = UILangPair.EMPTY;
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
