package com.ringov.yatrnsltr.api.raw_entity;

/**
 * Created by Sergey Koltsov on 18.04.2017.
 */

public class LanguageItem {
    String shortName;
    String fullName;

    public LanguageItem(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }
}
