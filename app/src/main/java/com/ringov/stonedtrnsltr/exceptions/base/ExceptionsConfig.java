package com.ringov.stonedtrnsltr.exceptions.base;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class ExceptionsConfig {
    public static final int CODE_SUCCESS = 200;
    public static final int WRONG_API_KEY = 401;
    public static final int BLOCKED_API_KEY = 402;
    public static final int DAILY_LIMIT_OVERFLOW = 404;
    public static final int TEST_SIZE_LIMIT_OVERFLOW = 413;
    public static final int TEXT_CAN_NOT_BE_TRANSLATED = 422;
    public static final int TRANSLATION_DIRETION_NOT_SUPPORTED = 501;
}
