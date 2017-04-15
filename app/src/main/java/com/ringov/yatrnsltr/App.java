package com.ringov.yatrnsltr;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class App extends Application {
    private static App appSingleton;

    public void onCreate() {
        super.onCreate();
        App.appSingleton = this;
        Realm.init(this);
    }

    public static Context getAppContext() {
        return App.appSingleton.getApplicationContext();
    }
}
