package com.ringov.yatrnsltr.exceptions.base;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public abstract class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
