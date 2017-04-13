package com.ringov.yatrnsltr.base.implementations;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringov.yatrnsltr.base.BasePresenter;
import com.ringov.yatrnsltr.base.interfaces.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public abstract class BaseFragment<PRESENTER extends BasePresenter> extends Fragment implements BaseView {

    protected PRESENTER mPresenter;
    private Unbinder unbinder;

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
        initializeViewsBeforeRestoreState();
        setRetainInstance(true);
        restoreState(savedInstanceState);
    }

    protected abstract void initializeViewsBeforeRestoreState();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(saveState(outState));
    }

    protected abstract void restoreState(Bundle bundle);

    protected abstract Bundle saveState(Bundle bundle);

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
        mPresenter = null;
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


    @Override
    public void showKnownException(String message) {
        // todo implement
    }

    @Override
    public void showInternalException(String message) {
        // todo implement
    }

    @Override
    public void showUnknownException(String message) {
        // todo implement
    }

    protected static abstract class BaseViewState implements Parcelable {

        public static class Builder<STATE extends BaseViewState, BUILDER extends BaseViewState.Builder> {

            protected STATE state;

            public BUILDER getThis() {
                return (BUILDER) this;
            }

            public STATE build() {
                return state;
            }
        }
    }
}
