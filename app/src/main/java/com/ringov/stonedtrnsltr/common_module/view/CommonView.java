package com.ringov.stonedtrnsltr.common_module.view;

import com.ringov.stonedtrnsltr.base.interfaces.BaseView;
import com.ringov.stonedtrnsltr.common_module.entities.UILanguage;
import com.ringov.stonedtrnsltr.ui_entities.UILangPair;

import java.util.List;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public interface CommonView extends BaseView {
    /**
     * Method is called by one of presenters
     * and force view to change every related representation
     * (for example, every text field) as it should be changed in a different mode
     *
     * @param enable
     */
    void setStonedMode(boolean enable);

    void showAllLanguages(List<UILanguage> languages);

    void showLanguagePair(UILangPair langPair);

    void requestInputFocus();

    void requestTranslate();
}
