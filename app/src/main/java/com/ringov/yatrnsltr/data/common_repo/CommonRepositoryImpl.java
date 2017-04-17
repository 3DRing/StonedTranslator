package com.ringov.yatrnsltr.data.common_repo;

import com.ringov.yatrnsltr.Config;
import com.ringov.yatrnsltr.api.ApiFactory;
import com.ringov.yatrnsltr.api.TranslationRetrofitService;
import com.ringov.yatrnsltr.api.raw_entity.LanguageItem;
import com.ringov.yatrnsltr.data.SharedPreferencesStorage;
import com.ringov.yatrnsltr.data.lang.Language;

import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class CommonRepositoryImpl implements CommonRepository {

    private BehaviorSubject<Boolean> changingModeEvents;

    CommonRepositoryImpl() {
        changingModeEvents = BehaviorSubject.create();
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
        return getService().getAllLanguages(Config.API_KEY,
                new Language(Language.SupportedLanguage.RU).getShortName()) // customize
                .flatMap(response -> Observable.from(response.getAllLangs()))
                .map(this::convertToLanguage)
                .toList();
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
