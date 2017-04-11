package com.ringov.yatrnsltr.data;

import com.ringov.yatrnsltr.Config;
import com.ringov.yatrnsltr.api.ApiFactory;
import com.ringov.yatrnsltr.api.TranslationRetrofitService;
import com.ringov.yatrnsltr.api.raw_entity.TranslationResponse;
import com.ringov.yatrnsltr.translation_module.entity.TranslationData;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationRepositoryImpl implements TranslationRepository {

    private TranslationRetrofitService getService() {
        return ApiFactory.getRetrofitService(TranslationRetrofitService.class);
    }

    @Override
    public Observable<TranslationData> translate(String text) {
        // todo remove hardcode
        return getService().translate(Config.API_KEY, "ru-en", text)
                .map(response -> convertResponse(text, response));
    }

    private TranslationData convertResponse(String originalText, TranslationResponse response) {
        return new TranslationData(originalText, response.getText());
    }

}
