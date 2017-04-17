package com.ringov.yatrnsltr.base.implementations;

import com.ringov.yatrnsltr.base.interfaces.BaseInteractor;
import com.ringov.yatrnsltr.data.common_repo.CommonRepositoryProvider;

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
