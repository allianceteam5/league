package com.league.activity.personactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.activity.personinfoactivity.InviteFriendActivity;
import com.league.utils.ActivityUtils;
import com.mine.league.R;

public class AlianceReward extends Activity {

    private ImageView back2, titleright, right1, right2;
    private TextView title,rules;
    private TextView tvInvite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliance_reward);
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
        title.setText("联盟奖励");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        rules= (TextView) findViewById(R.id.near_rule);
        rules.setVisibility(View.VISIBLE);
        tvInvite = (TextView) findViewById(R.id.tv_invite);
        tvInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.start_Activity(AlianceReward.this, InviteFriendActivity.class);
            }
        });
    }
}
