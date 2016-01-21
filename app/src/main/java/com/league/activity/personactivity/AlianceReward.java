package com.league.activity.personactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.RuleActivity;
import com.league.activity.personinfoactivity.InviteFriendActivity;
import com.league.bean.BankCardInfo;
import com.league.bean.SucessBean;
import com.league.bean.UserInfoBean;
import com.league.utils.ActivityUtils;
import com.league.utils.IContants;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import java.util.ArrayList;

public class AlianceReward extends Activity implements IContants {

    private ImageView back2, titleright, right1, right2;
    private TextView title, rules;
    private TextView tvInvite,tvMoney;
    private Button applyforTake;
    private UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliance_reward);
        initView();
        initData();
    }

    private void initData() {
        userInfoBean = StoreUtils.getUserInfo();
        tvMoney.setText(userInfoBean.getAlliancerewards()+"");
        if(userInfoBean.getStatus()==2){
            applyforTake.setText("正在提取");
            applyforTake.setEnabled(false);
        }else if(userInfoBean.getStatus()==3){
            applyforTake.setEnabled(false);
            applyforTake.setText("已提取");
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
        title.setText("联盟奖励");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        rules = (TextView) findViewById(R.id.near_rule);
        rules.setVisibility(View.VISIBLE);
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rule = new Intent(getApplication(), RuleActivity.class);
                rule.putExtra(RuleType, 1);
                startActivity(rule);
            }
        });
        tvInvite = (TextView) findViewById(R.id.tv_invite);
        tvInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.start_Activity(AlianceReward.this, InviteFriendActivity.class);
            }
        });
        applyforTake = (Button) findViewById(R.id.applyfortake);
        applyforTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtil.getUserBankcardList(AlianceReward.this, new BaseJsonHttpResponseHandler<ArrayList<BankCardInfo>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<BankCardInfo> response) {
                        if (response.size() == 0) {
                            ToastUtils.showShortToast(AlianceReward.this, "您还没有添加过银行卡");
                        } else {
                            ToastUtils.showShortToast(AlianceReward.this, "默认选择您银行卡列表中第一张银行卡");
                            ApiUtil.rewardOut(AlianceReward.this, response.get(0).getId()+"", userInfoBean.getAlliancerewards() + "", new BaseJsonHttpResponseHandler<SucessBean>() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                    if(response.getFlag().equals("1")){
                                        applyforTake.setText("已领取");
                                        applyforTake.setEnabled(false);
                                    }else{
                                        ToastUtils.showShortToast(AlianceReward.this,"提取失败");
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                    ToastUtils.showShortToast(AlianceReward.this,"网络失败");
                                }

                                @Override
                                protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<BankCardInfo> errorResponse) {
                        ToastUtils.showShortToast(AlianceReward.this, "网络不给力");
                    }

                    @Override
                    protected ArrayList<BankCardInfo> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<BankCardInfo>>() {
                        });
                    }
                });


            }
        });
        tvMoney = (TextView) findViewById(R.id.money);
    }
}
