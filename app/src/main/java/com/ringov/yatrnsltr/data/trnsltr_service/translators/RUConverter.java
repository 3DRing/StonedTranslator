package com.ringov.yatrnsltr.data.trnsltr_service.translators;

import com.ringov.yatrnsltr.data.trnsltr_service.StonedConverter;

/**
 * Created by Сергей on 14.04.2017.
 */
public class RUConverter implements StonedConverter {
    @Override
    public String convert(String string) {
        return string + " (упоротый)";
    }
}
