package com.ringov.yatrnsltr.custom_views;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public abstract class ToggleImageButton extends android.support.v7.widget.AppCompatImageButton {

    protected boolean mChecked;
    private OnToggleListener mListener;

    public ToggleImageButton(Context context) {
        super(context);
        handleImageAndBackground();
        initializeOnClickListener();
    }

    public ToggleImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleImageAndBackground();
        initializeOnClickListener();
    }

    public ToggleImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        handleImageAndBackground();
        initializeOnClickListener();
    }

    public void setOnToggleListener(OnToggleListener listener) {
        this.mListener = listener;
    }

    public void toggle() {
        this.mChecked = !mChecked;
        handleImageAndBackground();
        if (mListener != null) {
            mListener.onToggled(mChecked);
        }
    }

    protected void handleImageAndBackground() {
        this.setImageResource(this.mChecked ? getOnImageRes() : getOffImageRes());
        this.setBackgroundResource(0);
    }

    // todo learn how to set attributes via xml and not create new child for different drawables
    @DrawableRes
    protected abstract int getOnImageRes();

    // todo learn how to set attributes via xml and not create new child for different drawables
    @DrawableRes
    protected abstract int getOffImageRes();

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        if (this.mChecked != checked) {
            this.toggle();
        }
    }

    private void initializeOnClickListener() {
        this.setOnClickListener(v -> toggle());
    }

    public interface OnToggleListener {
        void onToggled(boolean newState);
    }
}

