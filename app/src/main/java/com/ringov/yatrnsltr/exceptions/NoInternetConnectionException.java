package com.ringov.yatrnsltr.exceptions;

import com.ringov.yatrnsltr.exceptions.base.UserImportantException;

/**
 * Created by Sergey Koltsov on 19.04.2017.
 */

public class NoInternetConnectionException extends UserImportantException {
    public NoInternetConnectionException(String message) {
        super(message);
    }
}
