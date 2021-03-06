package com.ringov.stonedtrnsltr.translation_module.entities;

import com.ringov.stonedtrnsltr.data.lang.Language;
import com.ringov.stonedtrnsltr.ui_entities.UILangPair;

import io.realm.RealmModel;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class LangPairData implements RealmModel {
    private Language sourceLang;
    private Language targetLang;

    public LangPairData(Language sourceLang, Language targetLang) {
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    public LangPairData(UILangPair langPair) {
        this.sourceLang = new Language(langPair.getSourceLang().getShortName());
        this.targetLang = new Language(langPair.getTargetLang().getShortName());
    }

    public Language getSourceLang() {
        return sourceLang;
    }

    public Language getTargetLang() {
        return targetLang;
    }

    public void swap() {
        Language tmp = this.sourceLang;
        this.sourceLang = this.targetLang;
        this.targetLang = tmp;
    }

    public UILangPair toUILangPair() {
        return new UILangPair(sourceLang.toUILanguage(), targetLang.toUILanguage());
    }
}
