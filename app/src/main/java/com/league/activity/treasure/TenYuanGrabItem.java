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
import com.league.adapter.ViewPaperAdapter;
import com.league.bean.TenYuanGrabBean;
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
    private ListViewForScrollView listView;
    private TenYuanGrabBean detail;
    private Button takeinNow;
    private String id;
    private ImageView state;
    private TextView period,name,totalneed,remain;
    private ProgressBar progressbar;
    private float needed,remained;
    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private List<ImageView> listviews;
    private ViewPaperAdapter mViewPaperAdapter;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 123 :
                    updateView(detail);
                    break;
                case 0:
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
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
        title.setText("10夺金");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.VISIBLE);
        listView= (ListViewForScrollView) findViewById(R.id.record_list);
        takeinNow=(Button)findViewById(R.id.takeinnow);
        takeinNow.setOnClickListener(this);


        state= (ImageView) findViewById(R.id.state);
        period= (TextView) findViewById(R.id.period);
        name= (TextView) findViewById(R.id.name);
        totalneed= (TextView) findViewById(R.id.totalpeo);
        remain= (TextView) findViewById(R.id.leavepeo);
        progressbar= (ProgressBar) findViewById(R.id.progressbar);
        linearLayout= (LinearLayout) findViewById(R.id.viewpaper);
        viewPager=new ViewPager(this);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        viewPager.setLayoutParams(new LinearLayout.LayoutParams(dm.widthPixels, dm.heightPixels * 2 / 5));
        linearLayout.addView(viewPager);
        listviews=new ArrayList<ImageView>();
        imageView=new ImageView(this);
        imageView1=new ImageView(this);
        imageView2=new ImageView(this);
        imageView3=new ImageView(this);
        imageView4=new ImageView(this);
        imageView5=new ImageView(this);
        imageView6=new ImageView(this);

    }

    public void initData(){
        ApiUtil.grabcornsGetDetail(getApplication(), id, Constants.PHONENUM, new BaseJsonHttpResponseHandler<TenYuanGrabBean>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, TenYuanGrabBean response) {
                detail = response;
                Paper.book().write(Constants.TenYuanDetail + id, detail);
                Message message = new Message();
                message.what = 123;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, TenYuanGrabBean errorResponse) {
                detail = Paper.book().read(Constants.TenYuanDetail + id);
                if (detail != null) {
                    updateView(detail);
                }
            }

            @Override
            protected TenYuanGrabBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);

                return new ObjectMapper().readValue(jsonObject.optString("detail"), new TypeReference<TenYuanGrabBean>() {
                });
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
                Intent inten=new Intent(TenYuanGrabItem.this,BuyList.class);
                startActivity(inten);
                break;
            case R.id.passannounced:
                Intent intent=new Intent(TenYuanGrabItem.this,PassAnnounced.class);
                startActivity(intent);
                break;
        }
    }

    void updateView(TenYuanGrabBean detail){

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

        Picasso.with(getApplication()).load(detail.getPicture()).into(imageView);
        listviews.add(imageView);

        Picasso.with(getApplication()).load(detail.getPicture1()).into(imageView1);
        listviews.add(imageView1);

        Picasso.with(getApplication()).load(detail.getPicture2()).into(imageView2);
        listviews.add(imageView2);

        Picasso.with(getApplication()).load(detail.getPicture3()).into(imageView3);
        listviews.add(imageView3);

        Picasso.with(getApplication()).load(detail.getPicture4()).into(imageView4);
        listviews.add(imageView4);

        Picasso.with(getApplication()).load(detail.getPicture5()).into(imageView5);
        listviews.add(imageView5);

        Picasso.with(getApplication()).load(detail.getPicture6()).into(imageView6);
        listviews.add(imageView6);

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
    }

}
