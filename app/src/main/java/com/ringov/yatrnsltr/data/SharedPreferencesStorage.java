package com.ringov.yatrnsltr.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ringov.yatrnsltr.App;
import com.ringov.yatrnsltr.data.lang.Language;
import com.ringov.yatrnsltr.translation_module.entities.LangPairData;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class SharedPreferencesStorage {

    private static final String SHARED_PREFERENCES_TAG = "yatrnsltr_shared_preferences";
    private static final String LAST_SOURCE_LANG = "last_source_lang";
    private static final String LAST_TARGET_LANG = "last_target_lang";
    private static final String STONED_MODE = "stoned_mode";
    private static final String LAST_LANGS_UPDATE = "last_langs_update";

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
            sourceLang = new Language(Language.SupportedLanguage.RU).getShortName();
        }
        if (targetLang.equals("")) {
            targetLang = new Language(Language.SupportedLanguage.EN).getShortName();
        }
        return new LangPairData(new Language(sourceLang), new Language(targetLang));
    }

    public static void saveLastLangPair(LangPairData langPair) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LAST_SOURCE_LANG, langPair.getSourceLang().getShortName());
        editor.putString(LAST_TARGET_LANG, langPair.getTargetLang().getShortName());
        editor.apply();
    }

    public static Observable<Boolean> saveStonedMode(boolean stonedMode) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(STONED_MODE, stonedMode);
        editor.apply();
        return Observable.just(stonedMode);
    }

    public static Observable<Boolean> loadStonedMode() {
        return Observable.just(sp.getBoolean(STONED_MODE, false));
    }

    public static long loadLastLanguagesUpdate() {
        return sp.getLong(LAST_LANGS_UPDATE, 0);
    }

    public static void saveLanguagesUpdate(long crtUpdate) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(LAST_LANGS_UPDATE, crtUpdate);
        editor.apply();
    }
}
