package com.league.activity.circle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.CircleMessageAdapter;
import com.league.bean.CircleMessageBean;
import com.league.bean.UserInfoBean;
import com.league.otto.RefreshEvent;
import com.league.utils.ActivityUtils;
import com.league.utils.IContants;
import com.league.utils.api.ApiUtil;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class CircleActivity extends BaseActivity implements View.OnClickListener, IContants {

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

//    private EditText etInput;
//    private TextView tvSend;
//    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);
        ibBack.setOnClickListener(this);
        ivPublish.setOnClickListener(this);

        View headerView = getLayoutInflater().inflate(R.layout.layout_circle_head, null);
        ivThumb = (ImageView) headerView.findViewById(R.id.iv_thumb);
        tvName = (TextView) headerView.findViewById(R.id.tv_name);
        tvFriendcount = (TextView) headerView.findViewById(R.id.tv_friendcount);
        tvLikecount = (TextView) headerView.findViewById(R.id.tv_likecount);
        tvSignature = (TextView) headerView.findViewById(R.id.tv_signature);
        listview.addHeaderView(headerView);
        showProgressDialog();
        adapter = new CircleMessageAdapter(this, list, getWindow().getDecorView());
        listview.setAdapter(adapter);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullToRefreshLayout.setVisibility(View.GONE);

        userInfoBean = Paper.book().read(UserInfo);
        if (!TextUtils.isEmpty(userInfoBean.getThumb()))
            Picasso.with(this).load(userInfoBean.getThumb()).placeholder(R.drawable.example).resize(120, 120).centerCrop().into(ivThumb);
        else
            Picasso.with(this).load(R.drawable.example).into(ivThumb);
        tvName.setText(userInfoBean.getNickname());
        tvFriendcount.setText(userInfoBean.getFriendcount() + "");
        tvLikecount.setText(userInfoBean.getConcerncount() + "");
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
        switch (v.getId()) {
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

    @Subscribe
    public void refreshEvent(RefreshEvent event) {
        currentPage = 1;
        // 更新数据
        initData(currentPage);
    }

//    public void closePopupWindow() {
//        if (popupWindow != null && popupWindow.isShowing())
//            popupWindow.dismiss();
//    }
//
//    public PopupWindow createPopupWindow() {
//        View view = getLayoutInflater().inflate(R.layout.layout_item_popup_comment, null);
//        tvSend = (TextView) view.findViewById(R.id.tv_send);
//        etInput = (EditText) view
//                .findViewById(R.id.et_input);
//        etInput.setFocusableInTouchMode(true);
//        etInput.requestFocus();
//        InputMethodManager inputManager = (InputMethodManager) etInput
//                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
//        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        popupWindow.setAnimationStyle(R.style.popwindow_anim_style);
//        return popupWindow;
//    }

}
