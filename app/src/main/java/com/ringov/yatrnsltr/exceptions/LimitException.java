package com.ringov.yatrnsltr.exceptions;

import com.ringov.yatrnsltr.exceptions.base.UIException;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class LimitException extends UIException {
    public LimitException(String message) {
        super(message);
    }
}
