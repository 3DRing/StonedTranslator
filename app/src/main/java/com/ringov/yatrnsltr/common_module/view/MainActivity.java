package com.ringov.yatrnsltr.common_module.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.BaseActivity;
import com.ringov.yatrnsltr.base.implementations.ContextAdapter;
import com.ringov.yatrnsltr.common_module.entities.UILanguage;
import com.ringov.yatrnsltr.common_module.interactor.CommonInteractorImpl;
import com.ringov.yatrnsltr.common_module.presenter.CommonPresenter;
import com.ringov.yatrnsltr.common_module.router.CommonRouterImpl;
import com.ringov.yatrnsltr.storage_module.view.FavoriteFragment;
import com.ringov.yatrnsltr.storage_module.view.HistoryFragment;
import com.ringov.yatrnsltr.translation_module.view.TranslateFragment;
import com.ringov.yatrnsltr.ui_entities.UILangPair;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<CommonPresenter> implements CommonView {

    @BindView(R.id.ll_stoned_bear)
    View mStonedBear;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private boolean stonedModeEnabled;

    @Deprecated // button will be used for other reasons
    @OnClick(R.id.fab_stoned_mode)
    void onFloatingButtonClick() {

    }

    @Override
    protected CommonPresenter providePresenter() {
        return new CommonPresenter(this, new CommonRouterImpl(new ContextAdapter(this)), new CommonInteractorImpl());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFragments();
        initializeTabLayout();
    }

    private void initializeTabLayout() {

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        commitFragmentIfNotExist(getSupportFragmentManager(), new HistoryFragment(), R.id.storage_content);
                        break;
                    case 1:
                        commitFragmentIfNotExist(getSupportFragmentManager(), new FavoriteFragment(), R.id.storage_content);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (stonedModeEnabled) {
            inflater.inflate(R.menu.stoned_menu_true, menu);
        } else {
            inflater.inflate(R.menu.stoned_menu_false, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_stoned_mode:
                mPresenter.onStonedModeChangedClicked();
                break;
            case R.id.menu_languages:
                mPresenter.onLanguagesClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeFragments() {
        commitFragmentIfNotExist(getSupportFragmentManager(), new TranslateFragment(), R.id.translate_content);
        commitFragmentIfNotExist(getSupportFragmentManager(), new HistoryFragment(), R.id.storage_content);
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
    public void setStonedMode(boolean enable) {
        stonedModeEnabled = enable;
        mStonedBear.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);

        invalidateOptionsMenu(); // redraw options menu in top-right corner
    }

    @Override
    public void showAllLanguages(List<UILanguage> languages) {
        // nothing
        // todo optimize in order not having these empty methods!
    }

    @Override
    public void showLanguagePair(UILangPair langPair) {
        // nothing
        // todo optimize in order not having these empty methods!
    }
}