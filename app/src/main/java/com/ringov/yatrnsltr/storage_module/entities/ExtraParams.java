package com.ringov.yatrnsltr.storage_module.entities;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class ExtraParams {
    private boolean favorite;
    private boolean changed;

    public ExtraParams(boolean favorite, boolean changed) {
        this.favorite = favorite;
        this.changed = changed;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isChanged() {
        return changed;
    }
}
