package com.ringov.yatrnsltr.data;

import com.ringov.yatrnsltr.translation_module.entity.TranslationData;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface TranslationRepository {
    Observable<TranslationData> translate(String text);
}
