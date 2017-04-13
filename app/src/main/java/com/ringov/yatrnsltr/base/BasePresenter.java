package com.ringov.yatrnsltr.base;

import com.ringov.yatrnsltr.base.interfaces.BaseInteractor;
import com.ringov.yatrnsltr.base.interfaces.BaseRouter;
import com.ringov.yatrnsltr.base.interfaces.BaseView;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public abstract class BasePresenter<VIEW extends BaseView, ROUTER extends BaseRouter, INTERACTOR extends BaseInteractor> {

    private VIEW mView;
    private ROUTER mRouter;
    private INTERACTOR mInteractor;
    private ExceptionHandler mExceptionHandler;

    public BasePresenter(ROUTER router, INTERACTOR interactor) {
        mRouter = router;
        mInteractor = interactor;
        mExceptionHandler = new ExceptionHandler(this::getView);
    }

    public void attachView(VIEW view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        getRouter().detachContext();
    }

    public abstract void onViewResumed();

    public abstract void onViewPaused();

    public VIEW getView() {
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
