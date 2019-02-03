package com.ringov.stonedtrnsltr.data.lang;

import com.ringov.stonedtrnsltr.common_module.entities.UILanguage;

import java.util.HashMap;
import java.util.Map;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

@RealmClass
public class Language implements RealmModel {

    public static final Language EMPTY = new Language();
    private String shortName;
    private String fullOriginalName;

    /**
     * service constructor
     */
    public Language() {
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

    public UILanguage toUILanguage() {
        return new UILanguage(this);
    }

    public enum SupportedLanguage {
        //- English
        //- French
        //- German
        //- Spanish
        //- Italian
        //- Russian
        //- Ukrainian
        EN, FR, DE, ES, IT, RU, UKR, NOT;

        private static Map<String, SupportedLanguage> langMap = new HashMap<String, SupportedLanguage>() {{
            put("ru", RU);
            put("uk", UKR);
            put("en", EN);
            put("fr", FR);
            put("de", DE);
            put("es", ES);
            put("it", IT);
        }};

        static SupportedLanguage fromString(String lang) {
            SupportedLanguage supLang = langMap.get(lang);
            return supLang != null ? supLang : NOT;
        }
    }
}
