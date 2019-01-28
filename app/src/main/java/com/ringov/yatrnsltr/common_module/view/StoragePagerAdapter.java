package com.ringov.yatrnsltr.common_module.view;

import com.ringov.yatrnsltr.storage_module.view.FavoriteFragment;
import com.ringov.yatrnsltr.storage_module.view.HistoryFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Sergey Koltsov on 22.04.2017.
 */
public class StoragePagerAdapter extends FragmentStatePagerAdapter {

    HistoryFragment historyFragment;
    FavoriteFragment favoriteFragment;
    private int crtItemPosition;

    public StoragePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        // in order to have ability to compare current visible fragment with notified one in getItemPosition()
        if (historyFragment == null && i == 0) {
            historyFragment = new HistoryFragment();
        }
        if (favoriteFragment == null && i == 1) {
            favoriteFragment = new FavoriteFragment();
        }
        return i == 0 ? historyFragment : favoriteFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "History" : "Favorite";
    }

    @Override
    public int getItemPosition(Object object) {
        // update other pages except current one
        if (object.equals(getItem(crtItemPosition))) {
            return POSITION_UNCHANGED;
        } else {
            return POSITION_NONE;
        }
    }

    public void setCrtItemPosition(int crtItemPosition) {
        this.crtItemPosition = crtItemPosition;
    }
}
