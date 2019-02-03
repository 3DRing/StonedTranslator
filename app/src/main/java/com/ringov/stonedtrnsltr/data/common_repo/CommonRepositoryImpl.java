package com.ringov.stonedtrnsltr.data.common_repo;

import com.ringov.stonedtrnsltr.Config;
import com.ringov.stonedtrnsltr.api.ApiFactory;
import com.ringov.stonedtrnsltr.api.TranslationRetrofitService;
import com.ringov.stonedtrnsltr.api.raw_entity.LanguageItem;
import com.ringov.stonedtrnsltr.data.SharedPreferencesStorage;
import com.ringov.stonedtrnsltr.data.lang.Language;
import com.ringov.stonedtrnsltr.translation_module.entities.LangPairData;

import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class CommonRepositoryImpl implements CommonRepository {

    private static final long LANG_UPDATE_EXPIRED = 1000 * 60 * 60 * 24 * 7; // 7 days
    private LangPairData crtLangPair;

    private BehaviorSubject<LangPairData> changingLanguageEvents;
    private BehaviorSubject<Boolean> changingModeEvents;

    private boolean languageUpdate = false;

    CommonRepositoryImpl() {
        changingModeEvents = BehaviorSubject.create();
        changingLanguageEvents = BehaviorSubject.create();
    }


    @Override
    public Observable<LangPairData> loadLastLangPair() {
        crtLangPair = SharedPreferencesStorage.loadLastLangPair();
        changingLanguageEvents.onNext(crtLangPair);
        return Observable.just(crtLangPair);
    }

    @Override
    public void saveLastLangPair() {
        changingLanguageEvents.onNext(crtLangPair);
        SharedPreferencesStorage.saveLastLangPair(crtLangPair);
    }

    @Override
    public Observable<Boolean> changeStonedMode(boolean stonedMode) {
        return SharedPreferencesStorage.saveStonedMode(stonedMode)
                .doOnNext(changingModeEvents::onNext);
    }

    @Override
    public Observable<Boolean> loadStonedMode() {
        return SharedPreferencesStorage.loadStonedMode()
                .doOnNext(changingModeEvents::onNext);
    }

    @Override
    public Observable<Boolean> subscribeToModeChanging() {
        return changingModeEvents;
    }

    @Override
    public Observable<List<Language>> loadAllLanguages() {
//        update languages once in 7 days
//        long lastUpdate = SharedPreferencesStorage.loadLastLanguagesUpdate();
//        long crt = System.currentTimeMillis();
        if (languageUpdate) {
            return Observable.just(Realm.getDefaultInstance()
                    .copyFromRealm(Realm.getDefaultInstance()
                            .where(Language.class)
                            .findAll()));
        } else {
            String currentLocale = Locale.getDefault().getLanguage();
            return getService().getAllLanguages(Config.API_KEY, currentLocale)
                    .flatMap(response -> Observable.from(response.getAllLangs()))
                    .map(this::convertToLanguage)
                    .toList()
                    .doOnNext(langs -> {
                        languageUpdate = true;
                        //SharedPreferencesStorage.saveLanguagesUpdate(crt);
                    })
                    .doOnNext(langs ->
                            Realm.getDefaultInstance().executeTransaction(realm -> {
                                realm.delete(Language.class);
                                realm.insert(langs);
                            }));
        }
    }

    @Override
    public Observable<LangPairData> subscribeToLangPairChanging() {
        return changingLanguageEvents;
    }

    @Override
    public Observable<LangPairData> changeLangPair(LangPairData langPair) {
        crtLangPair = langPair;
        return Observable.just(langPair);
    }

    private Language convertToLanguage(LanguageItem languageItem) {
        Language language = new Language(languageItem.getShortName());
        language.setFullOriginalName(languageItem.getFullName());
        return language;
    }

    private TranslationRetrofitService getService() {
        return ApiFactory.getRetrofitService(TranslationRetrofitService.class);
    }
}
