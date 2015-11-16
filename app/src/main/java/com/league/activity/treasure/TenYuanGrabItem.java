package com.league.activity.treasure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.TenYuanGrabBean;
import com.league.utils.Constants;
import com.league.utils.api.ApiUtil;
import com.league.widget.ListViewForScrollView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import io.paperdb.Paper;

public class TenYuanGrabItem extends Activity implements View.OnClickListener{

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private ListViewForScrollView listView;
    private TenYuanGrabBean detail;
    private Button takeinNow;
    private String id,phone;
    private ImageView state,picture;
    private TextView period,name,totalneed,remain;
    private ProgressBar progressbar;
    private float needed,remained;

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

        picture= (ImageView) findViewById(R.id.imgview);
        state= (ImageView) findViewById(R.id.state);
        period= (TextView) findViewById(R.id.period);
        name= (TextView) findViewById(R.id.name);
        totalneed= (TextView) findViewById(R.id.totalpeo);
        remain= (TextView) findViewById(R.id.leavepeo);
        progressbar= (ProgressBar) findViewById(R.id.progressbar);
    }

    public void initData(){
        ApiUtil.grabcornsGetDetail(getApplication(), id, Constants.PHONENUM, new BaseJsonHttpResponseHandler<TenYuanGrabBean>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, TenYuanGrabBean response) {
                detail = response;
                Paper.book().write(Constants.TenYuanDetail + id, detail);
                Message message = new Message();
                message.what = 1;
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

//        for(int i=0;i<5;i++){
//            OneYuanTakingMember oytm=new OneYuanTakingMember();
//            oytm.setName("小杜 "+i);
//            oytm.setTakeTime("15:34:13");
//            oytm.setTakeNum(i);
//            list.add(oytm);
//        }
//        listView.setAdapter(new OneYuanGrabTakRecodAdapter(list,getApplication()));
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
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    updateView(detail);
                    break;
            }
        }
    };
    void updateView(TenYuanGrabBean detail){
        Picasso.with(getApplication()).load(detail.getPicture()).into(picture);
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
        progressbar.setProgress((int)((needed-remained)/needed*100));
    }
}
