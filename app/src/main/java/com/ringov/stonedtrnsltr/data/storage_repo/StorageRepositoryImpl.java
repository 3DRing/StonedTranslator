package com.ringov.stonedtrnsltr.data.storage_repo;

import com.ringov.stonedtrnsltr.storage_module.entities.ExtraParams;
import com.ringov.stonedtrnsltr.storage_module.entities.StoredTranslationData;
import com.ringov.stonedtrnsltr.translation_module.entities.TranslationData;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Completable;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StorageRepositoryImpl implements StorageRepository {

    private static final String PRIMARY_LEY = "timestamp";
    private static final String FAVORITE = "favorite";

    private PublishSubject<StoredTranslationData> pickPreviousTranslationEvents;

    StorageRepositoryImpl() {
        pickPreviousTranslationEvents = PublishSubject.create();
    }

    @Override
    public Observable<List<StoredTranslationData>> loadHistory() {
        RealmResults<StoredTranslationData> results =
                Realm.getDefaultInstance().where(StoredTranslationData.class)
                        .findAll()
                        .sort(PRIMARY_LEY, Sort.DESCENDING);
        return Observable.just(Realm.getDefaultInstance().copyFromRealm(results));
    }

    @Override
    public Observable<List<StoredTranslationData>> loadFavorite() {
        RealmResults<StoredTranslationData> results =
                Realm.getDefaultInstance().where(StoredTranslationData.class)
                        .equalTo(FAVORITE, true)
                        .findAll()
                        .sort(PRIMARY_LEY, Sort.DESCENDING);
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

    @Override
    public Completable setFavorite(long timeStamp, boolean isFavorite) {
        StoredTranslationData record = Realm.getDefaultInstance().where(StoredTranslationData.class)
                .equalTo(PRIMARY_LEY, timeStamp)
                .findFirst();
        Realm.getDefaultInstance().beginTransaction();
        record.setFavorite(isFavorite);
        Realm.getDefaultInstance().commitTransaction();
        return Completable.complete();
    }

    @Override
    public Completable pickPreviousTranslation(long timestamp) {
        StoredTranslationData translation = Realm.getDefaultInstance().where(StoredTranslationData.class)
                .equalTo(PRIMARY_LEY, timestamp).findFirst();
        pickPreviousTranslationEvents.onNext(translation);
        return Completable.complete();
    }

    @Override
    public Observable<StoredTranslationData> subscribeToPreviousTranslationPicking() {
        return pickPreviousTranslationEvents;
    }
}
