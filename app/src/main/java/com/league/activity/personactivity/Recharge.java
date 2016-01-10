package com.league.activity.personactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.adapter.PayMoneyAmountAdapter;
import com.league.widget.MyRadioGroup;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class Recharge extends Activity implements MyRadioGroup.OnCheckedChangeListener,AdapterView.OnItemClickListener {

    private ImageView back2, titleright, right1, right2;
    private TextView title,tvNum;
    private Button sure;
    private MyRadioGroup radioGroup;
    private List<Integer> listData = new ArrayList<Integer>();
    private GridView gridView;
    private PayMoneyAmountAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
        initData();
    }

    private void initData() {
        int[] data = {20, 30, 50, 100, 200, 300};
        for (int m : data) {
            listData.add(m);
        }
        adapter = new PayMoneyAmountAdapter(listData,this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private void initView() {
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
        title.setText("充值");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        sure = (Button) findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PaymentOrder.class);
                startActivity(intent);

            }
        });
        radioGroup = (MyRadioGroup) findViewById(R.id.paytypegroup);
        radioGroup.setOnCheckedChangeListener(this);
        gridView = (GridView) findViewById(R.id.gv_recharge);
        tvNum = (TextView) findViewById(R.id.tv_num);
    }

    @Override
    public void onCheckedChanged(MyRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_wxpay:
                break;
            case R.id.rb_alipay:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tvNum.setText(listData.get(position)+"");
    }
}
