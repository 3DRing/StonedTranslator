package com.ringov.stonedtrnsltr.translation_module.router;

import com.ringov.stonedtrnsltr.base.interfaces.BaseRouter;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public interface TranslationRouter extends BaseRouter {
    void openYandexTranslatePage();

    void shareTranslation(String translation);
}
