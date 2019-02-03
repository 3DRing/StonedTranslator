package com.ringov.stonedtrnsltr.translation_module.view;

/**
 * Callback that is used by root view to interact
 * with included module
 *
 * Created by Sergey Koltsov on 22.04.2017.
 */

public interface TranslateViewCallback {
    void requestInputFocus();
    void requestTranslate();
}
