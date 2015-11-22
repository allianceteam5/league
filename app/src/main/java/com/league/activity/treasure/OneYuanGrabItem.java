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
import com.league.bean.OneGrabDetailBean;
import com.league.bean.OneYuanBean;
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

public class OneYuanGrabItem extends Activity implements View.OnClickListener{

    private ImageView back2, titleright, right1, right2;
    private TextView title;
    private ListViewForScrollView listView;
    private Button takeinNow,takeinAll;

    private OneYuanBean detail;
    private List<GrabRecordBean> records;
    private List<MyRecordGrabBean> myrecords=new ArrayList<MyRecordGrabBean>();
    private String id;
    private ImageView state;
    private TextView period,name,totalneed,remain,starttime;
    private ProgressBar progressbar;
    private float needed,remained;
    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private List<ImageView> listviews;
    private ViewPaperAdapter mViewPaperAdapter;
    String[] pictures;
    private TextView tv;//夺宝记录
    private ListViewForScrollView myrecordlist;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:
                    if(listviews.size()!=0){
                        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1)%listviews.size());
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_yuan_grab_item);
        id=getIntent().getStringExtra("id");
        initView();
        initData();
    }
    private void initView() {
        myrecordlist= (ListViewForScrollView) findViewById(R.id.myrecordlist);
        tv= (TextView) findViewById(R.id.viewtakestate);
        back2 = (ImageView) findViewById(R.id.near_back);

        back2.setVisibility(View.VISIBLE);
        back2.setOnClickListener(new View.OnClickListener() {
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
        listView= (ListViewForScrollView) findViewById(R.id.record_list);
        takeinNow=(Button)findViewById(R.id.takeinnow);
        takeinNow.setOnClickListener(this);
        takeinAll= (Button) findViewById(R.id.takeall);
        takeinAll.setOnClickListener(this);

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
        listviews=new ArrayList<ImageView>();
    }

    public void initData(){
        ApiUtil.grabcommoditiesGetDetail(getApplication(), id, Constants.PHONENUM, new BaseJsonHttpResponseHandler<OneGrabDetailBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, OneGrabDetailBean response) {
                detail=response.getOneYuanBean();
                records=response.getGrabRecordBean();
                myrecords.addAll(response.getMyRecordGrabBean());
                Paper.book().write(Constants.OneYuanDetail + id, detail);
                Paper.book().write(Constants.OneYuanDetailRecords+id,records);
                updateView(detail, records, myrecords);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, OneGrabDetailBean errorResponse) {
                detail = Paper.book().read(Constants.OneYuanDetail + id);
                records=Paper.book().read(Constants.OneYuanDetailRecords+id);
                if (detail != null) {
                    updateView(detail,records,myrecords);
                }
            }

            @Override
            protected OneGrabDetailBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject=new JSONObject(rawJsonData);
                OneYuanBean oyb=new ObjectMapper().readValue(jsonObject.optString("detail"), new TypeReference<OneYuanBean>() {});
                List<GrabRecordBean> grb=new ObjectMapper().readValue(jsonObject.optString("records"), new TypeReference<ArrayList<GrabRecordBean>>() {
                });
                List<MyRecordGrabBean> mrgb=new ObjectMapper().readValue(jsonObject.optString("myrecords"), new TypeReference<ArrayList<MyRecordGrabBean>>() {
                });
                OneGrabDetailBean ogdb=new OneGrabDetailBean();
                ogdb.setOneYuanBean(oyb);
                ogdb.setGrabRecordBean(grb);
                ogdb.setMyRecordGrabBean(mrgb);
                return ogdb;
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
    void updateView(OneYuanBean detail,List<GrabRecordBean> records,List<MyRecordGrabBean> myrecords){

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
        ImageView iv=null;
        for(int i=0;i<pictures.length;i++) {
            iv = new ImageView(this);
            Picasso.with(getApplication()).load(pictures[i]).into(iv);
            listviews.add(iv);
        }

        mViewPaperAdapter=new ViewPaperAdapter(listviews);
        viewPager.setAdapter(mViewPaperAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        listView.setAdapter(new OneYuanGrabTakRecodAdapter(records,getApplication()));
        if(myrecords.size()==0){
            tv.setVisibility(View.VISIBLE);
            myrecordlist.setVisibility(View.GONE);
        }else{
            tv.setVisibility(View.GONE);
            myrecordlist.setVisibility(View.VISIBLE);
            myrecordlist.setAdapter(new DetailMyRecords(myrecords,getApplication()));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.takeinnow:
                //弹出对话框
                TakeInDialog takeInDialog=new TakeInDialog(OneYuanGrabItem.this,id,1);
                takeInDialog.show();
                break;
            case R.id.passanou://往期揭晓
                Intent intent=new Intent(OneYuanGrabItem.this,PassAnnounced.class);
                intent.putExtra("id",detail.getKind());
                startActivity(intent);
                break;
            case R.id.takeall:
                Intent inten=new Intent(OneYuanGrabItem.this,BuyList.class);
                inten.putExtra("type",1);
                inten.putExtra("number",detail.getRemain());
                inten.putExtra("id",id);
                inten.putExtra("buytype",1);
                startActivity(inten);
                break;
            case R.id.picturesdetail:
                Intent intentpicture=new Intent (OneYuanGrabItem.this,PictureDetails.class);
                intentpicture.putExtra("picturesdetail",detail.getDetails());
                startActivity(intentpicture);
                break;
        }
    }
}
