package com.ringov.stonedtrnsltr.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ringov.stonedtrnsltr.App;
import com.ringov.stonedtrnsltr.exceptions.NoInternetConnectionException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sergey Koltsov on 19.04.2017.
 */

public class ConnectivityInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isOnline()) {
            throw new NoInternetConnectionException("No access to the Internet");
        }
        Request request = chain.request();
        Response response = chain.proceed(request);
        return response;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
