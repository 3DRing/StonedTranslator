package com.ringov.yatrnsltr.storage_module.entities;

import com.ringov.yatrnsltr.data.lang.Language;
import com.ringov.yatrnsltr.data.storage_repo.RealmString;
import com.ringov.yatrnsltr.translation_module.entities.LangPairData;
import com.ringov.yatrnsltr.translation_module.entities.TranslationData;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

// todo create general interface for any Translation representation on the model side
public class StoredTranslationData extends RealmObject {

    private String originalText;
    private RealmList<RealmString> translations;
    private String sourceLang;
    private String targetLang;
    private boolean favorite;
    private boolean changed;

    public String getOriginal() {
        return originalText;
    }

    public List<String> getTranslations() {
        List<String> list = new ArrayList<>();
        for (RealmString s :
                translations) {
            list.add(s.value);
        }
        return list;
    }

    public LangPairData getLangPair() {
        return new LangPairData(new Language(sourceLang), new Language(targetLang));
    }

    public StoredTranslationData() {

    }

    public StoredTranslationData(TranslationData translation, ExtraParams params) {
        this.originalText = translation.getOriginal();
        this.translations = new RealmList<>();
        for (String s :
                translation.getTranslations()) {
            translations.add(new RealmString(s));
        }
        this.sourceLang = translation.getLangPair().getSourceLang().getShortName();
        this.targetLang = translation.getLangPair().getTargetLang().getShortName();
        this.favorite = params.isFavorite();
        this.changed = params.isChanged();
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isChanged() {
        return changed;
    }
}
