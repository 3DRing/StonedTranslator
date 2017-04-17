package com.ringov.yatrnsltr.common_module.interactor;

import com.ringov.yatrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.yatrnsltr.data.common_repo.CommonRepositoryProvider;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class CommonInteractorImpl extends BaseInteractorImpl implements CommonInteractor {

    private boolean stonedMode;

    @Override
    public Observable<Boolean> changeStonedMode() {
        return CommonRepositoryProvider.getCommonRepository()
                .changeStonedMode(!stonedMode)
                .doOnNext(stonedMode -> this.stonedMode = stonedMode);
    }

    @Override
    public Observable<Boolean> loadStonedMode() {
        return CommonRepositoryProvider.getCommonRepository()
                .loadStonedMode()
                .doOnNext(stonedMode -> this.stonedMode = stonedMode);
    }
}
