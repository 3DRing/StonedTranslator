package com.ringov.yatrnsltr.common_module.view;

import com.ringov.yatrnsltr.base.interfaces.BaseView;

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

    void showAllLanguages(List<String> languages);
}
