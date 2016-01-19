package com.league.activity.personactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.bean.SucessBean;
import com.league.bean.UserInfoBean;
import com.league.bean.UserRealInfoBean;
import com.league.utils.IContants;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Certification extends BaseActivity implements View.OnClickListener, IContants {

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    @Bind(R.id.inputname)
    EditText mInputName;
    @Bind(R.id.inputid)
    EditText mInputID;
    @Bind(R.id.bindid)
    Button mBind;
    @Bind(R.id.ll_notcertify)
    LinearLayout llNotCertify;
    @Bind(R.id.ll_hascertified)
    LinearLayout llHasCertified;
    @Bind(R.id.tv_text)
    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
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
        title.setText("实名认证");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        mBind.setOnClickListener(this);
        UserInfoBean userInfoBean = StoreUtils.getUserInfo();
        if (userInfoBean.getStatus() == 0) {
            llNotCertify.setVisibility(View.VISIBLE);
            llHasCertified.setVisibility(View.GONE);
        } else {
            tvText.setVisibility(View.GONE);
            mBind.setVisibility(View.GONE);
            llHasCertified.setVisibility(View.VISIBLE);
            showProgressDialog();
            ApiUtil.getCertificationInfo(this, new BaseJsonHttpResponseHandler<UserRealInfoBean>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserRealInfoBean response) {
                    closeProgressDialog();
                    mInputName.setText(response.getRealname());
                    mInputID.setText(response.getIdcard());
                    mInputID.setEnabled(false);
                    mInputName.setEnabled(false);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserRealInfoBean errorResponse) {
                    closeProgressDialog();
                }

                @Override
                protected UserRealInfoBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<UserRealInfoBean>() {
                    });
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bindid:
                if (TextUtils.isEmpty(mInputID.getText().toString()) || TextUtils.isEmpty(mInputID.getText().toString())){
                    ToastUtils.showShortToast(this, "请输入详细信息");
                }else{
                    showProgressDialog();
                    ApiUtil.realAuth(Certification.this, mInputName.getText().toString(),
                            mInputID.getText().toString(), new BaseJsonHttpResponseHandler<SucessBean>() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                    closeProgressDialog();
                                    if (response.getFlag().equals("1")) {
                                        ToastUtils.showShortToast(Certification.this, "绑定成功");
                                        //设置已实名认证
                                        UserInfoBean bean = StoreUtils.getUserInfo();
                                        bean.setStatus(1);
                                        StoreUtils.setUserInfo(bean);
                                        onBackPressed();
                                        finish();
                                    } else {
                                        ToastUtils.showShortToast(Certification.this, "绑定失败");
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                    closeProgressDialog();
                                    ToastUtils.showShortToast(Certification.this, "绑定失败");
                                }

                                @Override
                                protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                    });
                                }
                            });
                }

                break;
        }
    }
}