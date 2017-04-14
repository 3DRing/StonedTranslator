package com.ringov.yatrnsltr.translation_module.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationData {
    private String originalText;
    private List<String> translations;
    private LangPairData langPair;

    public TranslationData(String original, List<String> translation, LangPairData langPair) {
        this.originalText = original;
        this.translations = translation != null ? translation : new ArrayList<>();
        this.langPair = langPair;
    }

    public String getOriginal() {
        return originalText;
    }

    public List<String> getTranslation() {
        return translations;
    }

    public LangPairData getLangPair() {
        return langPair;
    }
}
