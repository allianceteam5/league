package com.league.widget;

///
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.support.v4.view.ViewPager;

public interface PageIndicator extends ViewPager.OnPageChangeListener {
    void setViewPager(ViewPager var1);

    void setViewPager(ViewPager var1, int var2);

    void setCurrentItem(int var1);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener var1);

    void notifyDataSetChanged();
}
