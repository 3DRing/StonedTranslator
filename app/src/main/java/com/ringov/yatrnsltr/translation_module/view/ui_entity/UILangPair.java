package com.ringov.yatrnsltr.translation_module.view.ui_entity;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class UILangPair {
    private String sourceLang;
    private String targetLang;

    public UILangPair(String sourceLang, String targetLang) {
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }
}
