package com.ringov.stonedtrnsltr.exceptions;

import com.ringov.stonedtrnsltr.exceptions.base.UserImportantException;

/**
 * Created by Sergey Koltsov on 19.04.2017.
 */

public class NoInternetConnectionException extends UserImportantException {
    public NoInternetConnectionException(String message) {
        super(message);
    }
}
