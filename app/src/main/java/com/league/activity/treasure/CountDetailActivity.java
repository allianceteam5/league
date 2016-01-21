package com.league.activity.treasure;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.CountDetailAdapter;
import com.league.bean.CountDetailBean;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CountDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    @Bind(R.id.lv_countlist)
    ListView listView;
    CountDetailAdapter adapter;
    private List<CountDetailBean> list = new ArrayList<>();

    String type = "";
    String grabid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        type = getIntent().getStringExtra("type");
        grabid = getIntent().getStringExtra("id");
        if (type == null || grabid == null || type.equals("") || grabid.equals("")) {
            onBackPressed();
            finish();
        }
        if(type.equals("0")){
            ApiUtil.getTenGrabCountDetail(this, grabid, new BaseJsonHttpResponseHandler<ArrayList<CountDetailBean>>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<CountDetailBean> response) {
                    list.clear();
                    list.addAll(response);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<CountDetailBean> errorResponse) {
                    ToastUtils.showShortToast(CountDetailActivity.this,"网络错误");
                }

                @Override
                protected ArrayList<CountDetailBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<CountDetailBean>>() {
                    });
                }
            });
        }
        if(type.equals("1")){
            ApiUtil.getOneYuanGrabCountDetail(this, grabid, new BaseJsonHttpResponseHandler<ArrayList<CountDetailBean>>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<CountDetailBean> response) {
                    list.clear();
                    list.addAll(response);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<CountDetailBean> errorResponse) {
                    ToastUtils.showShortToast(CountDetailActivity.this, "网络错误");
                }

                @Override
                protected ArrayList<CountDetailBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<CountDetailBean>>() {
                    });
                }
            });
        }
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
        title.setText("计算详情");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);

        adapter = new CountDetailAdapter(this,list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
