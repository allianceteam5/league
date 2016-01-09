package com.league.activity.near;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.HobbyInfoAdapter;
import com.league.adapter.JobInfoAdapter;
import com.league.adapter.OtherMessageAdapter;
import com.league.adapter.RecommendationInfoAdapter;
import com.league.bean.HobbyInfoBean;
import com.league.bean.JobInfoBean;
import com.league.bean.LiaoBaMessageBean;
import com.league.bean.OtherMessageBean;
import com.league.bean.RecommendationInfoBean;
import com.league.utils.Constants;
import com.league.utils.IContants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MyPost extends BaseActivity implements OnClickListener, IContants {

    private ImageView back, title_right, right;
    private ListView list;
    private TextView title;
    private List<JobInfoBean> jobInfoList;
    private JobInfoAdapter jobInfoAdapter;
    private List<RecommendationInfoBean> recommendationInfoList;
    private RecommendationInfoAdapter recommendationInfoAdapter;
    private List<HobbyInfoBean> hobbyInfoList;
    private HobbyInfoAdapter hobbyInfoAdapter;
    private List<OtherMessageBean> otherMessageList;
    private OtherMessageAdapter otherMessageAdapter;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        flag = getIntent().getIntExtra(MYPOST_FLAG, 0);
        init();
        initdata();
    }

    void init() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        title_right = (ImageView) findViewById(R.id.near_title_right);
        title_right.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        right = (ImageView) findViewById(R.id.near_right);
        right.setVisibility(View.GONE);
        list = (ListView) findViewById(R.id.list_myhunting);
    }

    void initdata() {
        showProgressDialog();
        switch (flag) {
            case 1:
                title.setText("我的求职");
                ApiUtil.applyjobSearch(getApplicationContext(), true, 0, "", -1,1, new BaseJsonHttpResponseHandler<ArrayList<JobInfoBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<JobInfoBean> response) {
                        jobInfoList = response;
                        updateApplyJobView();
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<JobInfoBean> errorResponse) {
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
                    }

                    @Override
                    protected ArrayList<JobInfoBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        return new ObjectMapper().readValue(jsonObject.optJSONArray("items").toString(), new TypeReference<ArrayList<JobInfoBean>>() {
                        });
                    }
                });
                break;
            case 2:
                title.setText("我的推荐");
                ApiUtil.recommendationsSearch(getApplicationContext(), true, 0, "", 0, 1, new BaseJsonHttpResponseHandler<ArrayList<RecommendationInfoBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<RecommendationInfoBean> response) {
                        recommendationInfoList = response;
                        updateRecommendationView();
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<RecommendationInfoBean> errorResponse) {
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
                    }

                    @Override
                    protected ArrayList<RecommendationInfoBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        return new ObjectMapper().readValue(jsonObject.optJSONArray("items").toString(), new TypeReference<ArrayList<RecommendationInfoBean>>() {
                        });
                    }
                });
                break;
            case 3:
                title.setText("我的交友");
                ApiUtil.hobbySearch(getApplicationContext(), true, 0, "", 1, new BaseJsonHttpResponseHandler<ArrayList<HobbyInfoBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<HobbyInfoBean> response) {
                        hobbyInfoList = response;
                        updateHobbyView();
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<HobbyInfoBean> errorResponse) {
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
                    }

                    @Override
                    protected ArrayList<HobbyInfoBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        return new ObjectMapper().readValue(jsonObject.optJSONArray("items").toString(), new TypeReference<ArrayList<HobbyInfoBean>>() {
                        });
                    }
                });
                break;
            case 4:
                title.setText("我的发帖");
                ApiUtil.othersList(getApplicationContext(), true, "", 1, new BaseJsonHttpResponseHandler<ArrayList<OtherMessageBean>>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<OtherMessageBean> response) {
                        otherMessageList = response;
                        updateOtherView();
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<OtherMessageBean> errorResponse) {
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
                    }

                    @Override
                    protected ArrayList<OtherMessageBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        return new ObjectMapper().readValue(jsonObject.optJSONArray("items").toString(), new TypeReference<ArrayList<OtherMessageBean>>() {
                        });
                    }
                });
                break;
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

    public void updateApplyJobView() {
        jobInfoAdapter = new JobInfoAdapter(getApplicationContext(), jobInfoList);
        list.setAdapter(jobInfoAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), JobInfoActivity.class);
                Paper.book().write(Constants.SingleInfoName, jobInfoList.get(position));
                startActivity(intent);
            }
        });
    }

    public void updateRecommendationView() {
        if (recommendationInfoAdapter == null) {
            recommendationInfoAdapter = new RecommendationInfoAdapter(getApplicationContext(), recommendationInfoList);
            list.setAdapter(recommendationInfoAdapter);
            list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getApplicationContext(), RecommendationInfoActivity.class);
                    Paper.book().write(Constants.SingleInfoName, recommendationInfoList.get(position));
                    startActivity(intent);
                }
            });
        } else {
            recommendationInfoAdapter.notifyDataSetChanged();
        }
    }

    public void updateHobbyView() {
        if (hobbyInfoAdapter == null) {
            hobbyInfoAdapter = new HobbyInfoAdapter(getApplicationContext(), hobbyInfoList);
            list.setAdapter(hobbyInfoAdapter);
            list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getApplicationContext(), HobbyInfoActivity.class);
                    Paper.book().write(Constants.SingleInfoName, hobbyInfoList.get(position));
                    startActivity(intent);
                }
            });
        } else {
            hobbyInfoAdapter.notifyDataSetChanged();
        }
    }

    public void updateOtherView(){
        if (otherMessageAdapter == null){
            otherMessageAdapter = new OtherMessageAdapter(getApplicationContext(), otherMessageList);
            list.setAdapter(otherMessageAdapter);
        } else {
            otherMessageAdapter.notifyDataSetChanged();
        }
    }
}
