package com.ringov.stonedtrnsltr.storage_module.interactor;

import com.ringov.stonedtrnsltr.base.interfaces.BaseInteractor;
import com.ringov.stonedtrnsltr.ui_entities.UITranslation;

import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public interface StorageInteractor extends BaseInteractor {
    Observable<List<UITranslation>> loadHistory();

    Observable<List<UITranslation>> loadFavorite();

    Observable<UITranslation>  itemInserted();

    Completable deleteItem(long timeStamp);

    Observable<UITranslation> undoLastDeletion();

    Completable setFavorite(long timeStamp, boolean isFavorite);

    Completable pickPreviousTranslation(long id);
}
