package com.ringov.yatrnsltr.translation_module.router;

import com.ringov.yatrnsltr.base.implementations.BaseRouterImpl;
import com.ringov.yatrnsltr.base.implementations.ContextAdapter;
import com.ringov.yatrnsltr.base.routing.StoryBoard;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class TranslationRouterImpl extends BaseRouterImpl implements TranslationRouter {

    public TranslationRouterImpl(ContextAdapter context) {
        super(context);
    }

    @Override
    public void openYandexTranslatePage() {
        start(StoryBoard.yandexTranslateLink());
    }

    @Override
    public void shareTranslation(String translation) {
        start(StoryBoard.shareText(translation));
    }
}
