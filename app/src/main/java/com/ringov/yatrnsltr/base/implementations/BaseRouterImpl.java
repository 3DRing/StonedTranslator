package com.ringov.yatrnsltr.base.implementations;

import com.ringov.yatrnsltr.base.interfaces.BaseRouter;
import com.ringov.yatrnsltr.base.routing.StoryDestination;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class BaseRouterImpl implements BaseRouter {

    private ContextAdapter context;

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

    protected void start(StoryDestination destination) {
        context.start(destination);
    }
}
