package com.ringov.yatrnsltr.translation_module.view;

import com.ringov.yatrnsltr.base.interfaces.BaseView;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UITranslation;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface TranslationView extends BaseView {
    void showTranslation(UITranslation translation);

    void showMoreOptions();

    void hideMoreOptions();
}
