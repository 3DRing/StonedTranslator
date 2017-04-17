package com.ringov.yatrnsltr.base;

import com.ringov.yatrnsltr.base.interfaces.BaseView;
import com.ringov.yatrnsltr.exceptions.base.InternalException;
import com.ringov.yatrnsltr.exceptions.base.UserImportantException;

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
        if (t.getClass().isAssignableFrom(UserImportantException.class)) {
            handleUserImportantException(t);
        } else if (t.getClass().isAssignableFrom(InternalException.class)) {
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
        getView().showKnownException(t.getLocalizedMessage());
    }
}
