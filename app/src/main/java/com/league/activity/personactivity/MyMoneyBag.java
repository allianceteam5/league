package com.league.activity.personactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mine.league.R;

public class MyMoneyBag extends Activity implements View.OnClickListener{

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money_bag);
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
        title.setText("钱包");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myredbagbalance:
                Intent intent=new Intent(getApplication(),MyRedBagBalance.class);
                startActivity(intent);
                break;
            case R.id.chongzhi:
                Intent intent1=new Intent(getApplication(),Recharge.class);
                startActivity(intent1);
                break;
            case R.id.tixian:
                Intent intent2 =new Intent(getApplication(),WithDraw.class);
                startActivity(intent2);
                break;
            case R.id.bankcardnum:
                Intent intent3 =new Intent(getApplication(),SelectBankcard.class);
                startActivity(intent3);
                break;
            case R.id.goldcoin:
                Intent intent4 = new Intent(getApplication(),MyGoldCoin.class);
                startActivity(intent4);
                break;
            case R.id.paypassword:
                Intent intent5=new Intent(getApplication(),PayPassword.class);
                startActivity(intent5);
                break;
        }
    }
}
