package com.league.activity.near;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mine.league.R;

public class PostCard extends Activity implements View.OnClickListener {

    private ImageView back,title_right,right,jobfull,jobpart;
    private Button publish;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postcard);
        init();
    }
    void init(){
        back=(ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        title_right=(ImageView) findViewById(R.id.near_ti_right);
        title_right.setVisibility(View.GONE);
        title=(TextView) findViewById(R.id.near_centertitle);
        title.setText("发表推荐");
        right=(ImageView) findViewById(R.id.near_right);
        right.setVisibility(View.GONE);
        jobfull=(ImageView) findViewById(R.id.img_full);
        jobpart=(ImageView) findViewById(R.id.img_part);
        publish=(Button) findViewById(R.id.publish);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
        }
    }
}
