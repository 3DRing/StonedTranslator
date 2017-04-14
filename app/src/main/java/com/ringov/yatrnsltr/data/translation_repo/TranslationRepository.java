package com.ringov.yatrnsltr.data.translation_repo;

import com.ringov.yatrnsltr.translation_module.entity.LangPairData;
import com.ringov.yatrnsltr.translation_module.entity.TranslationData;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UILangPair;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface TranslationRepository {
    Observable<TranslationData> translate(String text, LangPairData langPair);

    Observable<LangPairData> loadLastLangPair();

    void saveLastLangPair(LangPairData langPair);
}
