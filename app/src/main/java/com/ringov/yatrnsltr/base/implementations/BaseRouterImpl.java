package com.ringov.yatrnsltr.base.implementations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ringov.yatrnsltr.base.interfaces.BaseRouter;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class BaseRouterImpl implements BaseRouter {

    protected ContextAdapter context;

    public BaseRouterImpl(ContextAdapter context) {
        attachContext(context);
    }

    @Override
    public void attachContext(ContextAdapter context) {
        this.context = context;
    }

    @Override
    public void detachContext() {
        this.context.detach();
        this.context = null;
    }
}
