package com.league.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.easemob.chatuidemo.activity.LoginActivity;
import com.league.utils.ActivityUtils;
import com.league.utils.StoreUtils;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends FragmentActivity {

    private ViewPager viewPage;
    private FragmentGuide1 mFragment1;
    private FragmentGuide2 mFragment2;
    private FragmentGuide3 mFragment3;
    private PagerAdapter mPgAdapter;
    private RadioGroup dotLayout;
    private List<Fragment> mListFragment = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        if (StoreUtils.getSkipGuidState()) {
            ActivityUtils.start_Activity(this, LoginActivity.class);
            this.finish();
        }

        initView();
        viewPage.setOnPageChangeListener(new MyPagerChangeListener());

    }

    private void initView() {
        dotLayout = (RadioGroup) findViewById(R.id.advertise_point_group);
        viewPage = (ViewPager) findViewById(R.id.viewpager);
        mFragment1 = new FragmentGuide1();
        mFragment2 = new FragmentGuide2();
        mFragment3 = new FragmentGuide3();
        mListFragment.add(mFragment1);
        mListFragment.add(mFragment2);
        mListFragment.add(mFragment3);
        mPgAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                mListFragment);
        viewPage.setAdapter(mPgAdapter);

    }

    public class MyPagerChangeListener implements OnPageChangeListener {

        public void onPageSelected(int position) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            ((RadioButton) dotLayout.getChildAt(position)).setChecked(true);
        }

    }
}
