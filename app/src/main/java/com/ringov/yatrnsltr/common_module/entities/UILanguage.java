package com.ringov.yatrnsltr.common_module.entities;

import com.ringov.yatrnsltr.data.lang.Language;

/**
 * Created by Sergey Koltsov on 19.04.2017.
 */

public class UILanguage {
    private String shortName;
    private String fullName;

    public UILanguage(Language languages) {
        this.shortName = languages.getShortName();
        this.fullName = languages.getFullOriginalName();
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }
}
