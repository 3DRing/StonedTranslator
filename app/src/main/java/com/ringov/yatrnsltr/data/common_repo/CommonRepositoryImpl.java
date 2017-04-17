package com.ringov.yatrnsltr.data.common_repo;

import com.ringov.yatrnsltr.data.SharedPreferencesStorage;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class CommonRepositoryImpl implements CommonRepository {

    private BehaviorSubject<Boolean> changingModeEvents;

    CommonRepositoryImpl() {
        changingModeEvents = BehaviorSubject.create();
    }

    @Override
    public Observable<Boolean> changeStonedMode(boolean stonedMode) {
        return SharedPreferencesStorage.saveStonedMode(stonedMode)
                .doOnNext(changingModeEvents::onNext);
    }

    @Override
    public Observable<Boolean> loadStonedMode() {
        return SharedPreferencesStorage.loadStonedMode()
                .doOnNext(changingModeEvents::onNext);
    }

    @Override
    public Observable<Boolean> subscribeToModeChanging() {
        return changingModeEvents;
    }


}
