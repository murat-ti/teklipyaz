package com.android.teklipyaz.views.adapters;

/**
 * Created by Lenovo on 13.10.2017.
 */

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.teklipyaz.views.fragments.EventFragment;
import com.android.teklipyaz.views.fragments.MapFragment;
import com.android.teklipyaz.views.fragments.MoreFragment;
import com.android.teklipyaz.views.fragments.OrganizationFragment;
import com.android.teklipyaz.views.fragments.OrganizationListFragment;
import com.android.teklipyaz.views.fragments.ProfileFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabsAdapter(FragmentManager fm, TabLayout tabLayout) {
        super(fm);
        this.mNumOfTabs = tabLayout.getTabCount();
        for(int i=1; i<mNumOfTabs; i++){
            tabLayout.getTabAt(i).getIcon().setAlpha(128);
        }
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                OrganizationListFragment tab1 = new OrganizationListFragment();
                return tab1;
            case 1:
                EventFragment tab2 = new EventFragment();
                return tab2;
            case 2:
                MapFragment tab3 = new MapFragment();
                return tab3;
            case 3:
                ProfileFragment tab4 = new ProfileFragment();
                return tab4;
            case 4:
                MoreFragment tab5 = new MoreFragment();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
