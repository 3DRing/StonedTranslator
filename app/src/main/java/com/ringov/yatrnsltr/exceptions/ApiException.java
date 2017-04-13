package com.ringov.yatrnsltr.exceptions;

import com.ringov.yatrnsltr.exceptions.base.InternalException;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class ApiException extends InternalException {
    public ApiException(String message) {
        super(message);
    }
}
