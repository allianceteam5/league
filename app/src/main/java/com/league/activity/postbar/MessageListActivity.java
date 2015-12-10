package com.league.activity.postbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.LiaoBaAdapter;
import com.league.bean.LiaoBaMessageBean;
import com.league.utils.IContants;
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

public class MessageListActivity extends BaseActivity implements View.OnClickListener,IContants{
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
    private  List<LiaoBaMessageBean> list=new ArrayList<LiaoBaMessageBean>();
    private LiaoBaAdapter adapter;
    private int totalPage;
    private int currentPage = 1;
    private int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concern_list);
        ButterKnife.bind(this);
        mode = getIntent().getIntExtra(MODE,0);
        nearBack.setOnClickListener(this);
        nearTiRight.setVisibility(View.GONE);
        nearRight.setVisibility(View.GONE);
        switch (mode){
            case 1:
                nearCentertitle.setText("我的点赞");
                break;
            case 2:
                nearCentertitle.setText("我的话题");
                break;
        }

        adapter = new LiaoBaAdapter(list, getApplicationContext(), 2);
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
        switch (mode){
            case 1:
                ApiUtil.liaobaTbmessagesMyLikesList(getApplicationContext(), currentPage, new BaseJsonHttpResponseHandler<ArrayList<LiaoBaMessageBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<LiaoBaMessageBean> response) {
                        if (currentPage == 1) {
                            list.clear();
                        }
                        Log.i("test", response.size() + "");
                        list.addAll(response);
                        adapter.notifyDataSetChanged();
                        pullToRefreshLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<LiaoBaMessageBean> errorResponse) {
                        Toast.makeText(getApplicationContext(), "哎呀网络不好", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected ArrayList<LiaoBaMessageBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                        return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<LiaoBaMessageBean>>() {
                        });
                    }
                });
                break;
            case 2:
                ApiUtil.liaobaTbmessagesMyList(getApplicationContext(), currentPage, new BaseJsonHttpResponseHandler<ArrayList<LiaoBaMessageBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<LiaoBaMessageBean> response) {
                        if (currentPage == 1) {
                            list.clear();
                        }
                        Log.i("test", response.size() + "");
                        list.addAll(response);
                        adapter.notifyDataSetChanged();
                        pullToRefreshLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<LiaoBaMessageBean> errorResponse) {
                        Toast.makeText(getApplicationContext(), "哎呀网络不好", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected ArrayList<LiaoBaMessageBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                        return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<LiaoBaMessageBean>>() {
                        });
                    }
                });
                break;
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
