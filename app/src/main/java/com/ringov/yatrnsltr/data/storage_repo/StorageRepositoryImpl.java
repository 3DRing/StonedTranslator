package com.ringov.yatrnsltr.data.storage_repo;

import com.google.common.annotations.Beta;
import com.ringov.yatrnsltr.storage_module.entities.ExtraParams;
import com.ringov.yatrnsltr.storage_module.entities.StoredTranslationData;
import com.ringov.yatrnsltr.translation_module.entities.TranslationData;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Completable;
import rx.Observable;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StorageRepositoryImpl implements StorageRepository {

    private static final String PRIMARY_LEY = "timestamp";

    @Override
    public Observable<List<StoredTranslationData>> loadHistory() {
        RealmResults<StoredTranslationData> results =
                Realm.getDefaultInstance().where(StoredTranslationData.class).findAll();
        return Observable.just(Realm.getDefaultInstance().copyFromRealm(results));
    }

    /**
     * Params are not used so far
     * Created for future improvements and potential internet requests
     *
     * @param from (inclusive)
     * @param to   (exclusive)
     * @return
     */
    @Beta
    private Observable<List<StoredTranslationData>> loadHistory(int from, int to) {
        return loadHistory();
    }

    @Override
    public StoredTranslationData addHistoryItem(TranslationData translation) {
        StoredTranslationData storedTranslation =
                new StoredTranslationData(System.currentTimeMillis(), translation, new ExtraParams(false, false));
        Realm.getDefaultInstance().executeTransaction(realm -> realm.insert(storedTranslation));
        return storedTranslation;
    }

    @Override
    public Completable deleteItem(long timeStamp) {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            realm.where(StoredTranslationData.class)
                    .equalTo(PRIMARY_LEY, timeStamp)
                    .findAll().deleteAllFromRealm();
        });
        return Completable.complete();
    }

    @Override
    public Observable<StoredTranslationData> undoLastDeletion(StoredTranslationData lastRemovedItem) {
        Realm.getDefaultInstance().executeTransaction(realm -> realm.insert(lastRemovedItem));
        return Observable.just(lastRemovedItem);
    }

}
