package com.ringov.yatrnsltr.storage_module.interactor;

import com.ringov.yatrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.yatrnsltr.data.stoned_service.StonedConvertingService;
import com.ringov.yatrnsltr.data.storage_repo.StorageRepositoryProvider;
import com.ringov.yatrnsltr.data.translation_repo.TranslationRepositoryProvider;
import com.ringov.yatrnsltr.exceptions.base.InternalException;
import com.ringov.yatrnsltr.storage_module.entities.StoredTranslationData;
import com.ringov.yatrnsltr.translation_module.entities.TranslationData;
import com.ringov.yatrnsltr.ui_entities.UITranslation;

import java.util.ArrayList;
import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StorageInteractorImpl extends BaseInteractorImpl implements StorageInteractor {

    private static final int DEFAULT_NUMBER_OF_LOADINGS = 20;
    // this list is used for storing current state of history field
    // and manage interactions with this list (deletion, changing options etc)
    List<StoredTranslationData> crtHistoryRecords;
    StoredTranslationData lastRemovedItem;
    int lastRemovedPosition;
    private int crtFrom;
    private int crtTo;

    public StorageInteractorImpl() {
        // not used, declared for future needs
        crtFrom = 0;
        crtTo = DEFAULT_NUMBER_OF_LOADINGS;

        crtHistoryRecords = new ArrayList<>();
    }

    @Override
    public Observable<List<UITranslation>> loadHistory() {
        return StorageRepositoryProvider.getStorageRepository()
                .loadHistory()
                .doOnNext(crtHistoryRecords::addAll)
                .flatMap(Observable::from)
                .map(this::applyStonedMode)
                .toList();
    }

    @Override
    public Observable<UITranslation> itemInserted() {
        return TranslationRepositoryProvider.getTranslationRepository()
                .subscribeToTranslation()
                .map(StorageRepositoryProvider.getStorageRepository()::addHistoryItem)
                .doOnNext(crtHistoryRecords::add)
                .map(this::applyStonedMode);
    }

    @Override
    public Completable deleteItem(int position) {
        return StorageRepositoryProvider.getStorageRepository()
                .deleteItem(crtHistoryRecords.get(position))
                .doOnSubscribe(subscription -> lastRemovedItem = crtHistoryRecords.get(position))
                .doOnSubscribe(subscription -> lastRemovedPosition = position)
                .doOnSubscribe(subscription -> crtHistoryRecords.remove(position));
    }

    @Override
    public Observable<UITranslation> undoLastDeletion(int position) {
        if (lastRemovedItem == null) {
            return Observable.error(
                    new InternalException("undo of deletion was called from inappropriate place"));
            // maybe its better to specify this exception in future
        }
        return StorageRepositoryProvider.getStorageRepository()
                .undoLastDeletion(lastRemovedItem)
                .doOnNext(translation -> crtHistoryRecords.add(position, translation))
                .map(this::applyStonedMode);
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
