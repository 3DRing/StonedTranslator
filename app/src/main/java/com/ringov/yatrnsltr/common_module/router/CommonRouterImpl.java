package com.ringov.yatrnsltr.common_module.router;

import com.ringov.yatrnsltr.base.implementations.BaseRouterImpl;
import com.ringov.yatrnsltr.base.implementations.ContextAdapter;
import com.ringov.yatrnsltr.base.routing.StoryBoard;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class CommonRouterImpl extends BaseRouterImpl implements CommonRouter {
    public CommonRouterImpl(ContextAdapter context) {
        super(context);
    }

    @Override
    public void openChooseLanguageScreen() {
        context.start(StoryBoard.chooseLanguageScreen());
    }
}
