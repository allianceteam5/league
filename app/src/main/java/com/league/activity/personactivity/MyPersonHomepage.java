package com.league.activity.personactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.league.activity.postbar.ConcernListActivity;
import com.league.activity.postbar.MessageListActivity;
import com.league.utils.IContants;
import com.mine.league.R;

public class MyPersonHomepage extends Activity implements View.OnClickListener, IContants {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_person_homepage);
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
                Intent intent2 = new Intent(getApplication(), MessageListActivity.class);
                intent2.putExtra(MODE, 2);
                startActivity(intent2);
                break;
            case R.id.mylikes:
                Intent intent1 = new Intent(getApplication(), MessageListActivity.class);
                intent1.putExtra(MODE, 1);
                startActivity(intent1);
                break;
        }
    }
}
