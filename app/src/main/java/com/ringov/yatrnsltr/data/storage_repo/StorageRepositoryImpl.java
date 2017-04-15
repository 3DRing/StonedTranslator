package com.ringov.yatrnsltr.data.storage_repo;

import com.ringov.yatrnsltr.data.lang.Language;
import com.ringov.yatrnsltr.storage_module.entities.ExtraParams;
import com.ringov.yatrnsltr.storage_module.entities.StoredTranslationData;
import com.ringov.yatrnsltr.translation_module.entities.LangPairData;
import com.ringov.yatrnsltr.translation_module.entities.TranslationData;

import java.util.ArrayList;
import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StorageRepositoryImpl implements StorageRepository {

    // mock history db
    private List<StoredTranslationData> history;

    StorageRepositoryImpl() {
        history = new ArrayList<>();

        // fake history

        List<String> translations = new ArrayList<>();
        translations.add("hi");
        LangPairData langPair = new LangPairData(new Language(Language.SupportedLanguage.RU),
                new Language(Language.SupportedLanguage.EN));
        ExtraParams ep = new ExtraParams(true, true);
        StoredTranslationData std = new StoredTranslationData(new TranslationData("привет", translations, langPair), ep);

        history.add(std);
    }

    /**
     * @param from (inclusive)
     * @param to   (exclusive)
     * @return
     */
    @Override
    public Observable<List<StoredTranslationData>> loadHistory(int from, int to) {
        if (from >= history.size()) {
            return Observable.just(new ArrayList<>());
        }
        if (to >= history.size()) {
            to = history.size();
        }
        return Observable.just(history.subList(from, to));
    }

    @Override
    public Completable addHistoryItem(StoredTranslationData translation) {
        history.add(translation);
        return Completable.complete();
    }

}
