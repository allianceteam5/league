package com.league.activity.personactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.bean.SucessBean;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

public class FindCode extends BaseActivity implements View.OnClickListener{

    private ImageView  back2, titleright, right1, right2;
    private TextView title;
    private Button getCode,yanzheng;
    private TimeCount timeCount;
    @Bind(R.id.inputphone)
    EditText mInputPhone;
    @Bind(R.id.code)
    EditText mInputCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_code);
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
        title.setText("找回密码");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        getCode=(Button)findViewById(R.id.getcode);
        getCode.setOnClickListener(this);
        timeCount=new TimeCount(60000,1000);
        yanzheng= (Button) findViewById(R.id.yanzheng);
        yanzheng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getcode:
                if(mInputPhone.getText().length()<11){
                    ToastUtils.showShortToast(FindCode.this,"请输入完整手机号");
                }else{
                    timeCount.start();
                    ApiUtil.sendCpText(this, mInputPhone.getText().toString(), new BaseJsonHttpResponseHandler<SucessBean>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                            if (response.getFlag().equals("1")) {
                                ToastUtils.showShortToast(FindCode.this, "发送成功");
                            } else {
                                ToastUtils.showShortToast(FindCode.this, "发送失败");
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                            ToastUtils.showShortToast(FindCode.this, "发送失败");
                        }

                        @Override
                        protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                            });
                        }
                    });
                }
                break;
            case R.id.yanzheng:
                if(mInputPhone.getText().length()<11&& TextUtils.isEmpty(mInputCode.getText())){
                    showProgressDialog();
                    ApiUtil.checkPXText(FindCode.this, mInputPhone.getText().toString(), mInputCode.getText().toString(),
                            new BaseJsonHttpResponseHandler<SucessBean>() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                    closeProgressDialog();
                                    if(response.getFlag().equals("1")){
                                        Intent intent=new Intent(getApplication(),SetCode.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        ToastUtils.showShortToast(FindCode.this, "验证失败");
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                    closeProgressDialog();
                                    ToastUtils.showShortToast(FindCode.this, "发送失败");
                                }

                                @Override
                                protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                    });
                                }
                            });
                }else{
                    ToastUtils.showShortToast(FindCode.this, "请输入信息");
                }

                break;
        }
    }
    class TimeCount extends CountDownTimer{

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCode.setClickable(false);
            getCode.setText("还剩 "+millisUntilFinished/1000+"秒");
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
