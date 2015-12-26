package com.league.activity.personinfoactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.SucessBean;
import com.league.bean.UserInfoBean;
import com.league.utils.Constants;
import com.league.utils.PersonInfoBaseActivity;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import io.paperdb.Paper;

public class SignatureActivity extends PersonInfoBaseActivity implements View.OnClickListener{
    private EditText mInputSignature;
    private Button mOption;
    private UserInfoBean userInfoBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("个性签名");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signature;
    }

    @Override
    protected void initView() {
        mInputSignature= (EditText) findViewById(R.id.inputsignature);
        mOption= (Button) findViewById(R.id.newadd);
        mOption.setVisibility(View.VISIBLE);
        mOption.setText("提交");
        mOption.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        userInfoBean= Paper.book().read("UserInfoBean");
        mInputSignature.setText(userInfoBean.getSignature());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newadd:
                ApiUtil.modifyUserDetailSignature(getApplicationContext(),mInputSignature.getText().toString(),
                        new BaseJsonHttpResponseHandler<SucessBean>() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                if(response.getFlag().equals("1")){
                                    userInfoBean.setSignature(""+mInputSignature.getText().toString());
                                    Paper.book().write("UserInfoBean",userInfoBean);
                                    onBackPressed();
                                    finish();
                                }else{
                                    ToastUtils.showShortToast(getApplicationContext(),"网络不好，再试试");
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                ToastUtils.showShortToast(getApplicationContext(),"网络不好，再试试");
                            }

                            @Override
                            protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                });
                            }
                        });
        }
    }
}
