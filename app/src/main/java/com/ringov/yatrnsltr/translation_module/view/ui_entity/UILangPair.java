package com.ringov.yatrnsltr.translation_module.view.ui_entity;

import com.ringov.yatrnsltr.data.lang.Language;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class UILangPair {
    public static final UILangPair EMPTY = new UILangPair();
    private Language sourceLang;
    private Language targetLang;

    public UILangPair(Language sourceLang, Language targetLang) {
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    /**
     * service constructor
     */
    public UILangPair() {
        this.sourceLang = Language.EMPTY;
        this.targetLang = Language.EMPTY;
    }

    public String getSourceLangShortName() {
        return sourceLang.getShortName();
    }

    public String getTargetLangShortName() {
        return targetLang.getShortName();
    }
}
