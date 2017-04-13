package com.ringov.yatrnsltr.translation_module.entity;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class LangPairData {
    private String sourceLang;
    private String targetLang;

    public LangPairData(String sourceLang, String targetLang) {
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void swap() {
        String tmp = this.sourceLang;
        this.sourceLang = this.targetLang;
        this.targetLang = tmp;
    }
}
