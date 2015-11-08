package com.league.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.adapter.OneYuanGrabTakRecodAdapter;
import com.league.bean.OneYuanTakingMember;
import com.league.widget.ListViewForScrollView;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class TenYuanGrabItem extends Activity implements View.OnClickListener{

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private ListViewForScrollView listView;
    private List<OneYuanTakingMember> list=new ArrayList<OneYuanTakingMember>();
    private Button takeinNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten_yuan_grab_item);
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
    }

    public void initData(){
        for(int i=0;i<5;i++){
            OneYuanTakingMember oytm=new OneYuanTakingMember();
            oytm.setName("小杜 "+i);
            oytm.setTakeTime("15:34:13");
            oytm.setTakeNum(i);
            list.add(oytm);
        }
        listView.setAdapter(new OneYuanGrabTakRecodAdapter(list,getApplication()));
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
}
