package com.ringov.yatrnsltr.translation_module.entities;

import com.ringov.yatrnsltr.data.lang.Language;
import com.ringov.yatrnsltr.ui_entities.UILangPair;

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
