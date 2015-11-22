package com.league.activity.treasure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.adapter.DetailMyRecords;
import com.league.adapter.OneYuanGrabTakRecodAdapter;
import com.league.adapter.ViewPaperAdapter;
import com.league.bean.GrabRecordBean;
import com.league.bean.MyRecordGrabBean;
import com.league.bean.TenGrabDetailBean;
import com.league.bean.TenYuanGrabBean;
import com.league.dialog.TakeInDialog;
import com.league.utils.Constants;
import com.league.utils.api.ApiUtil;
import com.league.widget.ListViewForScrollView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class TenYuanGrabItem extends Activity implements View.OnClickListener{

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private ListViewForScrollView recordListView;
    private TenYuanGrabBean detail;
    private List<GrabRecordBean> records;
    private List<MyRecordGrabBean> myrecords=new ArrayList<MyRecordGrabBean>();
    private Button takeinNow,buyAll;
    private String id;
    private ImageView state;
    private TextView period,name,totalneed,remain,starttime;
    private ProgressBar progressbar;
    private float needed,remained;
    private LinearLayout linearLayout,llPoints;
    private String[] imageDescriptions;
    private TextView tvDescription;
    private int previousSelectPosition = 0;
    private ViewPager viewPager;
    private List<ImageView> listImageViews=new ArrayList<ImageView>();
    private ViewPaperAdapter mViewPaperAdapter;
    private String[] pictures;
    private TextView tv;//夺宝记录
    private ListViewForScrollView myrecordlist;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:
                    viewPager.setCurrentItem((viewPager.getCurrentItem() + 1)%listImageViews.size());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten_yuan_grab_item);
        id=getIntent().getStringExtra("id");

        initView();
        initData();

    }
    private void initView() {
        myrecordlist= (ListViewForScrollView) findViewById(R.id.myrecordlist);
        tv= (TextView) findViewById(R.id.viewtakestate);
        back = (ImageView) findViewById(R.id.near_back);
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        titleright = (ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
        title.setText("奖品详情");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        recordListView= (ListViewForScrollView) findViewById(R.id.record_list);
        takeinNow=(Button)findViewById(R.id.takeinnow);
        takeinNow.setOnClickListener(this);
        buyAll= (Button) findViewById(R.id.buyall);
        buyAll.setOnClickListener(this);

        tvDescription = (TextView) findViewById(R.id.tv_image_description);
        llPoints = (LinearLayout) findViewById(R.id.ll_points);

        state= (ImageView) findViewById(R.id.state);
        period= (TextView) findViewById(R.id.period);
        name= (TextView) findViewById(R.id.name);
        totalneed= (TextView) findViewById(R.id.totalpeo);
        remain= (TextView) findViewById(R.id.leavepeo);
        starttime= (TextView) findViewById(R.id.starttime);
        progressbar= (ProgressBar) findViewById(R.id.progressbar);
        linearLayout= (LinearLayout) findViewById(R.id.viewpaper);
        viewPager=new ViewPager(this);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        viewPager.setLayoutParams(new LinearLayout.LayoutParams(dm.widthPixels, dm.heightPixels * 2 / 5));
        linearLayout.addView(viewPager);


    }

    public void initData(){
        ApiUtil.grabcornsGetDetail(getApplication(), id, Constants.PHONENUM, new BaseJsonHttpResponseHandler<TenGrabDetailBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, TenGrabDetailBean response) {
                detail = response.getTenYuanGrabBean();
                records=response.getGrabRecordBean();
                myrecords.addAll(response.getMyRecordGrabBean());
                Paper.book().write(Constants.TenYuanDetail + id, detail);
                Paper.book().write(Constants.TenyuanGrabRecords + id, records);
                updateView(detail,records,myrecords);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, TenGrabDetailBean errorResponse) {
                detail = Paper.book().read(Constants.TenYuanDetail + id);
                records=Paper.book().read(Constants.TenyuanGrabRecords+id);
                if (detail != null) {
                    updateView(detail,records,myrecords);
                }
            }

            @Override
            protected TenGrabDetailBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject=new JSONObject(rawJsonData);
                TenYuanGrabBean tyb=new ObjectMapper().readValue(jsonObject.optString("detail"), new TypeReference<TenYuanGrabBean>() {});
                List<GrabRecordBean> grb=new ObjectMapper().readValue(jsonObject.optString("records"), new TypeReference<ArrayList<GrabRecordBean>>() {
                });
                List<MyRecordGrabBean> mrgb=new ObjectMapper().readValue(jsonObject.optString("myrecords"), new TypeReference<ArrayList<MyRecordGrabBean>>() {
                });
                TenGrabDetailBean gdb=new TenGrabDetailBean();
                gdb.setTenYuanGrabBean(tyb);
                gdb.setGrabRecordBean(grb);
                gdb.setMyRecordGrabBean(mrgb);
                return gdb;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    SystemClock.sleep(2000);
                    handler.sendEmptyMessage(0);

                }

            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.takeinnow:
                //弹出对话框
                TakeInDialog takeInDialog=new TakeInDialog(TenYuanGrabItem.this,id,0);
                takeInDialog.show();

                break;
            case R.id.passannounced:
                Intent intent=new Intent(TenYuanGrabItem.this,PassAnnounced.class);
                intent.putExtra("id",detail.getKind());
                startActivity(intent);
                break;
            case R.id.buyall:
                Intent inten=new Intent(TenYuanGrabItem.this,BuyList.class);
                inten.putExtra("type",0);
                inten.putExtra("number",detail.getRemain());
                inten.putExtra("id",id);
                inten.putExtra("buytype",1);
                startActivity(inten);
                break;
        }
    }

    void updateView(TenYuanGrabBean detail,List<GrabRecordBean> records,List<MyRecordGrabBean> myrecords){

        if(detail.getIslotteried().equals("0")){
            state.setImageResource(R.drawable.running);
        }else{
            state.setImageResource(R.drawable.grabstate_finished);
        }
        period.setText("第"+detail.getVersion()+"期");
        name.setText(detail.getTitle());
        totalneed.setText(detail.getNeeded());
        remain.setText("剩余"+detail.getRemain());
        needed=Float.valueOf(detail.getNeeded());
        remained=Float.valueOf(detail.getRemain());
        progressbar.setProgress((int) ((needed - remained) / needed * 100));
        starttime.setText(detail.getCreated_at());
        pictures=detail.getPictures().split(" ");
        imageDescriptions = getImageDescription();
        ImageView iv=null;
        View view;
        for(int i=0;i<pictures.length;i++) {
            iv = new ImageView(this);
            Picasso.with(getApplication()).load(pictures[i]).into(iv);
            listImageViews.add(iv);

            // 添加点view对象
            view = new View(this);
            view.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.ic_launcher));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(5, 5);
            lp.leftMargin = 10;
            view.setLayoutParams(lp);
            view.setEnabled(false);
            llPoints.addView(view);
        }

        mViewPaperAdapter=new ViewPaperAdapter(listImageViews);
        viewPager.setAdapter(mViewPaperAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 改变图片的描述信息
                tvDescription
                        .setText(imageDescriptions[position % listImageViews.size()]);
                // 切换选中的点,把前一个点置为normal状态
                llPoints.getChildAt(previousSelectPosition).setEnabled(false);
                // 把当前选中的position对应的点置为enabled状态
                llPoints.getChildAt(position % listImageViews.size()).setEnabled(true);
                previousSelectPosition = position % listImageViews.size();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        recordListView.setAdapter(new OneYuanGrabTakRecodAdapter(records,getApplication()));
        if(myrecords.size()==0){
            tv.setVisibility(View.VISIBLE);
            myrecordlist.setVisibility(View.GONE);
        }else{
            tv.setVisibility(View.GONE);
            myrecordlist.setVisibility(View.VISIBLE);
            myrecordlist.setAdapter(new DetailMyRecords(myrecords,getApplication()));
        }

        tvDescription.setText(imageDescriptions[previousSelectPosition]);
        llPoints.getChildAt(previousSelectPosition).setEnabled(true);
    }

    private String[] getImageDescription() {
        int num=pictures.length;
        String[] temp=new String[num];
        for(int i=0;i<num;i++){
            temp[i]=i+1+"";
        }
        return temp;
    }
}
