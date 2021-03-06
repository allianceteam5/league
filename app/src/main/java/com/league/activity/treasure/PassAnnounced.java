package com.league.activity.treasure;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.PassAnnouncedAdapter;
import com.league.bean.PassAnnouncedBean;
import com.league.utils.api.ApiUtil;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PassAnnounced extends BaseActivity {

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    private ListView listView;
    private List<PassAnnouncedBean> list = new ArrayList<PassAnnouncedBean>();
    private int totalPage = 1;
    private int currentPage = 1;
    private int type = 0;//0表示10夺金  1表示一元夺宝
    private PullToRefreshLayout pullToRefreshLayout;
    private PassAnnouncedAdapter adapter;
    private String kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_announced);
        kind = getIntent().getStringExtra("kind");
        type = getIntent().getIntExtra("type", -1);
        if (type == -1) {
            return;
        }
        showProgressDialog();
        initView();
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
        title.setText("往期揭晓");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.INVISIBLE);
        listView = (ListView) findViewById(R.id.pass_list);
        adapter = new PassAnnouncedAdapter(list, getApplication());
        listView.setAdapter(adapter);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullToRefreshLayout.setVisibility(View.GONE);
        initData(type, currentPage);
    }

    private void initData(int type, final int currentPage) {
        if (type == 1) {
            ApiUtil.grabcommoditiesPassAnnounced(getApplication(), kind, currentPage, new BaseJsonHttpResponseHandler<ArrayList<PassAnnouncedBean>>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<PassAnnouncedBean> response) {
                    if (currentPage == 1) {
                        list.clear();
                    }
                    list.addAll(response);
                    if (list.get(0).getEnd_at().equals("0")) {
                        list.remove(0);
                    }

                    adapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(PassAnnounced.this, OneYuanGrabItem.class);
                            intent.putExtra("id", list.get(position).getId());
                            startActivity(intent);
                        }
                    });
                    pullToRefreshLayout.setVisibility(View.VISIBLE);
                    closeProgressDialog();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<PassAnnouncedBean> errorResponse) {
                    closeProgressDialog();
                }

                @Override
                protected ArrayList<PassAnnouncedBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    JSONObject jsonObject = new JSONObject(rawJsonData);
                    totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                    return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<PassAnnouncedBean>>() {
                    });
                }
            });
        } else if (type == 0) {
            ApiUtil.grabcornsPassAnnounced(getApplication(), kind, currentPage, new BaseJsonHttpResponseHandler<ArrayList<PassAnnouncedBean>>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<PassAnnouncedBean> response) {
                    if (currentPage == 1) {
                        list.clear();
                    }
                    list.addAll(response);
                    if (list.get(0).getEnd_at().equals("0")) {
                        list.remove(0);
                    }

                    adapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(PassAnnounced.this, TenYuanGrabItem.class);
                            intent.putExtra("id", list.get(position).getId());
                            startActivity(intent);
                        }
                    });
                    pullToRefreshLayout.setVisibility(View.VISIBLE);
                    closeProgressDialog();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<PassAnnouncedBean> errorResponse) {
                    closeProgressDialog();
                }

                @Override
                protected ArrayList<PassAnnouncedBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    JSONObject jsonObject = new JSONObject(rawJsonData);
                    totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                    return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<PassAnnouncedBean>>() {
                    });
                }
            });
        }
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    currentPage = 1;
                    initData(type, currentPage);
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
                        initData(type, currentPage);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

    }
}
