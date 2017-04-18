package com.ringov.yatrnsltr.api.raw_entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey Koltsov on 18.04.2017.
 */

public class LanguagesResponse {
    @SerializedName("langs")
    Map<String, String> allLangs;

    public LanguagesResponse() {
        this.allLangs = new HashMap<>();
    }

    public List<LanguageItem> getAllLangs() {
        Iterator<Map.Entry<String, String>> iterator = allLangs.entrySet().iterator();
        List<LanguageItem> list = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> crtEntry = iterator.next();
            list.add(new LanguageItem(crtEntry.getKey(), crtEntry.getValue()));
        }
        return list;
    }
}
