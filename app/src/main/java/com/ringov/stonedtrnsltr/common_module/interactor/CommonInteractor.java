package com.ringov.stonedtrnsltr.common_module.interactor;

import com.ringov.stonedtrnsltr.base.interfaces.BaseInteractor;
import com.ringov.stonedtrnsltr.common_module.entities.UILanguage;
import com.ringov.stonedtrnsltr.ui_entities.UILangPair;

import java.util.List;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public interface CommonInteractor extends BaseInteractor {

    Observable<Boolean> changeStonedMode();

    Observable<Boolean> loadStonedMode();

    Observable<List<UILanguage>> loadAllLanguages();

    Observable<UILangPair> loadLastLangPair();

    void saveLastLangPair();

    Observable<UILangPair> changeLangPair(UILangPair langPair);
}
