package com.ringov.yatrnsltr.translation_module.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationData {
    private String originalText;
    private List<String> translations;

    public TranslationData(String original, List<String> translation) {
        this.originalText = original;
        this.translations = translation != null ? translation : new ArrayList<>();
    }

    public String getOriginal() {
        return originalText;
    }

    public List<String> getTranslation() {
        return translations;
    }

}
