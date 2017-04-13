package com.ringov.yatrnsltr.base.interfaces;

import com.ringov.yatrnsltr.base.implementations.ContextAdapter;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface BaseRouter {
    void attachContext(ContextAdapter context);

    void detachContext();
}
