package com.league.activity.treasure;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.adapter.OneyuanGrabAdapter;
import com.league.bean.OneYuanBean;
import com.league.bean.TenYuanGrabBean;
import com.league.utils.Constants;
import com.league.utils.api.ApiUtil;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class OneYuanGrab extends Activity implements View.OnClickListener {

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private GridView gridView;
    private OneyuanGrabAdapter oneyuanGrabAdapter;
    private List<OneYuanBean> list = new ArrayList<OneYuanBean>();
    private int totalPage = 1;
    private int currentPage = 1;
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_yuan_grab);
        initView();
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
        oneyuanGrabAdapter = new OneyuanGrabAdapter(getApplication(), list);
        gridView.setAdapter(oneyuanGrabAdapter);
        ((PullToRefreshLayout) findViewById(R.id.refresh_view))
                .setOnRefreshListener(new MyListener());
        initData(type,currentPage);
    }

    private void initData(int type, final int currentPage){
        ApiUtil.grabCommoditiesSearch(getApplication(),type,currentPage, new BaseJsonHttpResponseHandler<ArrayList<OneYuanBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<OneYuanBean> response) {
                if(currentPage == 1){
                    list.clear();
                }
                list.addAll(response);
                updateView();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<OneYuanBean> errorResponse) {
            }

            @Override
            protected ArrayList<OneYuanBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<OneYuanBean>>() {
                });
            }
        });
    }
    private void updateView() {
        oneyuanGrabAdapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(OneYuanGrab.this,OneYuanGrabItem.class);
                startActivity(intent);
            }
        });
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener
    {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
        {
            // 下拉刷新操作
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    currentPage = 1;
                    initData(type,currentPage);
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);

        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
        {
            // 加载操作
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    currentPage++;
                    if(currentPage <= totalPage)
                        initData(type,currentPage);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }

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
