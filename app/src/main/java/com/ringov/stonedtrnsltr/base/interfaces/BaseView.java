package com.ringov.stonedtrnsltr.base.interfaces;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface BaseView {
    void showLoading();
    void hideLoading();
    void showKnownException(String message);
    void showInternalException(String message);
    void showUnknownException(String message);

    void showInternetConnectionException(String message);
}
