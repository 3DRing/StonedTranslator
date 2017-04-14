package com.ringov.trnsltr_service;

import com.ringov.trnsltr_service.translators.ENConverter;
import com.ringov.trnsltr_service.translators.RUConverter;

/**
 * Created by Сергей on 14.04.2017.
 */
class ConverterFactory {

    static StonedConverter getConverter(String lang) {
        // todo implement
        if (lang.equals("ru")) {
            return new RUConverter();
        } else if (lang.equals("en")) {
            return new ENConverter();
        } else {
            return new RUConverter();
        }
    }
}
