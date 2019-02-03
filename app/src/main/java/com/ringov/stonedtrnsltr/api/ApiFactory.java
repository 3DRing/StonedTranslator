package com.ringov.stonedtrnsltr.api;

import com.ringov.stonedtrnsltr.Config;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class ApiFactory {

    @NonNull
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new ConnectivityInterceptor())
            .addInterceptor(new ExceptionsInterceptor())
            .readTimeout(Config.TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(Config.TIMEOUT, TimeUnit.MILLISECONDS)
            .build();
    @NonNull
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @NonNull
    public static <T> T getRetrofitService(Class<T> tClass) {
        return retrofit.create(tClass);
    }
}
