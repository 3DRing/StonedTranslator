package com.ringov.yatrnsltr.storage_module.presenter;

import com.ringov.yatrnsltr.Utils;
import com.ringov.yatrnsltr.base.BasePresenter;
import com.ringov.yatrnsltr.storage_module.interactor.StorageInteractor;
import com.ringov.yatrnsltr.storage_module.router.StorageRouter;
import com.ringov.yatrnsltr.storage_module.view.StorageView;
import com.ringov.yatrnsltr.ui_entities.UITranslation;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StoragePresenter extends BasePresenter<StorageView, StorageRouter, StorageInteractor> {

    int lastRemovedPosition;

    public StoragePresenter(StorageView view, StorageRouter router, StorageInteractor interactor) {
        super(view, router, interactor);

        loadStorage(getView().getStorageType());

        subscribeToItemsInsertion();
        subscribeToModeChangedCommon();
    }

    private void loadStorage(StorageView.StorageType type) {
        Observable<List<UITranslation>> observable;
        switch (type) {
            case HISTORY:
                observable = getInteractor().loadHistory();
                break;
            case FAVORITE:
                observable = getInteractor().loadFavorite();
                break;
            default:
                observable = Observable.just(new ArrayList<>());
                break;
        }
        mSubscription.add(observable
                .compose(Utils.setRxSchedulers())
                .doOnSubscribe(getView()::showLoading)
                .doOnTerminate(getView()::hideLoading)
                .doOnError(throwable -> getView().hideLoading())
                .subscribe(getView()::showTranslations, this::handleError));
    }

    private void subscribeToItemsInsertion() {
        mSubscription.add(getInteractor().itemInserted()
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::addToStorage, this::handleError));
    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewPaused() {

    }

    public void onItemsSwiped(UITranslation translation, int position) {
        lastRemovedPosition = position;
        mSubscription.add(getInteractor().deleteItem(translation.getId())
                .compose(Utils.setRxSchedulersForCompletable())
                .subscribe(getView()::itemDeleted, this::handleError));
    }

    public void onUndoDeletion() {
        mSubscription.add(getInteractor().undoLastDeletion()
                .compose(Utils.setRxSchedulers())
                .subscribe(translation -> getView().returnItemBack(translation, lastRemovedPosition),
                        this::handleError));
    }

    protected void subscribeToModeChangedCommon() {
        mSubscription.add(getInteractor().subscribeToModeChanges()
                .compose(Utils.setRxSchedulers())
                .subscribe(getView()::setStonedMode));
    }

    public void onFavoriteClicked(UITranslation translation, boolean isFavorite) {
        mSubscription.add(getInteractor().setFavorite(translation.getId(), isFavorite)
                .compose(Utils.setRxSchedulersForCompletable())
                .subscribe(() -> {
                    // nothing to do in view, because items handled icon changing by itself
                }, this::handleError));
    }
}
