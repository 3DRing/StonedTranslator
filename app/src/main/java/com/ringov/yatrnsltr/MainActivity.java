package com.ringov.yatrnsltr;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ringov.yatrnsltr.settings.SettingsFragment;
import com.ringov.yatrnsltr.storage_module.view.StorageFragment;
import com.ringov.yatrnsltr.translation_module.view.TranslateFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // not used, keeping just in case of changing architecture back to the bottom menu

    @Deprecated
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @Deprecated
    @IdRes
    int defaultMenu = R.id.navigation_translation;

    /**
     * Should be called from onCreate after views initialization
     */
    @Deprecated
    private void initializeBottomMenu() {
        navigation.setSelectedItemId(defaultMenu);
        navigation.setOnNavigationItemSelectedListener(item -> {
            changeFragment(item.getItemId());
            return true;
        });
    }

    @Deprecated
    private void changeFragment(@IdRes int menuId) {
        Fragment f = null;
        switch (menuId) {
            case R.id.navigation_storage:
                f = new StorageFragment();
                break;
            case R.id.navigation_translation:
                f = new TranslateFragment();
                break;
            case R.id.navigation_settings:
                f = new SettingsFragment();
                break;
            default:
                f = new TranslateFragment();
                break;
        }

        if (f != null) {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.findFragmentByTag(f.getClass().getSimpleName()) != null) {
                // do not commit another instance of a fragment
                // if there already is the same one in manager
                return;
            }
            fm.beginTransaction().replace(R.id.translate_content, f, f.getClass().getSimpleName()).commit();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeFragments();
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

}
