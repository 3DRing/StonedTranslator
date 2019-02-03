package com.ringov.stonedtrnsltr.storage_module.view;

import com.ringov.stonedtrnsltr.base.interfaces.BaseView;
import com.ringov.stonedtrnsltr.ui_entities.UITranslation;

import java.util.List;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public interface StorageView extends BaseView {

    StorageType getStorageType();

    void showTranslations(List<UITranslation> translations);

    void addToStorage(UITranslation transaction);

    void itemDeleted();

    void returnItemBack(UITranslation translation, int position);

    // todo create a new parent class StonedView and move this method there
    void setStonedMode(boolean enable);

    enum StorageType {HISTORY, FAVORITE}
}
