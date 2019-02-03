package com.ringov.stonedtrnsltr.base.interfaces;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface BaseInteractor {
    Observable<Boolean> subscribeToModeChanges();
}
