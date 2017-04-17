package com.ringov.yatrnsltr.common_module.interactor;

import com.ringov.yatrnsltr.base.implementations.BaseInteractorImpl;
import com.ringov.yatrnsltr.data.common_repo.CommonRepositoryProvider;
import com.ringov.yatrnsltr.data.lang.Language;

import java.util.List;

import rx.Observable;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class CommonInteractorImpl extends BaseInteractorImpl implements CommonInteractor {

    private boolean stonedMode;

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
    public Observable<List<String>> loadAllLanguages() {
        return CommonRepositoryProvider.getCommonRepository()
                .loadAllLanguages()
                .flatMap(Observable::from)
                .map(this::convertToUILanguage)
                .toList();
    }

    private String convertToUILanguage(Language languages) {
        return languages.getFullOriginalName();
    }
}
