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
import android.widget.Toast;

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
        // if presenter already exists do not provide a new one (in case of screen rotation)
        mPresenter = providePresenter();
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

    /**
     * Method called from onViewCreated right after views initialization
     * Should be used by children of {@link BaseFragment} if some views (e.x. RecyclerView)
     * needs to be initialized before restoring {@link BaseViewState}
     */
    protected void initializeViewsBeforeRestoreState() {
        // to override
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(saveState(outState));
    }

    protected abstract void restoreState(Bundle bundle);

    protected abstract Bundle saveState(Bundle bundle);

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.onDestroy();
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
        Toast.makeText(getContext(), "Exception: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInternalException(String message) {
        // todo implement
        Toast.makeText(getContext(), "Internal exception: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnknownException(String message) {
        // todo implement
        Toast.makeText(getContext(), "Internal exception: " + message, Toast.LENGTH_SHORT).show();
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
