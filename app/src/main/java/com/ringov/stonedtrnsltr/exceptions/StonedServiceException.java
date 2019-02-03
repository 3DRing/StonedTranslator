package com.ringov.stonedtrnsltr.exceptions;

import com.ringov.stonedtrnsltr.exceptions.base.InternalException;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StonedServiceException extends InternalException {
    public StonedServiceException(String message) {
        super(message);
    }
}
