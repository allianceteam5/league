package com.league.activity.treasure;

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
import com.league.activity.BaseActivity;
import com.league.adapter.LatestAnnouncedAdapter;
import com.league.bean.AnnouncedTheLatestBean;
import com.league.utils.api.ApiUtil;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LatestAnnounce extends BaseActivity implements View.OnClickListener {

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private GridView gridView;
    private LatestAnnouncedAdapter adapter;
    private List<AnnouncedTheLatestBean> list = new ArrayList<>();
    private int totalPage = 1;
    private int currentPage = 1;
    private PullToRefreshLayout pullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_announce);
        showProgressDialog();
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        titleright = (ImageView) findViewById(R.id.near_title_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("最新揭晓");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        gridView = (GridView) findViewById(R.id.gridview_latest);
        adapter = new LatestAnnouncedAdapter(list, getApplication());
        gridView.setAdapter(adapter);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullToRefreshLayout.setVisibility(View.GONE);
        initData(currentPage);
    }

    private void initData(final int currentPage) {
        //新接口获取即将揭晓
        ApiUtil.getTheLatest(getApplicationContext(), currentPage, new BaseJsonHttpResponseHandler<ArrayList<AnnouncedTheLatestBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<AnnouncedTheLatestBean> response) {
                if (currentPage == 1) {
                    list.clear();
                }
                list.addAll(response);
                updateView();
                pullToRefreshLayout.setVisibility(View.VISIBLE);
                closeProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<AnnouncedTheLatestBean> errorResponse) {
                closeProgressDialog();
            }

            @Override
            protected ArrayList<AnnouncedTheLatestBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<AnnouncedTheLatestBean>>() {
                });
            }
        });

//        ApiUtil.grabCommoditiesSearch(getApplication(), type, currentPage, new BaseJsonHttpResponseHandler<ArrayList<OneYuanBean>>() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<OneYuanBean> response) {
//                if (currentPage == 1) {
//                    list.clear();
//                }
//                list.addAll(response);
//                updateView();
//                pullToRefreshLayout.setVisibility(View.VISIBLE);
//                closeProgressDialog();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<OneYuanBean> errorResponse) {
//                closeProgressDialog();
//            }
//
//            @Override
//            protected ArrayList<OneYuanBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                JSONObject jsonObject = new JSONObject(rawJsonData);
//                totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
//                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<OneYuanBean>>() {
//                });
//            }
//        });
    }

    private void updateView() {
        adapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (list.get(position).getTbk().equals("1")) {
                    intent = new Intent(LatestAnnounce.this, OneYuanGrabItem.class);
                } else {
                    intent = new Intent(LatestAnnounce.this, TenYuanGrabItem.class);
                }
                intent.putExtra("id", list.get(position).getId());
                startActivity(intent);
            }
        });
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    currentPage = 1;
                    initData(currentPage);
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);

        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    currentPage++;
                    if (currentPage <= totalPage)
                        initData(currentPage);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
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
