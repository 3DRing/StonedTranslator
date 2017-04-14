package com.ringov.yatrnsltr.data.storage_repo;

import com.ringov.yatrnsltr.storage_module.entities.StoredTranslationData;

import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public interface StorageRepository {
    Observable<List<StoredTranslationData>> loadHistory(int from, int to);
    Completable saveHistoryItem(StoredTranslationData translation);
}
