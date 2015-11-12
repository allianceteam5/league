package com.league.activity.personactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mine.league.R;

import static android.view.View.VISIBLE;

public class FindCode extends Activity implements View.OnClickListener{

    private ImageView  back2, titleright, right1, right2;
    private TextView title;
    private Button getCode,yanzheng;
    private TimeCount timeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_code);
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
        titleright = (ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
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
                timeCount.start();
                break;
            case R.id.yanzheng:
                Intent intent=new Intent(getApplication(),ChangeCode.class);
                startActivity(intent);
                finish();
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
}
