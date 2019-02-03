package com.ringov.stonedtrnsltr.exceptions;

import com.ringov.stonedtrnsltr.exceptions.base.UserImportantException;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class TranslationException extends UserImportantException {
    public TranslationException(String message) {
        super(message);
    }
}
