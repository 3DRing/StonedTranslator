package com.ringov.yatrnsltr.common_module.interactor;

import com.ringov.yatrnsltr.base.interfaces.BaseInteractor;

import java.util.List;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public interface CommonInteractor extends BaseInteractor {
    Observable<Boolean> changeStonedMode();

    Observable<Boolean> loadStonedMode();

    Observable<List<String>> loadAllLanguages();
}
