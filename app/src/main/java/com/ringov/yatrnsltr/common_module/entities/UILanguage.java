package com.ringov.yatrnsltr.common_module.entities;

import com.ringov.yatrnsltr.data.lang.Language;

/**
 * Created by Sergey Koltsov on 19.04.2017.
 */

public class UILanguage {

    public static UILanguage EMPTY = new UILanguage();

    private String shortName;
    private String fullName;

    public UILanguage(Language languages) {
        this.shortName = languages.getShortName();
        this.fullName = languages.getFullOriginalName();
    }

    /**
     * service constructor
     */
    private UILanguage() {

    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object obj) {
        return shortName.equals(((UILanguage) obj).getShortName());
    }
}
