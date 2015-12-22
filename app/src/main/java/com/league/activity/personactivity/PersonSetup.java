package com.league.activity.personactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.utils.DataCleanManager;
import com.league.utils.ToastUtils;
import com.mine.league.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonSetup extends Activity implements View.OnClickListener{

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    @Bind(R.id.cachesize)
    TextView mCachesize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_setup);
        ButterKnife.bind(this);
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
        title.setText("设置");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mCachesize.setText(DataCleanManager.getTotalCacheSize(this)+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.aboutus:
                Intent intent=new Intent(getApplication(),AboutUs.class);
                startActivity(intent);
                break;
            case R.id.feedback:
                Intent intent1=new Intent(getApplication(),FeedBack.class);
                startActivity(intent1);
                break;
            case R.id.idrelated:
                Intent intent2 =new Intent(getApplication(),IDRelated.class);
                startActivity(intent2);
                break;
            case R.id.clearallcache:
                DataCleanManager.clearAllCache(getApplicationContext());
                ToastUtils.showShortToast(this, "清理完成");
                try {
                    mCachesize.setText(DataCleanManager.getTotalCacheSize(PersonSetup.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
