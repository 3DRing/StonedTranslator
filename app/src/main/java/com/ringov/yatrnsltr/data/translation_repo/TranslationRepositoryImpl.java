package com.ringov.yatrnsltr.data.translation_repo;

import com.ringov.yatrnsltr.Config;
import com.ringov.yatrnsltr.api.ApiFactory;
import com.ringov.yatrnsltr.api.TranslationRetrofitService;
import com.ringov.yatrnsltr.api.raw_entity.TranslationResponse;
import com.ringov.yatrnsltr.data.SharedPreferencesStorage;
import com.ringov.yatrnsltr.translation_module.entities.LangPairData;
import com.ringov.yatrnsltr.translation_module.entities.TranslationData;
import com.ringov.yatrnsltr.translation_module.interactor.TranslationInteractorImpl;

import java.util.Locale;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationRepositoryImpl implements TranslationRepository {

    private PublishSubject<TranslationData> translationEvents;

    TranslationRepositoryImpl() {
        translationEvents = PublishSubject.create();
    }

    private TranslationRetrofitService getService() {
        return ApiFactory.getRetrofitService(TranslationRetrofitService.class);
    }

    @Override
    public Observable<TranslationData> translate(String text, LangPairData langPair) {
        return getService().translate(Config.API_KEY, apiLangFormat(langPair), text)
                .map(response -> convertResponse(text, langPair, response))
                .doOnNext(translationEvents::onNext);
    }

    @Override
    public Observable<LangPairData> loadLastLangPair() {
        return Observable.just(SharedPreferencesStorage.loadLastLangPair());
    }

    @Override
    public void saveLastLangPair(LangPairData langPair) {
        SharedPreferencesStorage.saveLastLangPair(langPair);
    }

    @Override
    public PublishSubject<TranslationData> subscribeToTranslation() {
        return translationEvents;
    }

    private TranslationData convertResponse(String originalText, LangPairData langPair, TranslationResponse response) {
        return new TranslationData(originalText, response.getText(), langPair);
    }

    private String apiLangFormat(LangPairData langPair) {
        return String.format(Locale.ENGLISH, "%1$s-%2$s", langPair.getSourceLang().getShortName(),
                langPair.getTargetLang().getShortName());
    }

}
