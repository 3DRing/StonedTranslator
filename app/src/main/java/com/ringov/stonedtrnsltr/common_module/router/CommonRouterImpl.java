package com.ringov.stonedtrnsltr.common_module.router;

import com.ringov.stonedtrnsltr.base.implementations.BaseRouterImpl;
import com.ringov.stonedtrnsltr.base.implementations.ContextAdapter;
import com.ringov.stonedtrnsltr.base.routing.StoryBoard;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class CommonRouterImpl extends BaseRouterImpl implements CommonRouter {
    public CommonRouterImpl(ContextAdapter context) {
        super(context);
    }

    @Override
    public void openChooseLanguageScreen() {
        start(StoryBoard.chooseLanguageScreen());
    }
}
