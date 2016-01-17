package com.league.activity.personactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.TradingRecordAdapter;
import com.league.bean.TradingDetailBean;
import com.league.utils.api.ApiUtil;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TradingRecordActivity extends BaseActivity {

    private ImageView back2, titleright, right1, right2;
    private TextView title;
    @Bind(R.id.lv_trading)
    ListView listView;
    TradingRecordAdapter adapter;
    List<TradingDetailBean> listData = new ArrayList<>();

    private int totalPage = 1;
    private int currentPage = 1;
    private PullToRefreshLayout pullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_record);
        ButterKnife.bind(this);
        initView();
        fillData(currentPage);
    }

    private void fillData(final int currentpage) {
        showProgressDialog();
        ApiUtil.getTradingRecord(this,currentpage, new BaseJsonHttpResponseHandler<ArrayList<TradingDetailBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<TradingDetailBean> response) {
                closeProgressDialog();
                if (currentPage == 1) {
                    listData.clear();
                }
                listData.addAll(response);
                adapter.notifyDataSetChanged();
                pullToRefreshLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<TradingDetailBean> errorResponse) {
                closeProgressDialog();
            }

            @Override
            protected ArrayList<TradingDetailBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<TradingDetailBean>>() {
                });
            }
        });
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

        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullToRefreshLayout.setVisibility(View.GONE);
    }
    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    currentPage = 1;
                    fillData(currentPage);
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
                        fillData( currentPage);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

    }

}
