package com.ringov.yatrnsltr.base.implementations;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.interfaces.BaseView;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    @LayoutRes
    protected abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutRes(), container, false);
        return v;
    }
}
