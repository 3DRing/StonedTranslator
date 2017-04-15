package com.ringov.yatrnsltr.data.storage_repo;

import io.realm.RealmObject;

/**
 * Created by Sergey Koltsov on 15.04.2017.
 */

public class RealmString extends RealmObject {
    public String value;

    public RealmString() {

    }

    public RealmString(String s) {
        this.value = s;
    }
}
