package com.ringov.yatrnsltr.data.trnsltr_service.translators;

import com.ringov.yatrnsltr.data.trnsltr_service.StonedConverter;
import com.ringov.yatrnsltr.exceptions.StonedServiceException;

/**
 * Throws an exception if something went wrong
 * in dealing with languages and its converting
 *
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class DefaultConverter implements StonedConverter {
    @Override
    public String convert(String string) {
        throw new StonedServiceException("Problems with converting appears on a string: " + string);
    }
}
