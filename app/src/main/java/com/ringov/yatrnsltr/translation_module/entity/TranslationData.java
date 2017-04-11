package com.ringov.yatrnsltr.translation_module.entity;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationData {
    private String originalText;
    private String translationText;

    public TranslationData(String original, String translation) {
        this.originalText = original;
        this.translationText = translation;
    }

    public String getOriginal() {
        return originalText;
    }

    public String getTranslation() {
        return translationText;
    }
}
