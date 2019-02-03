package com.ringov.stonedtrnsltr.data.stoned_service;

import com.ringov.stonedtrnsltr.data.lang.Language;
import com.ringov.stonedtrnsltr.data.stoned_service.translators.DEConverter;
import com.ringov.stonedtrnsltr.data.stoned_service.translators.DefaultConverter;
import com.ringov.stonedtrnsltr.data.stoned_service.translators.ESConverter;
import com.ringov.stonedtrnsltr.data.stoned_service.translators.FRConverter;
import com.ringov.stonedtrnsltr.data.stoned_service.translators.ITConverter;
import com.ringov.stonedtrnsltr.data.stoned_service.translators.LatinBaseConverter;
import com.ringov.stonedtrnsltr.data.stoned_service.translators.RUConverter;
import com.ringov.stonedtrnsltr.data.stoned_service.translators.URKConverter;

/**
 * Created by Сергей on 14.04.2017.
 */
class ConverterFactory {

    static StonedConverter getConverter(Language lang) {
        Language.SupportedLanguage sLang = lang.getSupported();

        StonedConverter converter = null;
        switch (sLang) {
            case EN:
                converter = new LatinBaseConverter();
                break;
            case FR:
                converter = new FRConverter();
                break;
            case DE:
                converter = new DEConverter();
                break;
            case ES:
                converter = new ESConverter();
                break;
            case IT:
                converter = new ITConverter();
                break;
            case RU:
                converter = new RUConverter();
                break;
            case UKR:
                converter = new URKConverter();
                break;
            case NOT:
                // break missed intentionally
            default:
                converter = new DefaultConverter();
                break;
        }

        return converter;
    }
}
