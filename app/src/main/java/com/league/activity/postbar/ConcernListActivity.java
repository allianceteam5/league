package com.league.activity.postbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.LiaobaConcernListAdapter;
import com.league.bean.PopularityBean;
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

public class ConcernListActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.near_back)
    ImageButton nearBack;
    @Bind(R.id.near_title)
    TextView nearCentertitle;
    @Bind(R.id.near_title_right)
    ImageView nearTiRight;
    @Bind(R.id.near_right)
    ImageButton nearRight;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout pullToRefreshLayout;
    @Bind(R.id.liaoba_concern_list)
    ListView listView;
    private List<PopularityBean> list = new ArrayList<PopularityBean>();
    private LiaobaConcernListAdapter adapter;
    private int totalPage;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concern_list);
        ButterKnife.bind(this);

        nearBack.setOnClickListener(this);
        nearTiRight.setVisibility(View.GONE);
        nearCentertitle.setText("我的关注");
        nearRight.setVisibility(View.GONE);

        showProgressDialog();
        adapter = new LiaobaConcernListAdapter(list, getApplicationContext());
        listView.setAdapter(adapter);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullToRefreshLayout.setVisibility(View.GONE);

        initData(currentPage);
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

    private void initData(final int currentPage) {
        ApiUtil.liaobaGetMyConcernList(getApplicationContext(),currentPage, new BaseJsonHttpResponseHandler<ArrayList<PopularityBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<PopularityBean> response) {
                if (currentPage == 1) {
                    list.clear();
                }
                list.addAll(response);
                adapter.notifyDataSetChanged();
                pullToRefreshLayout.setVisibility(View.VISIBLE);
                closeProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<PopularityBean> errorResponse) {
                closeProgressDialog();
                Toast.makeText(getApplicationContext(), "哎呀网络不好", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected ArrayList<PopularityBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<PopularityBean>>() {
                });
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
}
