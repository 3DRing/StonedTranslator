package com.ringov.yatrnsltr.translation_module.view.ui_entity;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class UILangPair {
    public static final UILangPair EMPTY = new UILangPair();
    private String sourceLang;
    private String targetLang;

    public UILangPair(String sourceLang, String targetLang) {
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    /**
     * service constructor
     */
    public UILangPair() {
        this.sourceLang = "";
        this.targetLang = "";
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }
}
