package com.ringov.yatrnsltr.base;

import com.ringov.yatrnsltr.base.interfaces.BaseInteractor;
import com.ringov.yatrnsltr.base.interfaces.BaseRouter;
import com.ringov.yatrnsltr.base.interfaces.BaseView;
import com.ringov.yatrnsltr.exceptions.ViewMissingException;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public abstract class BasePresenter<VIEW extends BaseView, ROUTER extends BaseRouter, INTERACTOR extends BaseInteractor> {

    private VIEW mView;
    private ROUTER mRouter;
    private INTERACTOR mInteractor;
    private ExceptionHandler mExceptionHandler;

    public BasePresenter(VIEW view, ROUTER router, INTERACTOR interactor) {
        mRouter = router;
        mInteractor = interactor;
        attachView(view);
        mExceptionHandler = new ExceptionHandler(this::getView);
    }

    private void attachView(VIEW view) {
        mView = view;
    }

    private void detachView() {
        mView = null;
        getRouter().detachContext();
    }

    public void onDestroy() {
        detachView();
    }

    public abstract void onViewResumed();

    public abstract void onViewPaused();

    public VIEW getView() {
        if (mView == null) {
            throw new ViewMissingException("View " + mView.getClass().getName() + " was missing on a call");
        }
        return mView;
    }

    public ROUTER getRouter() {
        return mRouter;
    }

    public INTERACTOR getInteractor() {
        return mInteractor;
    }

    public void handleError(Throwable throwable) {
        mExceptionHandler.handleError(throwable);
    }
}
