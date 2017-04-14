package com.ringov.yatrnsltr.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ringov.yatrnsltr.App;
import com.ringov.yatrnsltr.data.lang.Language;
import com.ringov.yatrnsltr.translation_module.entity.LangPairData;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class SharedPreferencesStorage {

    private static final String SHARED_PREFERENCES_TAG = "yatrnsltr_shared_preferences";
    private static final String LAST_SOURCE_LANG = "last_source_lang";
    private static final String LAST_TARGET_LANG = "last_target_lang";

    // todo improve dealing with languages (not simply strings)
    private static final String DEFAULT_SOURCE_LANG = "ru";
    private static final String DEFAULT_TARGET_LANG = "en";

    private static SharedPreferences sp;

    static {
        new SharedPreferencesStorage();
    }

    public SharedPreferencesStorage() {
        final Context context = App.getAppContext();
        sp = context.getSharedPreferences(SHARED_PREFERENCES_TAG, Activity.MODE_PRIVATE);
    }

    public static LangPairData loadLastLangPair() {
        String sourceLang = sp.getString(LAST_SOURCE_LANG, "");
        String targetLang = sp.getString(LAST_TARGET_LANG, "");
        if (sourceLang.equals("")) {
            sourceLang = DEFAULT_SOURCE_LANG;
        }
        if (targetLang.equals("")) {
            targetLang = DEFAULT_TARGET_LANG;
        }
        return new LangPairData(new Language(sourceLang), new Language(targetLang));
    }

    public static void saveLastLangPair(LangPairData langPair) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LAST_SOURCE_LANG, langPair.getSourceLang().getShortName());
        editor.putString(LAST_TARGET_LANG, langPair.getTargetLang().getShortName());
        editor.apply();
    }

}
