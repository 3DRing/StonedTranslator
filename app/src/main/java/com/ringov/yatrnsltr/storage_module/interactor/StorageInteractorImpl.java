package com.ringov.yatrnsltr.storage_module.interactor;

import com.ringov.yatrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.yatrnsltr.data.stoned_service.StonedConvertingService;
import com.ringov.yatrnsltr.data.storage_repo.StorageRepositoryProvider;
import com.ringov.yatrnsltr.data.translation_repo.TranslationRepositoryProvider;
import com.ringov.yatrnsltr.exceptions.base.InternalException;
import com.ringov.yatrnsltr.storage_module.entities.StoredTranslationData;
import com.ringov.yatrnsltr.translation_module.entities.TranslationData;
import com.ringov.yatrnsltr.ui_entities.UITranslation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Completable;
import rx.Observable;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StorageInteractorImpl extends BaseInteractorImpl implements StorageInteractor {

    private static final int DEFAULT_NUMBER_OF_LOADINGS = 20;
    // this list is used for storing current state of history field
    // and manage interactions with this list (deletion, changing options etc)
    Map<Long, StoredTranslationData> crtHistoryRecords;
    StoredTranslationData lastRemovedItem;
    long lastRemovedTimeStamp;
    private int crtFrom;
    private int crtTo;

    public StorageInteractorImpl() {
        // not used, declared for future needs
        crtFrom = 0;
        crtTo = DEFAULT_NUMBER_OF_LOADINGS;

        crtHistoryRecords = new HashMap<>();
    }

    @Override
    public Observable<List<UITranslation>> loadHistory() {
        return StorageRepositoryProvider.getStorageRepository()
                .loadHistory()
                .flatMap(Observable::from)
                .doOnNext(translation -> crtHistoryRecords.put(translation.getTimestamp(), translation))
                .map(this::applyStonedMode)
                .toList();
    }

    @Override
    public Observable<List<UITranslation>> loadFavorite() {
        return Observable.from(crtHistoryRecords.values())
                .filter(StoredTranslationData::isFavorite)
                .map(this::applyStonedMode)
                .toList();
    }

    @Override
    public Observable<UITranslation> itemInserted() {
        return TranslationRepositoryProvider.getTranslationRepository()
                .subscribeToTranslation()
                .map(StorageRepositoryProvider.getStorageRepository()::addHistoryItem)
                .doOnNext(translation -> crtHistoryRecords.put(translation.getTimestamp(), translation))
                .map(this::applyStonedMode);
    }

    @Override
    public Completable deleteItem(long timeStamp) {
        return StorageRepositoryProvider.getStorageRepository()
                .deleteItem(timeStamp)
                .doOnSubscribe(subscription -> lastRemovedItem = crtHistoryRecords.get(timeStamp))
                .doOnSubscribe(subscription -> lastRemovedTimeStamp = timeStamp)
                .doOnSubscribe(subscription -> crtHistoryRecords.remove(timeStamp));
    }

    @Override
    public Observable<UITranslation> undoLastDeletion() {
        if (lastRemovedItem == null) {
            return Observable.error(
                    new InternalException("undo of deletion was called from inappropriate place"));
            // maybe its better to specify this exception in future
        }
        return StorageRepositoryProvider.getStorageRepository()
                .undoLastDeletion(lastRemovedItem)
                .doOnNext(translation -> crtHistoryRecords.put(lastRemovedTimeStamp, translation))
                .map(this::applyStonedMode);
    }

    @Override
    public Completable setFavorite(long timeStamp, boolean isFavorite) {
        return StorageRepositoryProvider.getStorageRepository()
                .setFavorite(timeStamp, isFavorite);
    }

    private UITranslation convertToUITranslation(StoredTranslationData storedTranslationData, TranslationData stonedTranslation) {
        UITranslation uiTranslation = new UITranslation(storedTranslationData, storedTranslationData.getLangPair());
        uiTranslation.setChangedData(stonedTranslation.getOriginal(), stonedTranslation.getTranslations());
        uiTranslation.setFavorite(storedTranslationData.isFavorite());
        uiTranslation.setChanged(storedTranslationData.isChanged());
        return uiTranslation;
    }

    private UITranslation applyStonedMode(StoredTranslationData translation) {
        TranslationData tmp = new TranslationData(translation.getOriginal(),
                translation.getTranslations(),
                translation.getLangPair());
        TranslationData stoned = StonedConvertingService.convert(tmp);
        return this.convertToUITranslation(translation, stoned);
    }
}
