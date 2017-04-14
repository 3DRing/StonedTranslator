package com.ringov.yatrnsltr.storage_module.interactor;

import com.ringov.yatrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.yatrnsltr.data.storage_repo.StorageRepositoryProvider;
import com.ringov.yatrnsltr.storage_module.entities.StoredTranslationData;
import com.ringov.yatrnsltr.ui_entities.UITranslation;

import java.util.List;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StorageInteractorImpl extends BaseInteractorImpl implements StorageInteractor {

    private static final int DEFAULT_NUMBER_OF_LOADINGS = 20;

    private int crtFrom;
    private int crtTo;

    public StorageInteractorImpl() {
        crtFrom = 0;
        crtTo = DEFAULT_NUMBER_OF_LOADINGS;
    }

    @Override
    public Observable<List<UITranslation>> loadHistory() {
        return StorageRepositoryProvider.getStorageRepository()
                .loadHistory(crtFrom, crtTo)
                .flatMap(Observable::from)
                .map(this::convertToUITranslation)
                .toList();
    }

    private UITranslation convertToUITranslation(StoredTranslationData storedTranslationData) {
        UITranslation uiTranslation = new UITranslation(storedTranslationData, storedTranslationData.getLangPair());
        uiTranslation.setFavorite(storedTranslationData.isFavorite());
        uiTranslation.setChanged(storedTranslationData.isChanged());
        return uiTranslation;
    }
}
