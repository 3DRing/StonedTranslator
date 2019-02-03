package com.ringov.stonedtrnsltr.base.interfaces;

import com.ringov.stonedtrnsltr.base.implementations.ContextAdapter;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface BaseRouter {
    void attachContext(ContextAdapter context);

    void detachContext();
}
