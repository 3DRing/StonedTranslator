package com.ringov.stonedtrnsltr.exceptions;

import com.ringov.stonedtrnsltr.exceptions.base.UserImportantException;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class LimitException extends UserImportantException {
    public LimitException(String message) {
        super(message);
    }
}
