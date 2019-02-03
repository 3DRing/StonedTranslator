package com.ringov.stonedtrnsltr.data.storage_repo;

import com.ringov.stonedtrnsltr.storage_module.entities.StoredTranslationData;
import com.ringov.stonedtrnsltr.translation_module.entities.TranslationData;

import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public interface StorageRepository {
    Observable<List<StoredTranslationData>> loadHistory();

    Observable<List<StoredTranslationData>> loadFavorite();

    StoredTranslationData addHistoryItem(TranslationData translation);

    Completable deleteItem(long timeStamp);

    Observable<StoredTranslationData> undoLastDeletion(StoredTranslationData lastRemovedItem);

    Completable setFavorite(long timeStamp, boolean isFavorite);

    Completable pickPreviousTranslation(long timestamp);

    Observable<StoredTranslationData> subscribeToPreviousTranslationPicking();
}
