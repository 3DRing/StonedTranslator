package com.ringov.stonedtrnsltr.base.implementations;

import com.ringov.stonedtrnsltr.base.interfaces.BaseInteractor;
import com.ringov.stonedtrnsltr.data.common_repo.CommonRepositoryProvider;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public abstract class BaseInteractorImpl implements BaseInteractor {
    @Override
    public Observable<Boolean> subscribeToModeChanges() {
        return CommonRepositoryProvider.getCommonRepository().subscribeToModeChanging();
    }
}
