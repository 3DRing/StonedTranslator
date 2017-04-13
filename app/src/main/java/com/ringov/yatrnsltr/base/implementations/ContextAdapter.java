package com.ringov.yatrnsltr.base.implementations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ringov.yatrnsltr.base.routing.StoryDestination;

/**
 * Encapsulate communication between routers
 * and context.
 * <p>
 * Created by Sergey Koltsov on 14.04.2017.
 */
public class ContextAdapter {
    private Context context;

    public ContextAdapter(Context context) {
        this.context = context;
    }

    public void start(StoryDestination destination) {
        destination.start(context);
    }

    public void detach() {
        this.context = null;
    }
}