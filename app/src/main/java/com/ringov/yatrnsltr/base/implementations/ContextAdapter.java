package com.ringov.yatrnsltr.base.implementations;

import android.app.Activity;

import com.ringov.yatrnsltr.base.routing.StoryDestination;

/**
 * Encapsulate communication between routers
 * and context.
 * <p>
 * Created by Sergey Koltsov on 14.04.2017.
 */
public class ContextAdapter {
    private Activity activity;

    public ContextAdapter(Activity activity) {
        this.activity = activity;
    }

    public void start(StoryDestination destination) {
        destination.start(activity);
    }

    public void detach() {
        this.activity = null;
    }
}