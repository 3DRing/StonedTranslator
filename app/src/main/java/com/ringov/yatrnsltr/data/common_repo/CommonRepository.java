package com.ringov.yatrnsltr.data.common_repo;

import com.ringov.yatrnsltr.data.lang.Language;
import com.ringov.yatrnsltr.translation_module.entities.LangPairData;

import java.util.List;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public interface CommonRepository {
    Observable<LangPairData> loadLastLangPair();

    void saveLastLangPair();

    Observable<Boolean> changeStonedMode(boolean stonedMode);

    Observable<Boolean> loadStonedMode();

    Observable<List<Language>> loadAllLanguages();


    // todo move to separate interface
    Observable<Boolean> subscribeToModeChanging();

    // todo move to separate interface
    Observable<LangPairData> subscribeToLangPairChanging();

    Observable<LangPairData> changeLangPair(LangPairData langPair);
}
