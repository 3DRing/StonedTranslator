package com.ringov.yatrnsltr;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ringov.yatrnsltr.translation_module.view.TranslateFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @IdRes
    int defaultMenu = R.id.navigation_translation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigation.setSelectedItemId(defaultMenu);
        switchFragment(defaultMenu);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switchFragment(item.getItemId());
            return true;
        });

    }

    private void switchFragment(@IdRes int menuId) {
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

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content, f, f.getClass().getSimpleName()).commit();
    }
}
