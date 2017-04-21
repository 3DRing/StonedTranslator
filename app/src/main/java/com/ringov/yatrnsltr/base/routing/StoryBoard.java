package com.ringov.yatrnsltr.base.routing;

import android.content.Intent;
import android.net.Uri;

import com.ringov.yatrnsltr.common_module.view.ChooseLanguageActivity;

/**
 * Created by Sergey Koltsov on 14.04.2017.
 */

public class StoryBoard {
    public static StoryDestination yandexTranslateLink() {
        return context -> {
            String url = "https://www.requestTranslate.yandex.ru";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        };
    }

    public static StoryDestination chooseLanguageScreen() {
        return context -> {
            Intent i = new Intent(context, ChooseLanguageActivity.class);
            context.startActivity(i);
        };
    }
}
