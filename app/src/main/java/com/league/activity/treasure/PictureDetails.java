package com.league.activity.treasure;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mine.league.R;
import com.squareup.picasso.Picasso;

/**
 * Created by liug on 15/11/22.
 */
public class PictureDetails extends Activity implements View.OnClickListener{
    private ImageView back, titleright, right1, right2;
    private TextView title;
    private String details;
    private String[] pictures;
    private LinearLayout viewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picturedetails);
        details=getIntent().getStringExtra("picturesdetail");
        if("".equals(details)){
            return;
        }else{
            pictures=details.trim().split(" ");
        }
        initView();
    }
    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        titleright = (ImageView) findViewById(R.id.near_title_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("图片详情");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        viewGroup= (LinearLayout) findViewById(R.id.viewgroup);
        for(String picture :pictures){
            ImageView imageView=new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (!TextUtils.isEmpty(picture))
                Picasso.with(getApplicationContext()).load(picture).into(imageView);
            viewGroup.addView(imageView);
        }
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
