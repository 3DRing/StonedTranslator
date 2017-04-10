package com.ringov.yatrnsltr.custom_views;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public abstract class ToggleImageButton extends android.support.v7.widget.AppCompatImageButton {

    private boolean mChecked;
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

    public void setChecked(boolean checked) {
        if (this.mChecked != checked) {
            this.toggle();
        }
    }

    protected void toggleAction(View v) {
        toggle();
        if (mListener != null) {
            mListener.onToggled(v, mChecked);
        }
    }

    public void setOnToggleListener(OnToggleListener listener) {
        this.mListener = listener;
    }

    public void toggle() {
        this.mChecked = !mChecked;
        handleImageAndBackground();
    }

    protected void handleImageAndBackground() {
        this.setImageResource(this.mChecked ? getOnImageRes() : getOffImageRes());
        this.setBackgroundResource(0);
    }

    @DrawableRes
    protected abstract int getOnImageRes();

    @DrawableRes
    protected abstract int getOffImageRes();

    public boolean isChecked() {
        return mChecked;
    }

    private void initializeOnClickListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAction(v);
            }
        });
    }

    public interface OnToggleListener {
        void onToggled(View v, boolean newState);
    }
}

