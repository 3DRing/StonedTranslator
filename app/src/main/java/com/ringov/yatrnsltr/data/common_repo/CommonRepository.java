package com.ringov.yatrnsltr.data.common_repo;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public interface CommonRepository {
    Observable<Boolean> changeStonedMode(boolean stonedMode);

    Observable<Boolean> loadStonedMode();

    Observable<Boolean> subscribeToModeChanging();
}
