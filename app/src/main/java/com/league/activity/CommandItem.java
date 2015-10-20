package com.league.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mine.league.R;

public class CommandItem extends Activity implements OnClickListener{

    private ImageView back1,back2,titleright,right1,right2;
    private TextView title;
    private EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_near_command_item);
        initView();
    }
    private void initView(){
        back1=(ImageView) findViewById(R.id.near_back);
        back2=(ImageView) findViewById(R.id.near_back_item);
        back1.setVisibility(View.GONE);
        back2.setVisibility(View.VISIBLE);
        back2.setOnClickListener(this);
        titleright=(ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title=(TextView) findViewById(R.id.near_centertitle);
        title.setText("详细");
        right1=(ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2=(ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.VISIBLE);
        comment= (EditText) findViewById(R.id.comment);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.near_back_item:
                onBackPressed();
                finish();
                break;

        }
    }
}
