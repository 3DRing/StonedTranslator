package com.ringov.yatrnsltr.data.stoned_service.translators;

import com.ringov.yatrnsltr.data.stoned_service.StonedConverter;

/**
 * If language is not supported by converter
 * we simple return the same string
 * <p>
 * <p>
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class DefaultConverter implements StonedConverter {
    @Override
    public String convert(String string) {
        return string;
    }
}
