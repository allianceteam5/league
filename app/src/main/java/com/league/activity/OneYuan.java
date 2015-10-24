package com.league.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.league.adapter.HorizonListAdapter;
import com.league.bean.TenYuanGrabBean;
import com.league.view.HorizontalListView;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class OneYuan extends Activity implements View.OnClickListener{

    private ImageView back1,back2,titleright,right1,right2;
    private TextView title;
    private HorizontalListView horizonList;
    private List<TenYuanGrabBean> listTenYuanGrab=new ArrayList<TenYuanGrabBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_yuan);
        initView();
        initData();
    }
    private void initView(){
        back1=(ImageView) findViewById(R.id.near_back);
        back2=(ImageView) findViewById(R.id.near_back_item);
        back1.setVisibility(View.GONE);
        back2.setVisibility(View.VISIBLE);
        back2.setOnClickListener(this);
        titleright=(ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title=(TextView) findViewById(R.id.near_centertitle);
        title.setText("一元夺宝");
        right1=(ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2=(ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        horizonList=(HorizontalListView)findViewById(R.id.horizon_listview);

    }
    void initData(){
        for(int i=0;i<5;i++){
            TenYuanGrabBean example=new TenYuanGrabBean();
            example.setmMoney(50+i+"");
            example.setmTotalPeo(500);
            example.setmTakingPeo(200);
            listTenYuanGrab.add(example);
        }
        horizonList.setAdapter(new HorizonListAdapter(getApplication(),listTenYuanGrab,0));

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.near_back_item:
                onBackPressed();
                finish();
                break;
            case R.id.One_tengrab:
                Toast.makeText(this,"10",Toast.LENGTH_LONG).show();
                break;
            case R.id.One_Yuan:
                Toast.makeText(this,"1",Toast.LENGTH_LONG).show();
                break;
            case R.id.One_record:
                Toast.makeText(this,"rec",Toast.LENGTH_LONG).show();
                break;
            case R.id.One_question:
                Toast.makeText(this,"que",Toast.LENGTH_LONG).show();
                break;
            case R.id.tengrb_more:
                Toast.makeText(this,"10more",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
