package com.ringov.trnsltr_service.translators;

import com.ringov.trnsltr_service.StonedConverter;

/**
 * Created by Сергей on 14.04.2017.
 */
public class ENConverter implements StonedConverter {
    @Override
    public String convert(String string) {
        return string + " (stoned)";
    }
}
