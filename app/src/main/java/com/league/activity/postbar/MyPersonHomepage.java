package com.league.activity.postbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.UserInfoBean;
import com.league.utils.IContants;
import com.league.utils.StoreUtils;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.bean.Image;

public class MyPersonHomepage extends Activity implements View.OnClickListener, IContants {


    @Bind(R.id.iv_thumb)
    ImageView ivThumb;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_friendcount)
    TextView tvFriendcount;
    @Bind(R.id.tv_fanscount)
    TextView tvFanscount;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.iv_background)
    ImageView ivBackground;

    private UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_person_homepage);
        ButterKnife.bind(this);
        userInfoBean = StoreUtils.getUserInfo();
        if (!TextUtils.isEmpty(userInfoBean.getThumb()))
            Picasso.with(this).load(userInfoBean.getThumb()).resize(160, 160).centerCrop().placeholder(R.drawable.example).into(ivThumb);
        else
            Picasso.with(this).load(R.drawable.example).into(ivThumb);

//        if (!TextUtils.isEmpty(userInfoBean.getBackground()))
//            Picasso.with(this).load(userInfoBean.getBackground()).placeholder(R.drawable.mybackground).into(ivBackground);
//        else
//            Picasso.with(this).load(R.drawable.mybackground).into(ivBackground);
        tvNickname.setText(userInfoBean.getNickname());
        tvFriendcount.setText(userInfoBean.getFriendcount() + "");
        tvFanscount.setText(userInfoBean.getConcerncount() + "");
        tvSignature.setText(userInfoBean.getSignature());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                finish();
                break;
            case R.id.myconcern:
                Intent intent3 = new Intent(MyPersonHomepage.this, ConcernListActivity.class);
                startActivity(intent3);
                break;
            case R.id.mytopic:
                Intent intent2 = new Intent(MyPersonHomepage.this, MessageListActivity.class);
                intent2.putExtra(MODE, 2);
                startActivity(intent2);
                break;
            case R.id.mylikes:
                Intent intent1 = new Intent(MyPersonHomepage.this, MessageListActivity.class);
                intent1.putExtra(MODE, 1);
                startActivity(intent1);
                break;
        }
    }
}
