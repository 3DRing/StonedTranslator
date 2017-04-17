package com.ringov.yatrnsltr.data.stoned_service;

import com.ringov.yatrnsltr.data.lang.Language;
import com.ringov.yatrnsltr.data.stoned_service.translators.DefaultConverter;
import com.ringov.yatrnsltr.data.stoned_service.translators.ENConverter;
import com.ringov.yatrnsltr.data.stoned_service.translators.RUConverter;

/**
 * Created by Сергей on 14.04.2017.
 */
class ConverterFactory {

    static StonedConverter getConverter(Language lang) {
        Language.SupportedLanguage sLang = lang.getSupported();

        StonedConverter converter = null;
        switch (sLang){
            case RU:
                converter = new RUConverter();
                break;
            case EN:
                converter = new ENConverter();
                break;
            case NOT:
                // break missed intentionally
            default:
                converter = new DefaultConverter(); // throws StonedServiceException on convert() call
                break;
        }

        return converter;
    }
}
