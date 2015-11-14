package com.league.activity.personactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.league.activity.liaobaactivity.MyLikes;
import com.league.activity.liaobaactivity.MyTopic;
import com.mine.league.R;

public class MyPersonHomepage extends Activity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_person_homepage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                finish();
                break;
            case R.id.myconcern:
                break;
            case R.id.mytopic:
                Intent intent1=new Intent(getApplication(), MyTopic.class);
                startActivity(intent1);
                break;
            case R.id.mylikes:
                Intent intent2 =new Intent(getApplication(), MyLikes.class);
                startActivity(intent2);
                break;
        }
    }
}
