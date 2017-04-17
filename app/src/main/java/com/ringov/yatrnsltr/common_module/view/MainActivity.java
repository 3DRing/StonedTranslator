package com.ringov.yatrnsltr.common_module.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.ContextAdapter;
import com.ringov.yatrnsltr.common_module.interactor.CommonInteractorImpl;
import com.ringov.yatrnsltr.common_module.presenter.CommonPresenter;
import com.ringov.yatrnsltr.common_module.router.CommonRouterImpl;
import com.ringov.yatrnsltr.storage_module.view.StorageFragment;
import com.ringov.yatrnsltr.translation_module.view.TranslateFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements CommonView {

    @BindView(R.id.iv_bear)
    ImageView mIvBear;
    @BindView(R.id.fab_stoned_mode)
    FloatingActionButton mFabStonedMode;
    CommonPresenter mPresenter;

    @OnClick(R.id.fab_stoned_mode)
    void onStonedModeChangeClick() {
        mPresenter.onStonedModeChangedClicked();
    }

    protected CommonPresenter providePresenter() {
        return new CommonPresenter(this, new CommonRouterImpl(new ContextAdapter(this)), new CommonInteractorImpl());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeFragments();
        mPresenter = providePresenter();
    }

    private void initializeFragments() {
        commitFragmentIfNotExist(getSupportFragmentManager(), new TranslateFragment(), R.id.translate_content);
        commitFragmentIfNotExist(getSupportFragmentManager(), new StorageFragment(), R.id.storage_content);
    }

    private boolean commitFragmentIfNotExist(FragmentManager fm, Fragment fragment, @IdRes int fragmentContainer) {
        if (fm.findFragmentByTag(fragment.getClass().getSimpleName()) == null) {
            fm.beginTransaction().replace(fragmentContainer, fragment, fragment.getClass().getSimpleName()).commit();
            return true;
        } else {
            // do not commit another instance of a fragment
            // if there already is the same one in manager
            return false;
        }
    }

    @Override
    public void showLoading() {
        // nothing, maybe in future
    }

    @Override
    public void hideLoading() {
        // nothing, maybe in future
    }

    @Override
    public void showKnownException(String message) {
        // nothing, maybe in future
    }

    @Override
    public void showInternalException(String message) {
        // nothing, maybe in future
    }

    @Override
    public void showUnknownException(String message) {
        // nothing, maybe in future
    }

    @Override
    public void setStonedMode(boolean enable) {
        mFabStonedMode.setImageResource(enable ? R.mipmap.mipmap_stoned_bear_white_true
                : R.mipmap.mipmap_stoned_bear_white_false);
        mIvBear.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
    }
}
