package com.league.activity.treasure;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.league.adapter.ViewPagerTabAdapter;
import com.league.fragment.RecordFrament;
import com.league.widget.PagerSlidingTabStrip;
import com.mine.league.R;

public class GrabRecord extends FragmentActivity {

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;

    private ViewPager pager;
    private ViewPagerTabAdapter adapter;
    PagerSlidingTabStrip tabs;
    CharSequence titles[] = {"夺金列表", "夺宝列表", "中奖列表"};
    ColorStateList colors[];
    Fragment[] fragments;
    RecordFrament grabCoin,grabCommo,winrecord;
    int currentTab = 0;
    LinearLayout view;
    TextView tabViews[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_record);
        initView();
    }
    private void initView(){
        back2 = (ImageView) findViewById(R.id.near_back);

        back2.setVisibility(View.VISIBLE);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        titleright = (ImageView) findViewById(R.id.near_title_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("夺宝记录");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.INVISIBLE);

        grabCoin=new RecordFrament(0);
        grabCommo=new RecordFrament(1);
        winrecord=new RecordFrament(2);
        fragments=new Fragment[]{grabCoin,grabCommo,winrecord};
        adapter = new ViewPagerTabAdapter(getSupportFragmentManager(), titles, fragments);
        pager = (ViewPager) findViewById(R.id.paper_grabrecords);
        pager.setAdapter(adapter);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_grabrecords);
        tabs.setViewPager(pager);
        view = (LinearLayout) tabs.getChildAt(0);
        tabViews = new TextView[3];
        colors = new ColorStateList[2];
        colors[0]=getResources().getColorStateList(R.color.red);
        colors[1]=getResources().getColorStateList(R.color.black);
        tabViews[0]= (TextView) view.getChildAt(0);
        tabViews[0].setTextColor(colors[0]);
        tabViews[1]= (TextView) view.getChildAt(1);
        tabViews[1].setTextColor(colors[1]);
        tabViews[2]= (TextView) view.getChildAt(2);
        tabViews[2].setTextColor(colors[1]);
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
                        break;
                    case 1:
                        tabViews[0].setTextColor(colors[1]);
                        tabViews[1].setTextColor(colors[0]);
                        tabViews[2].setTextColor(colors[1]);
                        break;
                    case 2:
                        tabViews[0].setTextColor(colors[1]);
                        tabViews[1].setTextColor(colors[1]);
                        tabViews[2].setTextColor(colors[0]);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
