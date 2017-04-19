package com.ringov.yatrnsltr.base.implementations;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringov.yatrnsltr.MessageDialogHelper;
import com.ringov.yatrnsltr.R;
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
        mPresenter = providePresenter();
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
    public void onResume() {
        super.onResume();
        mPresenter.onViewResumed();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onViewPaused();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mPresenter = null;
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        // implemented by children
    }

    @Override
    public void hideLoading() {
        // implemented by children
    }


    @Override
    public void showKnownException(String message) {
        MessageDialogHelper.getErrorDialog(getActivity(), getString(R.string.exception), message).show();
    }

    @Override
    public void showInternalException(String message) {
        MessageDialogHelper.getErrorDialog(getActivity(), getString(R.string.exception),
                getString(R.string.internal_exception_appeared)).show();
    }

    @Override
    public void showUnknownException(String message) {
        MessageDialogHelper.getErrorDialog(getActivity(), getString(R.string.exception),
                getString(R.string.unknown_exception_appeared)).show();
    }

    @Override
    public void showInternetConnectionException(String message) {
        MessageDialogHelper.getErrorDialog(getActivity(), getString(R.string.exception),
                getString(R.string.no_internet_connection_text)).show();
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
