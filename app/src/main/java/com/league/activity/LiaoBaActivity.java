package com.league.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.league.activity.personactivity.MyPersonHomepage;
import com.league.adapter.ViewPagerTabAdapter;
import com.league.fragment.LiaoBaConcernFragment;
import com.league.fragment.LiaoBaHotFragment;
import com.league.fragment.LiaoBaLatestFragment;
import com.league.fragment.LiaoBaPopularityFragment;
import com.league.widget.PagerSlidingTabStrip;
import com.mine.league.R;

public class LiaoBaActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton back,mine;
    private ViewPager pager;
    private ViewPagerTabAdapter adapter;
    PagerSlidingTabStrip tabs;
    CharSequence titles[] = {"最新", "热门", "人气", "关注"};
    ColorStateList colors[];
    Fragment[] fragments;
    LiaoBaLatestFragment latest;
    LiaoBaPopularityFragment popularity;
    LiaoBaHotFragment hot;
    LiaoBaConcernFragment concern;
    int lastTab = 0;
    int currentTab = 0;
    LinearLayout view;
    TextView tabViews[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liao_ba);
        initView();
    }

    private void initView() {
        back = (ImageButton) findViewById(R.id.liaoba_back);
        back.setOnClickListener(this);
        latest = new LiaoBaLatestFragment();
        popularity = new LiaoBaPopularityFragment();
        hot = new LiaoBaHotFragment();
        concern = new LiaoBaConcernFragment();
        fragments = new Fragment[]{latest, hot, popularity, concern};
        adapter = new ViewPagerTabAdapter(getSupportFragmentManager(), titles, fragments);
        pager = (ViewPager) findViewById(R.id.paper_liaoba);
        pager.setAdapter(adapter);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_liaoba);
        tabs.setViewPager(pager);
        view = (LinearLayout) tabs.getChildAt(0);
        tabViews = new TextView[4];
        colors = new ColorStateList[2];
        colors[0]=getResources().getColorStateList(R.color.red);
        colors[1]=getResources().getColorStateList(R.color.black);
        tabViews[0]= (TextView) view.getChildAt(0);
        tabViews[0].setTextColor(colors[0]);
        tabViews[1]= (TextView) view.getChildAt(1);
        tabViews[1].setTextColor(colors[1]);
        tabViews[2]= (TextView) view.getChildAt(2);
        tabViews[2].setTextColor(colors[1]);
        tabViews[3]= (TextView) view.getChildAt(3);
        tabViews[3].setTextColor(colors[1]);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                currentTab = position;
                switch (position) {
                    case 0:
                        tabViews[0].setTextColor(colors[0]);
                        tabViews[1].setTextColor(colors[1]);
                        tabViews[2].setTextColor(colors[1]);
                        tabViews[3].setTextColor(colors[1]);
                        break;
                    case 1:
                        tabViews[0].setTextColor(colors[1]);
                        tabViews[1].setTextColor(colors[0]);
                        tabViews[2].setTextColor(colors[1]);
                        tabViews[3].setTextColor(colors[1]);
                        break;
                    case 2:
                        tabViews[0].setTextColor(colors[1]);
                        tabViews[1].setTextColor(colors[1]);
                        tabViews[2].setTextColor(colors[0]);
                        tabViews[3].setTextColor(colors[1]);
                        break;
                    case 3:
                        tabViews[0].setTextColor(colors[1]);
                        tabViews[1].setTextColor(colors[1]);
                        tabViews[2].setTextColor(colors[1]);
                        tabViews[3].setTextColor(colors[0]);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mine= (ImageButton) findViewById(R.id.liaoba_mine);
        mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.liaoba_back:
                onBackPressed();
                finish();
                break;
            case R.id.liaoba_mine:
                Intent intent = new Intent(getApplication(), MyPersonHomepage.class);
                startActivity(intent);
                break;
        }
    }
}
