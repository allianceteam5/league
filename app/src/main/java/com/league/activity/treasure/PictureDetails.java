package com.league.activity.treasure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.league.adapter.PicturesDetailAdapter;
import com.mine.league.R;

/**
 * Created by liug on 15/11/22.
 */
public class PictureDetails extends Activity implements View.OnClickListener{
    private ImageView back, titleright, right1, right2;
    private TextView title;
    private ListView listView;
    private String details;
    private String[] pictures;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picturedetails);
        details=getIntent().getStringExtra("picturesdetail");
        if("".equals(details)){
            return;
        }else{
            pictures=details.split(" ");
        }
        initView();
    }
    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        titleright = (ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
        title.setText("图片详情");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        listView = (ListView) findViewById(R.id.listview_details);
        listView.setAdapter(new PicturesDetailAdapter(pictures,getApplication()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;

        }
    }
}
