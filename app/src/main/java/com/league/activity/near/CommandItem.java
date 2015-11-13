package com.league.activity.near;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mine.league.R;

public class CommandItem extends Activity implements View.OnClickListener {

    private ImageView back,titleright,right1,right2;
    private TextView title;
    private EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_near_command_item);
        initView();
    }
    private void initView(){
        back=(ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
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
            case R.id.near_back:
                onBackPressed();
                finish();
                break;

        }
    }
}
