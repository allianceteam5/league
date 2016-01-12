package com.league.activity.personactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class ChangeCode extends BaseActivity implements View.OnClickListener {

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title, commit;
    @Bind(R.id.pwd)
    EditText mPwd;
    @Bind(R.id.newpwd)
    EditText mNewPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_code);
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
        title.setText("修改密码");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        commit = (TextView) findViewById(R.id.near_commit);
        commit.setText("修改");
        commit.setVisibility(View.VISIBLE);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_commit:
                if (TextUtils.isEmpty(mPwd.getText()) || TextUtils.isEmpty(mNewPwd.getText())) {
                    ToastUtils.showShortToast(ChangeCode.this, "输入框不能为空");
                } else {
                    showProgressDialog();
                    ApiUtil.changePwd(ChangeCode.this, mPwd.getText().toString(),
                            mNewPwd.getText().toString(), new BaseJsonHttpResponseHandler<SucessBean>() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                    closeProgressDialog();
                                    if (response.getFlag().equals("1")) {
                                        ToastUtils.showShortToast(ChangeCode.this, "密码修改成功");
                                        finish();
                                    } else {
                                        ToastUtils.showShortToast(ChangeCode.this, "密码修改失败");
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                    closeProgressDialog();
                                    ToastUtils.showShortToast(ChangeCode.this, "密码修改失败");
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
