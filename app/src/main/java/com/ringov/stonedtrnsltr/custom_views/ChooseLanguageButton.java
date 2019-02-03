package com.ringov.stonedtrnsltr.custom_views;

import android.content.Context;
import android.util.AttributeSet;

import com.ringov.stonedtrnsltr.R;

/**
 * Created by Sergey Koltsov on 19.04.2017.
 */

public class ChooseLanguageButton extends ToggleImageButton {
    public ChooseLanguageButton(Context context) {
        super(context);
    }

    public ChooseLanguageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseLanguageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getOnImageRes() {
        return R.drawable.ic_arrow_forward_24dp;
    }

    @Override
    protected int getOffImageRes() {
        return 0;
    }

    @Override
    protected void handleImageAndBackground() {
        super.handleImageAndBackground();
        setBackgroundResource(mChecked ? R.drawable.background_choose_lang_on : 0);
    }
}
