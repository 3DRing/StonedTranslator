package com.ringov.yatrnsltr.base.implementations;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.BasePresenter;
import com.ringov.yatrnsltr.base.interfaces.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public abstract class BaseFragment<PRESENTER extends BasePresenter> extends Fragment implements BaseView {

    private Unbinder unbinder;

    protected PRESENTER mPresenter;

    protected abstract PRESENTER providePresenter();

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = providePresenter();
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutRes(), container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        // todo implement
    }

    @Override
    public void hideLoading() {
        // todo implement
    }
}
