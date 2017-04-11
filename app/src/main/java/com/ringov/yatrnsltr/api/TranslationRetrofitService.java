package com.ringov.yatrnsltr.api;

import com.ringov.yatrnsltr.api.raw_entity.TranslationResponse;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface TranslationRetrofitService {

    Observable<TranslationResponse> translate(String key, String lang, String text);

}
