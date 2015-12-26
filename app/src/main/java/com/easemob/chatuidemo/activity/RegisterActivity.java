/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.chatuidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.personinfoactivity.CompletePersonInfo;
import com.league.bean.SucessBean;
import com.league.utils.Constants;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

import static android.view.View.VISIBLE;

/**
 * 注册页
 *
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back2, titleright, right1, right2;
    private TextView title;
    private Button getCode, register;
    private TimeCount timeCount;
    @Bind(R.id.inputphone)
    TextView mInputPhone;
    @Bind(R.id.inputcode)
    TextView mInputCode;
    @Bind(R.id.inputpwd)
    TextView mInputPwd;
    private String HxId;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {

        back2 = (ImageView) findViewById(R.id.near_back);

        back2.setVisibility(VISIBLE);
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
        title.setText("手机号注册");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        getCode = (Button) findViewById(R.id.getcode);
        getCode.setOnClickListener(this);
        timeCount = new TimeCount(60000, 1000);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcode:
                if (mInputPhone.length() == 11) {
                    timeCount.start();
                    ApiUtil.sendRGText(this, mInputPhone.getText().toString(), new BaseJsonHttpResponseHandler<SucessBean>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                            if (response.getFlag().equals("0")) {
                                ToastUtils.showShortToast(RegisterActivity.this, response.getMsg());
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                            ToastUtils.showShortToast(RegisterActivity.this, "发送失败");
                        }

                        @Override
                        protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                            });
                        }
                    });
                } else {
                    ToastUtils.showShortToast(this, "请输入正确手机号");
                }

                break;
            case R.id.register:
                final String phone = mInputPhone.getText().toString();
                if (phone.length() != 11) {
                    ToastUtils.showShortToast(this, "请输入正确手机号");
                    return;
                }

                String code = mInputCode.getText().toString();
                if (code.length() != 4) {
                    ToastUtils.showShortToast(this, "请输入4位正确的验证码");
                    return;
                }

                pwd = mInputPwd.getText().toString();
                if (pwd.length() < 6) {
                    ToastUtils.showShortToast(this, "密码不得少于6位");
                    return;
                }

                ApiUtil.signup(this, phone, code, pwd
                        , new BaseJsonHttpResponseHandler<SucessBean>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                        if (response.getFlag().equals("1")) {
                            StoreUtils.setPhone(phone);
                            StoreUtils.setHuanXinId(response.getHuanxinid());
                            ApiUtil.testPhone = phone;
                            startActivity(new Intent(RegisterActivity.this, CompletePersonInfo.class));
                            finish();
                        } else {
                            ToastUtils.showShortToast(RegisterActivity.this, "该账号已经注册过");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {

                        ToastUtils.showShortToast(RegisterActivity.this, "网络不好");
                    }

                    @Override
                    protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                        });
                    }
                });

                break;
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCode.setClickable(false);
            getCode.setText("还剩 " + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            getCode.setText("重新验证");
            getCode.setClickable(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.onFinish();
    }

}
