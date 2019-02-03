package com.ringov.stonedtrnsltr.base;

import com.ringov.stonedtrnsltr.base.interfaces.BaseView;
import com.ringov.stonedtrnsltr.exceptions.NoInternetConnectionException;
import com.ringov.stonedtrnsltr.exceptions.base.InternalException;
import com.ringov.stonedtrnsltr.exceptions.base.UserImportantException;

import rx.functions.Func0;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class ExceptionHandler {

    private Func0<BaseView> mViewProvider;

    ExceptionHandler(Func0<BaseView> viewProvider) {
        mViewProvider = viewProvider;
    }

    private BaseView getView() {
        return mViewProvider.call();
    }

    public void handleError(Throwable t) {
        if (UserImportantException.class.isAssignableFrom(t.getClass())) {
            handleUserImportantException(t);
        } else if (InternalException.class.isAssignableFrom(t.getClass())) {
            handleInternalException(t);
        } else {
            handleOtherException(t);
        }
    }

    private void handleOtherException(Throwable t) {
        // todo keep track of errors log, notify developer
        getView().showUnknownException(t.getLocalizedMessage());
    }

    private void handleInternalException(Throwable t) {
        // todo keep track of errors log, notify developer
        getView().showInternalException(t.getLocalizedMessage());
    }

    private void handleUserImportantException(Throwable t) {
        if (t instanceof NoInternetConnectionException) {
            getView().showInternetConnectionException(t.getLocalizedMessage());
        } else {
            getView().showKnownException(t.getLocalizedMessage());
        }
    }
}
