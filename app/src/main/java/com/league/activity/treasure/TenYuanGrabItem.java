package com.league.activity.treasure;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.DetailMyRecords;
import com.league.adapter.OneYuanGrabTakRecodAdapter;
import com.league.adapter.ViewPaperAdapter;
import com.league.bean.GrabRecordBean;
import com.league.bean.MyRecordGrabBean;
import com.league.bean.PassAnnouncedBean;
import com.league.bean.TenGrabDetailBean;
import com.league.bean.TenYuanGrabBean;
import com.league.dialog.TakeInDialog;
import com.league.utils.Constants;
import com.league.utils.ToastUtils;
import com.league.utils.Utils;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.league.widget.ListViewForScrollView;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class TenYuanGrabItem extends BaseActivity implements View.OnClickListener {

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private ListViewForScrollView recordListView;
    private TenYuanGrabBean detail;
    private List<GrabRecordBean> records;
    private List<MyRecordGrabBean> myrecords;
    private Button takeinNow, buyAll;
    private String id;
    private ImageView state;
    private TextView period, name, totalneed, remain, starttime, date;
    private ProgressBar progressbar;
    private float needed, remained;
    private LinearLayout linearLayout, llPoints, winnerresult, bottonnormal;
    private RelativeLayout bottongo;
    private RelativeLayout viewTimeCount;
    private TextView timeMinutes, timeMill;//倒计时textview
    private Button countdetail;//计算详情
    private TimeCount count;//计时器
    private TextView lucknum;//幸运号码
    private Button countdetail1;//计算详情，幸运号码旁边那个
    private CircleImageView winnerThumb;//获奖者头像
    private TextView winnerName;//获奖者名字
    private TextView winnerId;//获奖者ID；
    private TextView winnerCount;//获奖者参与人次
    private TextView winnerEndTime;//揭晓时间
    private int previousSelectPosition = 0;
    private ViewPager viewPager;
    private List<ImageView> listImageViews = new ArrayList<ImageView>();
    private ViewPaperAdapter mViewPaperAdapter;
    private String[] pictures;
    private TextView tv;//夺宝记录
    private ListViewForScrollView myrecordlist;
    private Button goRightNow;
    private PullToRefreshLayout pullToRefreshLayout;
    private int totalPage = 2;
    private int currentPage = 1;
    private OneYuanGrabTakRecodAdapter recordAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    if (listImageViews.size() != 0) {
                        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % listImageViews.size());
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten_yuan_grab_item);
        id = getIntent().getStringExtra("id");
        showProgressDialog();
        initView();
        initData();
        title.setFocusable(true);
        title.setFocusableInTouchMode(true);
        title.requestFocus();
        title.requestFocusFromTouch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(4000);
                    handler.sendEmptyMessage(0);

                }

            }
        }).start();
    }

    private void initView() {
        myrecordlist = (ListViewForScrollView) findViewById(R.id.myrecordlist);
        tv = (TextView) findViewById(R.id.viewtakestate);
        back = (ImageView) findViewById(R.id.near_back);
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        titleright = (ImageView) findViewById(R.id.near_title_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("奖品详情");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        recordListView = (ListViewForScrollView) findViewById(R.id.record_list);
        takeinNow = (Button) findViewById(R.id.takeinnow);
        takeinNow.setOnClickListener(this);
        buyAll = (Button) findViewById(R.id.buyall);
        buyAll.setOnClickListener(this);
        llPoints = (LinearLayout) findViewById(R.id.ll_points);
        viewTimeCount = (RelativeLayout) findViewById(R.id.viewtimecount);
        timeMinutes = (TextView) findViewById(R.id.countminu);
        timeMill = (TextView) findViewById(R.id.countmill);
        countdetail = (Button) findViewById(R.id.countdetail);
        winnerresult = (LinearLayout) findViewById(R.id.viewwinneresult);
        lucknum = (TextView) findViewById(R.id.luckynumber);
        countdetail1 = (Button) findViewById(R.id.countdetail1);
        winnerThumb = (CircleImageView) findViewById(R.id.thumb);
        winnerName = (TextView) findViewById(R.id.holdername);
        winnerId = (TextView) findViewById(R.id.holderid);
        winnerCount = (TextView) findViewById(R.id.taknum);
        winnerEndTime = (TextView) findViewById(R.id.endtime);

        state = (ImageView) findViewById(R.id.state);
        period = (TextView) findViewById(R.id.period);
        name = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date);
        totalneed = (TextView) findViewById(R.id.totalpeo);
        remain = (TextView) findViewById(R.id.leavepeo);
        starttime = (TextView) findViewById(R.id.starttime);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        linearLayout = (LinearLayout) findViewById(R.id.viewpaper);
        viewPager = new ViewPager(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        viewPager.setLayoutParams(new LinearLayout.LayoutParams(dm.widthPixels, dm.heightPixels * 2 / 5));
        linearLayout.addView(viewPager);

        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());

        bottonnormal = (LinearLayout) findViewById(R.id.bottomnormal);
        bottongo = (RelativeLayout) findViewById(R.id.bottom_go);
        goRightNow = (Button) findViewById(R.id.gorightnow);
        goRightNow.setOnClickListener(this);
    }

    public void initData() {
        ApiUtil.grabcornsGetDetail(getApplication(), id, new BaseJsonHttpResponseHandler<TenGrabDetailBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, TenGrabDetailBean response) {
                detail = response.getTenYuanGrabBean();
                records = response.getGrabRecordBean();
                myrecords = response.getMyRecordGrabBean();
                Paper.book().write(Constants.TenYuanDetail + id, detail);
                Paper.book().write(Constants.TenyuanGrabRecords + id, records);
                updateView(detail, records, myrecords);
                closeProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, TenGrabDetailBean errorResponse) {
                detail = Paper.book().read(Constants.TenYuanDetail + id);
                records = Paper.book().read(Constants.TenyuanGrabRecords + id);
                if (detail != null) {
                    updateView(detail, records, myrecords);

                }
                closeProgressDialog();
            }

            @Override
            protected TenGrabDetailBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                TenYuanGrabBean tyb = new ObjectMapper().readValue(jsonObject.optString("detail"), new TypeReference<TenYuanGrabBean>() {
                });
                List<GrabRecordBean> grb = new ObjectMapper().readValue(jsonObject.optString("records"), new TypeReference<ArrayList<GrabRecordBean>>() {
                });
                List<MyRecordGrabBean> mrgb = new ObjectMapper().readValue(jsonObject.optString("myrecords"), new TypeReference<ArrayList<MyRecordGrabBean>>() {
                });
                TenGrabDetailBean gdb = new TenGrabDetailBean();
                gdb.setTenYuanGrabBean(tyb);
                gdb.setGrabRecordBean(grb);
                gdb.setMyRecordGrabBean(mrgb);
                return gdb;
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takeinnow:
                //弹出对话框
                TakeInDialog takeInDialog = new TakeInDialog(TenYuanGrabItem.this, id, 0);
                takeInDialog.show();

                break;
            case R.id.passannounced:
                Intent intent = new Intent(TenYuanGrabItem.this, PassAnnounced.class);
                intent.putExtra("type", 0);
                intent.putExtra("kind", detail.getKind());
                startActivity(intent);
                break;
            case R.id.buyall:
                Intent inten = new Intent(TenYuanGrabItem.this, BuyList.class);
                inten.putExtra("type", 0);//type==0表示10夺金购买
                inten.putExtra("number", detail.getNeeded());
                inten.putExtra("id", id);
                inten.putExtra("buytype", 1);//购买全部
                startActivity(inten);
                break;
            case R.id.gorightnow:
                ApiUtil.grabcornsPassAnnounced(getApplication(), detail.getKind(), 1, new BaseJsonHttpResponseHandler<ArrayList<PassAnnouncedBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<PassAnnouncedBean> response) {
                        if (response.size() != 0 && response.get(0).getEnd_at().equals("0")) {
                            Intent go = new Intent(TenYuanGrabItem.this, TenYuanGrabItem.class);
                            go.putExtra("id", response.get(0).getId());
                            startActivity(go);
                        } else {
                            ToastUtils.showShortToast(getApplicationContext(), "没有下一期了");
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<PassAnnouncedBean> errorResponse) {
                        ToastUtils.showShortToast(getApplicationContext(), "网络不给力");
                    }

                    @Override
                    protected ArrayList<PassAnnouncedBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<PassAnnouncedBean>>() {
                        });
                    }
                });

                break;
        }
    }

    void updateView(TenYuanGrabBean detail, List<GrabRecordBean> records, List<MyRecordGrabBean> myrecords) {

        if (detail.getIslotteried().equals("0")) {
            state.setImageResource(R.drawable.running);
            winnerresult.setVisibility(View.GONE);
            if (detail.getEnd_at().equals("0")) {
                viewTimeCount.setVisibility(View.GONE);
                bottonnormal.setVisibility(View.VISIBLE);
                bottongo.setVisibility(View.GONE);
            } else {
                bottongo.setVisibility(View.VISIBLE);
                bottonnormal.setVisibility(View.GONE);
                viewTimeCount.setVisibility(View.VISIBLE);
                count = new TimeCount(Long.valueOf(detail.getEnd_at()) * 1000 - System.currentTimeMillis(), 1000);
                count.start();
            }

        } else {
            bottongo.setVisibility(View.VISIBLE);
            bottonnormal.setVisibility(View.GONE);
            state.setImageResource(R.drawable.grabstate_finished);
            viewTimeCount.setVisibility(View.GONE);
            winnerresult.setVisibility(View.VISIBLE);
            lucknum.setText(detail.getWinnernumber());
            winnerId.setText(detail.getWinneruserid());
            winnerEndTime.setText(Utils.TimeStamp2SystemNotificationDate(Long.valueOf(detail.getEnd_at()) * 1000));
            winnerName.setText(detail.getNickname());
            winnerCount.setText(detail.getCount());
            if (!TextUtils.isEmpty(detail.getThumb()))
                Picasso.with(getApplicationContext()).load(detail.getThumb()).into(winnerThumb);

        }

        period.setText("保本夺金第" + detail.getVersion() + "期");
        name.setText(detail.getTitle());
        date.setText(Utils.TimeStamp2Date(Integer.valueOf(detail.getDate())));
        totalneed.setText(detail.getNeeded());
        remain.setText("剩余" + detail.getRemain());
        needed = Float.valueOf(detail.getNeeded());
        remained = Float.valueOf(detail.getRemain());
        progressbar.setProgress((int) ((needed - remained) / needed * 100));
        starttime.setText(Utils.TimeStamp2SystemNotificationDate(Long.valueOf(detail.getCreated_at()) * 1000));
        pictures = detail.getPictures().split(" ");
        ImageView iv = null;
        View view;
        listImageViews.clear();
        llPoints.removeAllViews();
        for (int i = 0; i < pictures.length; i++) {
            iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            if (!TextUtils.isEmpty(pictures[i]))
                Picasso.with(getApplication()).load(pictures[i]).into(iv);
            listImageViews.add(iv);

            // 添加点view对象
            view = new View(this);
            view.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.pointswhite));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(25, 25);
            lp.leftMargin = 10;
            view.setLayoutParams(lp);
            view.setEnabled(false);
            llPoints.addView(view);
        }

        mViewPaperAdapter = new ViewPaperAdapter(listImageViews);
        viewPager.setAdapter(mViewPaperAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // 切换选中的点,把前一个点置为normal状态
                llPoints.getChildAt(previousSelectPosition).setEnabled(false);
                llPoints.getChildAt(previousSelectPosition).setBackground(getResources().getDrawable(R.drawable.pointswhite));
                // 把当前选中的position对应的点置为enabled状态
                llPoints.getChildAt(position % listImageViews.size()).setEnabled(true);
                llPoints.getChildAt(position % listImageViews.size()).setBackground(getResources().getDrawable(R.drawable.pointsred));
                previousSelectPosition = position % listImageViews.size();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        recordAdapter = new OneYuanGrabTakRecodAdapter(records, getApplication());
        recordListView.setAdapter(recordAdapter);
        if (myrecords.size() == 0) {
            tv.setVisibility(View.VISIBLE);
            myrecordlist.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.GONE);
            myrecordlist.setVisibility(View.VISIBLE);
            myrecordlist.setAdapter(new DetailMyRecords(myrecords, getApplication()));
        }
        llPoints.getChildAt(previousSelectPosition).setEnabled(true);
        llPoints.getChildAt(previousSelectPosition).setBackground(getResources().getDrawable(R.drawable.pointsred));
    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minutes = millisUntilFinished / 60000;
            long mill = (millisUntilFinished % 60000) / 1000;
            if (timeMinutes.getText().toString().equals(minutes + "")) {
                timeMill.setText(mill + "");
            } else {
                timeMill.setText(mill + "");
                timeMinutes.setText(minutes + "");
            }

        }

        @Override
        public void onFinish() {
            initData();
        }
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    initData();
                    totalPage = 2;
                    currentPage = 1;
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);

        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    currentPage++;
                    if (currentPage <= totalPage) {
                        ApiUtil.getMoreRecordCorn(getApplicationContext(), id, currentPage, new BaseJsonHttpResponseHandler<ArrayList<GrabRecordBean>>() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<GrabRecordBean> response) {
                                if (totalPage == 1) {
                                    records.clear();
                                }
                                records.addAll(response);
                                recordAdapter.notifyDataSetChanged();
                                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<GrabRecordBean> errorResponse) {
                                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                            }

                            @Override
                            protected ArrayList<GrabRecordBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                JSONObject jsonObject = new JSONObject(rawJsonData);
                                totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<GrabRecordBean>>() {
                                });
                            }
                        });
                    } else {
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }

                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

    }
}
