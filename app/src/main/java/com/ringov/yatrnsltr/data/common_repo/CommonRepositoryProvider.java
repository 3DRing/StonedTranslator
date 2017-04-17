package com.ringov.yatrnsltr.data.common_repo;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class CommonRepositoryProvider {
    private static CommonRepository storageRepository;

    public static CommonRepository getCommonRepository() {
        if (storageRepository == null) {
            storageRepository = new CommonRepositoryImpl();
        }
        return storageRepository;
    }
}
