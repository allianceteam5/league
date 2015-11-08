package com.league.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import com.mine.league.R;
public class GrabRedEnvolope extends Activity implements OnClickListener{


    private ImageView back,titleright,right1,right2;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabredenvolope);
        initView();
    }
    private void initView(){
        back=(ImageView) findViewById(R.id.near_back);
        back.setVisibility(View.GONE);
        titleright=(ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title=(TextView) findViewById(R.id.near_centertitle);
        title.setText("抢红包");
        right1=(ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2=(ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.near_back:
                onBackPressed();
                finish();
                break;

        }
    }
}