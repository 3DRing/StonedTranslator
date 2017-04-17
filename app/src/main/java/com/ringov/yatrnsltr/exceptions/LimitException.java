package com.ringov.yatrnsltr.exceptions;

import com.ringov.yatrnsltr.exceptions.base.UserImportantException;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class LimitException extends UserImportantException {
    public LimitException(String message) {
        super(message);
    }
}
