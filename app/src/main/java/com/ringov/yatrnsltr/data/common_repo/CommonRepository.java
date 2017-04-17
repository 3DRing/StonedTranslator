package com.ringov.yatrnsltr.data.common_repo;

import com.ringov.yatrnsltr.data.lang.Language;

import java.util.List;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public interface CommonRepository {
    Observable<Boolean> changeStonedMode(boolean stonedMode);

    Observable<Boolean> loadStonedMode();

    Observable<Boolean> subscribeToModeChanging();

    Observable<List<Language>> loadAllLanguages();
}
