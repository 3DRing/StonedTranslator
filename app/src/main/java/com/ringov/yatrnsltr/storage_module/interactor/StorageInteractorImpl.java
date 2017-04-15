package com.ringov.yatrnsltr.storage_module.interactor;

import com.ringov.yatrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.yatrnsltr.data.storage_repo.StorageRepositoryProvider;
import com.ringov.yatrnsltr.data.translation_repo.TranslationRepositoryProvider;
import com.ringov.yatrnsltr.storage_module.entities.StoredTranslationData;
import com.ringov.yatrnsltr.ui_entities.UITranslation;

import java.util.ArrayList;
import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StorageInteractorImpl extends BaseInteractorImpl implements StorageInteractor {

    // this list is used for storing current state of history field
    // and manage interactions with this list (deletion, changing options etc)
    List<StoredTranslationData> crtHistoryRecords;

    private static final int DEFAULT_NUMBER_OF_LOADINGS = 20;

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
                .map(this::convertToUITranslation)
                .toList();
    }

    @Override
    public Observable<UITranslation> itemInserted() {
        return TranslationRepositoryProvider.getTranslationRepository()
                .subscribeToTranslation()
                .map(StorageRepositoryProvider.getStorageRepository()::addHistoryItem)
                .doOnNext(crtHistoryRecords::add)
                .map(this::convertToUITranslation);
    }

    @Override
    public Completable deleteItem(int position) {
        return StorageRepositoryProvider.getStorageRepository()
                .deleteItem(crtHistoryRecords.get(position))
                .doOnCompleted(() -> crtHistoryRecords.remove(position));
    }

    private UITranslation convertToUITranslation(StoredTranslationData storedTranslationData) {
        UITranslation uiTranslation = new UITranslation(storedTranslationData, storedTranslationData.getLangPair());
        uiTranslation.setFavorite(storedTranslationData.isFavorite());
        uiTranslation.setChanged(storedTranslationData.isChanged());
        return uiTranslation;
    }
}
