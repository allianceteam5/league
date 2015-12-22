package com.league.activity.circle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chatuidemo.domain.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.CircleMessageAdapter;
import com.league.adapter.LiaobaConcernListAdapter;
import com.league.bean.CircleMessageBean;
import com.league.bean.PopularityBean;
import com.league.bean.UserInfoBean;
import com.league.otto.BusProvider;
import com.league.utils.ActivityUtils;
import com.league.utils.IContants;
import com.league.utils.Utils;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.league.widget.NoScrollListView;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class CircleActivity extends BaseActivity implements View.OnClickListener,IContants{

    @Bind(R.id.ib_back)
    ImageView ibBack;
    @Bind(R.id.iv_publish)
    ImageView ivPublish;
    ImageView ivThumb;
    TextView tvName;
    TextView tvFriendcount;
    TextView tvLikecount;
    TextView tvSignature;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout pullToRefreshLayout;
    private List<CircleMessageBean> list = new ArrayList<CircleMessageBean>();
    private CircleMessageAdapter adapter;
    private int totalPage;
    private int currentPage = 1;
    private UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);
        ibBack.setOnClickListener(this);
        ivPublish.setOnClickListener(this);

        View headerView = getLayoutInflater().inflate(R.layout.layout_circle_head,null);
        ivThumb = (ImageView) headerView.findViewById(R.id.iv_thumb);
        tvName =(TextView) headerView.findViewById(R.id.tv_name);
        tvFriendcount= (TextView) headerView.findViewById(R.id.tv_friendcount);
        tvLikecount = (TextView) headerView.findViewById(R.id.tv_likecount);
        tvSignature = (TextView) headerView.findViewById(R.id.tv_signature);
        listview.addHeaderView(headerView);
        showProgressDialog();
        adapter = new CircleMessageAdapter(this, list);
        listview.setAdapter(adapter);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullToRefreshLayout.setVisibility(View.GONE);

        userInfoBean = Paper.book().read(UserInfo);
        Picasso.with(this).load(userInfoBean.getThumb()).placeholder(R.drawable.default_avatar).into(ivThumb);
        tvName.setText(userInfoBean.getNickname());
        tvFriendcount.setText(userInfoBean.getFriendcount()+"");
        tvLikecount.setText(userInfoBean.getConcerncount()+"");
        tvSignature.setText(userInfoBean.getSignature());
        initData(currentPage);
    }

    private void initData(final int currentPage) {
        ApiUtil.circleMessageGet(this, currentPage, new BaseJsonHttpResponseHandler<ArrayList<CircleMessageBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<CircleMessageBean> response) {
                if (currentPage == 1) {
                    list.clear();
                }
                list.addAll(response);
                adapter.setData(list);
                pullToRefreshLayout.setVisibility(View.VISIBLE);
                closeProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<CircleMessageBean> errorResponse) {
                closeProgressDialog();
                Toast.makeText(getApplicationContext(), "哎呀网络不好", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected ArrayList<CircleMessageBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<CircleMessageBean>>() {
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.iv_publish:
                ActivityUtils.start_Activity(this, CirclePublishActivity.class);
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
