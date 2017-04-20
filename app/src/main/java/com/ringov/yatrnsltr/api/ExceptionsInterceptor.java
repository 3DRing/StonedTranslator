package com.ringov.yatrnsltr.api;

import com.google.gson.Gson;
import com.ringov.yatrnsltr.api.raw_entity.TranslationResponse;
import com.ringov.yatrnsltr.exceptions.ApiException;
import com.ringov.yatrnsltr.exceptions.LimitException;
import com.ringov.yatrnsltr.exceptions.TranslationException;
import com.ringov.yatrnsltr.exceptions.UnknownException;
import com.ringov.yatrnsltr.exceptions.base.ExceptionsConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class ExceptionsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int status = response.code();

        switch (status) {
            case ExceptionsConfig.CODE_SUCCESS:
                return response;
            case ExceptionsConfig.WRONG_API_KEY:
                throw new ApiException(getErrorMessage(response));
            case ExceptionsConfig.BLOCKED_API_KEY:
                throw new ApiException(getErrorMessage(response));
            case ExceptionsConfig.DAILY_LIMIT_OVERFLOW:
                throw new LimitException(getErrorMessage(response));
            case ExceptionsConfig.TEST_SIZE_LIMIT_OVERFLOW:
                throw new LimitException(getErrorMessage(response));
            case ExceptionsConfig.TEXT_CAN_NOT_BE_TRANSLATED:
                throw new TranslationException(getErrorMessage(response));
            case ExceptionsConfig.TRANSLATION_DIRETION_NOT_SUPPORTED:
                throw new TranslationException(getErrorMessage(response));
            default:
                throw new UnknownException(getErrorMessage(response));
        }
    }

    private String getErrorMessage(Response response) throws IOException {
        return new Gson().fromJson(response.body().string(), TranslationResponse.class).getMessage();
    }
}
