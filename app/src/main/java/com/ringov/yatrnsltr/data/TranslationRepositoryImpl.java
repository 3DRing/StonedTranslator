package com.ringov.yatrnsltr.data;

import com.ringov.yatrnsltr.Config;
import com.ringov.yatrnsltr.api.ApiFactory;
import com.ringov.yatrnsltr.api.TranslationRetrofitService;
import com.ringov.yatrnsltr.api.raw_entity.TranslationResponse;
import com.ringov.yatrnsltr.data.lang.Language;
import com.ringov.yatrnsltr.translation_module.entity.LangPairData;
import com.ringov.yatrnsltr.translation_module.entity.TranslationData;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UILangPair;

import java.util.Locale;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationRepositoryImpl implements TranslationRepository {

    private TranslationRetrofitService getService() {
        return ApiFactory.getRetrofitService(TranslationRetrofitService.class);
    }

    @Override
    public Observable<TranslationData> translate(String text, LangPairData langPair) {
        return getService().translate(Config.API_KEY, apiLangFormat(langPair), text)
                .map(response -> convertResponse(text, response));
    }

    @Override
    public Observable<LangPairData> loadLastLangPair() {
        return Observable.just(SharedPreferencesStorage.loadLastLangPair());
    }

    @Override
    public void saveLastLangPair(LangPairData langPair) {
        SharedPreferencesStorage.saveLastLangPair(langPair);
    }

    private TranslationData convertResponse(String originalText, TranslationResponse response) {
        return new TranslationData(originalText, response.getText());
    }

    private String apiLangFormat(LangPairData langPair) {
        return String.format(Locale.ENGLISH, "%1$s-%2$s", langPair.getSourceLang().getShortName(),
                langPair.getTargetLang().getShortName());
    }

}
