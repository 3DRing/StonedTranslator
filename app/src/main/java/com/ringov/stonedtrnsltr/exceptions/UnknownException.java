package com.ringov.stonedtrnsltr.exceptions;

import com.ringov.stonedtrnsltr.exceptions.base.InternalException;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class UnknownException extends InternalException {
    public UnknownException(String message){
        super(message);
    }
}
