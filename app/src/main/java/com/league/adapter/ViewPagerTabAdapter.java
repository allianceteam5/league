package com.league.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by liug on 15/11/9.
 */
public class ViewPagerTabAdapter extends FragmentPagerAdapter{

    CharSequence Titles[];
    Fragment fragments[];

    public ViewPagerTabAdapter(FragmentManager fm,CharSequence cs[],Fragment fragment[]) {
        super(fm);
        Titles=cs;
        fragments=fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
}
