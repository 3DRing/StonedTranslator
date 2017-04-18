package com.ringov.yatrnsltr.ui_entities;

import com.ringov.yatrnsltr.common_module.entities.UILanguage;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class UILangPair {
    public static final UILangPair EMPTY = new UILangPair();
    private UILanguage sourceLang;
    private UILanguage targetLang;

    public UILangPair(UILanguage sourceLang, UILanguage targetLang) {
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    /**
     * service constructor
     */
    public UILangPair() {
        this.sourceLang = UILanguage.EMPTY;
        this.targetLang = UILanguage.EMPTY;
    }

    public UILanguage getSourceLang() {
        return sourceLang;
    }

    public UILanguage getTargetLang() {
        return targetLang;
    }
}
