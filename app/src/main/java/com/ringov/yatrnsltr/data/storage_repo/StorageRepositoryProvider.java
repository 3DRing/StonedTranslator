package com.ringov.yatrnsltr.data.storage_repo;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StorageRepositoryProvider {

    private static StorageRepositoryImpl storageRepository;

    public static StorageRepository getStorageRepository() {
        if (storageRepository == null) {
            storageRepository = new StorageRepositoryImpl();
        }
        return storageRepository;
    }

}
