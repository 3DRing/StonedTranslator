package com.ringov.yatrnsltr.exceptions;

import com.ringov.yatrnsltr.exceptions.base.UserImportantException;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class TranslationException extends UserImportantException {
    public TranslationException(String message) {
        super(message);
    }
}
