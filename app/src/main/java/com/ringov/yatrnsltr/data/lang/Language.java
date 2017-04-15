package com.ringov.yatrnsltr.data.lang;

import java.util.HashMap;
import java.util.Map;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class Language implements RealmModel {

    public static final Language EMPTY = new Language();
    private String shortName;
    private String fullOriginalName;

    /**
     * service constructor
     */
    private Language() {
        this.shortName = "";
        this.fullOriginalName = "";
    }

    public Language(SupportedLanguage lang) {
        this.shortName = lang.toString().toLowerCase();
    }

    public Language(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullOriginalName() {
        return fullOriginalName;
    }

    public void setFullOriginalName(String fullOriginalName) {
        this.fullOriginalName = fullOriginalName;
    }

    @Override
    public boolean equals(Object obj) {
        // instance and has the same short name
        return obj instanceof Language && this.shortName.equals(((Language) obj).shortName);
    }

    public boolean equals(SupportedLanguage lang) {
        return shortName.equals(lang.toString().toLowerCase());
    }

    public SupportedLanguage getSupported() {
        return SupportedLanguage.fromString(shortName);
    }

    public enum SupportedLanguage {
        RU, EN, NOT;

        private static Map<String, SupportedLanguage> langMap = new HashMap<String, SupportedLanguage>() {{
            put("ru", RU);
            put("en", EN);
        }};

        static SupportedLanguage fromString(String lang) {
            SupportedLanguage supLang = langMap.get(lang);
            return supLang != null ? supLang : NOT;
        }
    }
}
