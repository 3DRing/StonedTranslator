package com.ringov.yatrnsltr;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class App extends Application {
    private static App appSingleton;

    public void onCreate() {
        super.onCreate();
        App.appSingleton = this;
        initRealm();
    }

    private void initRealm() {
        Realm.init(getAppContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static Context getAppContext() {
        return App.appSingleton.getApplicationContext();
    }
}
