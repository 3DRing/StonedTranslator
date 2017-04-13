package com.ringov.yatrnsltr.exceptions;

import com.ringov.yatrnsltr.exceptions.base.UIException;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class TranslationException extends UIException {
    public TranslationException(String message) {
        super(message);
    }
}
