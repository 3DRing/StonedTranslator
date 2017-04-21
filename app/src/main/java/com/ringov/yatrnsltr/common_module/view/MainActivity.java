package com.ringov.yatrnsltr.common_module.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.BaseActivity;
import com.ringov.yatrnsltr.base.implementations.ContextAdapter;
import com.ringov.yatrnsltr.common_module.entities.UILanguage;
import com.ringov.yatrnsltr.common_module.interactor.CommonInteractorImpl;
import com.ringov.yatrnsltr.common_module.presenter.CommonPresenter;
import com.ringov.yatrnsltr.common_module.router.CommonRouterImpl;
import com.ringov.yatrnsltr.translation_module.view.TranslateFragment;
import com.ringov.yatrnsltr.translation_module.view.TranslateViewCallback;
import com.ringov.yatrnsltr.ui_entities.UILangPair;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<CommonPresenter> implements CommonView {

    private static final int HISTORY_TAB = 0;
    private static final int FAVORITE_TAB = 1;

    @BindView(R.id.appbar)
    AppBarLayout mAppbar;

    @BindView(R.id.ll_stoned_bear)
    View mStonedBear;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.root_layout)
    View activityRootLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    TranslateViewCallback translateCallback;
    private StoragePagerAdapter mViewPagerAdapter;
    private boolean stonedModeEnabled;
    private boolean translateOnFloatingButtonClick;

    @OnClick(R.id.fab)
    void onFloatingButtonClick() {
        mPresenter.onFloatingButtonClicked(translateOnFloatingButtonClick);
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
        initializeKeyboardChangedListener();
        initializeTabLayoutAndViewPager();
    }

    private void initializeTabLayoutAndViewPager() {
        mViewPagerAdapter = new StoragePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initializeTabLayout() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    mViewPagerAdapter.setCrtItemPosition(mViewPager.getCurrentItem());
                    mViewPagerAdapter.notifyDataSetChanged();
                }
            }
        });

        mTabLayout.addOnTabSelectedListener(new OnTabSelectedAdaptedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

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

    private void initializeKeyboardChangedListener() {
        // changing image of floating button depending on state of the keyboard
        activityRootLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new OnKeyboardStateChangedListener(activityRootLayout) {
                    @Override
                    public void onStateChanged(boolean nextStateOpened) {
                        translateOnFloatingButtonClick = nextStateOpened;
                        fab.setImageResource(nextStateOpened ? R.drawable.ic_forward_24dp : R.drawable.ic_focus_24dp);
                    }
                });
    }

    private void initializeFragments() {
        TranslateFragment translateFragment = new TranslateFragment();
        translateCallback = translateFragment;
        commitFragmentIfNotExist(getSupportFragmentManager(), translateFragment, R.id.translate_content);
        //commitFragmentIfNotExist(getSupportFragmentManager(), new HistoryFragment(), R.id.storage_content);
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
        mTabLayout.getTabAt(HISTORY_TAB).setText(enable ? R.string.history_title_stoned : R.string.history_title);
        mTabLayout.getTabAt(FAVORITE_TAB).setText(enable ? R.string.favorite_title_stoned : R.string.favorite_title);
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

    @Override
    public void requestInputFocus() {
        translateCallback.requestInputFocus();
        mAppbar.setExpanded(true, true);
    }

    @Override
    public void requestTranslate() {
        translateCallback.requestTranslate();
    }

    private static abstract class OnKeyboardStateChangedListener implements ViewTreeObserver.OnGlobalLayoutListener {

        private View activityRootView;
        private boolean softKeyboardOpened;

        OnKeyboardStateChangedListener(View activityRootView) {
            this.activityRootView = activityRootView;
            this.softKeyboardOpened = false;
        }

        public abstract void onStateChanged(boolean nextStateOpened);

        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            activityRootView.getWindowVisibleDisplayFrame(r);
            int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
            if (heightDiff > 100) {
                if (!softKeyboardOpened) {
                    onStateChanged(true);
                    softKeyboardOpened = true;
                }
            } else {
                if (softKeyboardOpened) {
                    onStateChanged(false);
                    softKeyboardOpened = false;
                }
            }
        }
    }

    private abstract class OnTabSelectedAdaptedListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            // nothing
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            // nothing
        }
    }
}
