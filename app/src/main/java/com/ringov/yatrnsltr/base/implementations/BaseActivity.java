package com.ringov.yatrnsltr.base.implementations;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.ringov.yatrnsltr.MessageDialogHelper;
import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.BasePresenter;
import com.ringov.yatrnsltr.base.interfaces.BaseView;

import butterknife.ButterKnife;

/**
 * Created by Sergey Koltsov on 18.04.2017.
 */

public abstract class BaseActivity<PRESENTER extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected PRESENTER mPresenter;

    protected abstract PRESENTER providePresenter();

    protected abstract @LayoutRes int getLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        mPresenter = providePresenter();
    }

    @Override
    public void showLoading() {
        // nothing
    }

    @Override
    public void hideLoading() {
        // nothing
    }

    @Override
    public void showKnownException(String message) {
        MessageDialogHelper.getErrorDialog(this, getString(R.string.exception), message);
    }

    @Override
    public void showInternalException(String message) {
        MessageDialogHelper.getErrorDialog(this, getString(R.string.exception),
                getString(R.string.internal_exception_appeared));
    }

    @Override
    public void showUnknownException(String message) {
        MessageDialogHelper.getErrorDialog(this, getString(R.string.exception),
                getString(R.string.unknown_exception_appeared));
    }
}
