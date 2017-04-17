package com.ringov.yatrnsltr.storage_module.presenter;

import com.ringov.yatrnsltr.Utils;
import com.ringov.yatrnsltr.base.BasePresenter;
import com.ringov.yatrnsltr.storage_module.interactor.StorageInteractor;
import com.ringov.yatrnsltr.storage_module.router.StorageRouter;
import com.ringov.yatrnsltr.storage_module.view.StorageView;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StoragePresenter extends BasePresenter<StorageView, StorageRouter, StorageInteractor> {

    public StoragePresenter(StorageView view, StorageRouter router, StorageInteractor interactor) {
        super(view, router, interactor);
        loadHistory();

        subscribeToItemsInsertion();
        subscribeToModeChangedCommon();
    }

    private void loadHistory() {
        mSubscription.add(getInteractor().loadHistory()
                .compose(Utils.setRxSchedulers())
                .doOnSubscribe(getView()::showLoading)
                .doOnTerminate(getView()::hideLoading)
                .doOnError(throwable -> getView().hideLoading())
                .subscribe(getView()::showHistory, this::handleError));
    }

    private void subscribeToItemsInsertion() {
        mSubscription.add(getInteractor().itemInserted()
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::addToHistory, this::handleError));
    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewPaused() {

    }

    public void onItemsSwiped(int position) {
        mSubscription.add(getInteractor().deleteItem(position)
                .compose(Utils.setRxSchedulersForCompletable())
                .subscribe(getView()::itemDeleted, this::handleError));
    }

    public void onUndoDeletion(int position) {
        mSubscription.add(getInteractor().undoLastDeletion(position)
                .compose(Utils.setRxSchedulers())
                .subscribe(translation -> getView().returnItemBack(translation, position),
                        this::handleError));
    }

    protected void subscribeToModeChangedCommon() {
        mSubscription.add(getInteractor().subscribeToModeChanges()
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::setStonedMode));
    }
}
