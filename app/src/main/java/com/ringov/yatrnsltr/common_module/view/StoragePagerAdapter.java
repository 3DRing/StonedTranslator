package com.ringov.yatrnsltr.common_module.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ringov.yatrnsltr.storage_module.view.FavoriteFragment;
import com.ringov.yatrnsltr.storage_module.view.HistoryFragment;
import com.ringov.yatrnsltr.translation_module.view.TranslateFragment;

/**
 * Created by Sergey Koltsov on 22.04.2017.
 */
public class StoragePagerAdapter extends FragmentStatePagerAdapter {

    public StoragePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return i == 0 ? new HistoryFragment() : new FavoriteFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "History" : "Favorite";
    }
}
