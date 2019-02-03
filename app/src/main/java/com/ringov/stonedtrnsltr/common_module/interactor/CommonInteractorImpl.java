package com.ringov.stonedtrnsltr.common_module.interactor;

import com.ringov.stonedtrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.stonedtrnsltr.common_module.entities.UILanguage;
import com.ringov.stonedtrnsltr.data.common_repo.CommonRepositoryProvider;
import com.ringov.stonedtrnsltr.data.lang.Language;
import com.ringov.stonedtrnsltr.translation_module.entities.LangPairData;
import com.ringov.stonedtrnsltr.ui_entities.UILangPair;

import java.util.List;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class CommonInteractorImpl extends BaseInteractorImpl implements CommonInteractor {

    private boolean stonedMode;

    @Override
    public Observable<UILangPair> loadLastLangPair() {
        return CommonRepositoryProvider.getCommonRepository().loadLastLangPair()
                .map(LangPairData::toUILangPair);
    }

    @Override
    public void saveLastLangPair() {
        CommonRepositoryProvider.getCommonRepository().saveLastLangPair();
    }

    @Override
    public Observable<UILangPair> changeLangPair(UILangPair langPair) {
        return CommonRepositoryProvider.getCommonRepository()
                .changeLangPair(new LangPairData(langPair))
                .map(LangPairData::toUILangPair);
    }

    @Override
    public Observable<Boolean> changeStonedMode() {
        return CommonRepositoryProvider.getCommonRepository()
                .changeStonedMode(!stonedMode)
                .doOnNext(stonedMode -> this.stonedMode = stonedMode);
    }

    @Override
    public Observable<Boolean> loadStonedMode() {
        return CommonRepositoryProvider.getCommonRepository()
                .loadStonedMode()
                .doOnNext(stonedMode -> this.stonedMode = stonedMode);
    }

    @Override
    public Observable<List<UILanguage>> loadAllLanguages() {
        return CommonRepositoryProvider.getCommonRepository()
                .loadAllLanguages()
                .flatMap(Observable::from)
                .map(this::convertToUILanguage)
                .toList();
    }

    private UILanguage convertToUILanguage(Language languages) {
        return new UILanguage(languages);
    }
}
