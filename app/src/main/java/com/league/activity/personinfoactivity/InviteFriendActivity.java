package com.league.activity.personinfoactivity;

import android.os.Bundle;

import com.league.utils.PersonInfoBaseActivity;
import com.mine.league.R;

public class InviteFriendActivity extends PersonInfoBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("邀请好友");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
