package com.vjtechsolution.aiceluckywheel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments = new Fragment[] {
                new ProductListFragment(),
                new CustomerFotoFragment(),
                new CustomerBioFragment()
        };
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

}
