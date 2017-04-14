package com.ringov.yatrnsltr.storage_module.entities;

import com.ringov.yatrnsltr.translation_module.entities.TranslationData;

import java.util.List;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StoredTranslationData extends TranslationData {

    private ExtraParams params;

    protected StoredTranslationData(String original, List<String> translation) {
        super(original, translation);
    }

    public StoredTranslationData(TranslationData translation, ExtraParams params) {
        super(translation.getOriginal(), translation.getTranslation());
        this.params = params;
    }

    public boolean isFavorite(){
        return params.isFavorite();
    }

    public boolean isChanged(){
        return params.isChanged();
    }
}
