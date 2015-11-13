package com.league.activity.treasure;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.adapter.TenYuanGrabAdapter;
import com.league.bean.TenYuanGrabBean;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class OneYuanGrab extends Activity implements View.OnClickListener {

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private GridView gridView;
    private List<TenYuanGrabBean> list = new ArrayList<TenYuanGrabBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_yuan_grab);
        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        titleright = (ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
        title.setText("一元夺宝");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        gridView = (GridView) findViewById(R.id.gridview_one);
    }

    private void initData() {
        for (int i = 0; i < 6; i++) {
            TenYuanGrabBean tygb = new TenYuanGrabBean();
            tygb.setmPeriods(44);
            tygb.setmMoney("商品"+i);
            tygb.setmTotalPeo(60);
            tygb.setmTakingPeo(22);
            tygb.setmLessPeo(38);
            list.add(tygb);
        }
        gridView.setAdapter(new TenYuanGrabAdapter(getApplication(), list));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(OneYuanGrab.this,OneYuanGrabItem.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;

        }
    }
}
