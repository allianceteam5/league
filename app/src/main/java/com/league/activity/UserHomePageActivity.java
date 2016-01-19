package com.league.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMContactManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.UserInfoBean;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class UserHomePageActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.iv_thumb)
    CircleImageView ivThumb;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_friendcount)
    TextView tvFriendcount;
    @Bind(R.id.tv_fanscount)
    TextView tvFanscount;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.et_reason)
    EditText etReason;
    @Bind(R.id.addfriend)
    Button addfriend;
    private UserInfoBean userInfoBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        ButterKnife.bind(this);
        String phone = getIntent().getStringExtra(PHONE);
        showProgressDialog();
        ApiUtil.getUserDetail(UserHomePageActivity.this, phone, new BaseJsonHttpResponseHandler<UserInfoBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserInfoBean response) {
                userInfoBean = response;
                updateView();
                closeProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserInfoBean errorResponse) {
                closeProgressDialog();
            }

            @Override
            protected UserInfoBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<UserInfoBean>() {
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = etReason.getText().toString();
                if (TextUtils.isEmpty(reason)) {
//                    ToastUtils.showShortToast(UserHomePageActivity.this, "请输入申请理由");
//                    return;
                    reason = "请加我好友吧"; //默认的理由
                }
                if (userInfoBean == null){
                    ToastUtils.showShortToast(UserHomePageActivity.this, getString(R.string.warning_internet));
                    return;
                }

                addFriend(reason);
            }
        });
    }

    private void addFriend(final String reason) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMContactManager.getInstance().addContact(userInfoBean.getHxId(), reason);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void updateView() {
        if (!TextUtils.isEmpty(userInfoBean.getThumb()))
            Picasso.with(this).load(userInfoBean.getThumb()).resize(160, 160).centerCrop().into(ivThumb);
        tvNickname.setText(userInfoBean.getNickname());
        tvFriendcount.setText(userInfoBean.getFriendcount() + "");
        tvFanscount.setText(userInfoBean.getConcerncount() + "");
        tvSignature.setText(userInfoBean.getSignature());
    }

}
