package com.league.utils;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mine.league.R;

public abstract class PersonInfoBaseActivity extends Activity {
    private TextView tvTitle;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = LayoutInflater.from(getApplicationContext()).inflate(getLayoutId(),null);
        setContentView(v);
        init();
        initView();
        initData();
    }
    private void init(){
        ImageButton ivBack = (ImageButton) findViewById(R.id.ib_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setTitle(String title) {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        if(!TextUtils.isEmpty(title))
            tvTitle.setText(title);
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();
}
