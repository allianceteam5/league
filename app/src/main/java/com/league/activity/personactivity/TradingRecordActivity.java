package com.league.activity.personactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.league.adapter.TradingRecordAdapter;
import com.league.bean.TradingDetailBean;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TradingRecordActivity extends Activity {

    private ImageView back2, titleright, right1, right2;
    private TextView title;
    @Bind(R.id.lv_trading)
    ListView listView;
    TradingRecordAdapter adapter;
    List<TradingDetailBean> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_record);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        listData.clear();
        for (int i = 0; i < 10; i++) {
            TradingDetailBean tradingDetailBean = new TradingDetailBean();
            tradingDetailBean.setPayDate((new Date()).toString());
            tradingDetailBean.setPayTime("20:01");
            tradingDetailBean.setPayMoney("100");
            tradingDetailBean.setPayContent("c测试啊");
            listData.add(tradingDetailBean);
        }
        adapter.notifyDataSetChanged();
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
        title.setText("交易记录");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        adapter = new TradingRecordAdapter(this, listData);
        listView.setAdapter(adapter);
    }
}
