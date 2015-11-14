package com.league.activity.treasure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.league.adapter.OneyuanGridAdapter;
import com.league.bean.AnnouncedTheLatestBean;
import com.league.bean.OneYuanBean;
import com.league.bean.TenYuanGrabBean;
import com.league.view.MyGridView;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class OneYuan extends Activity implements View.OnClickListener {

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private MyGridView gridView;

    private ImageView tenImage1,tenImage2,tenImage3;
    private TextView money1,money2,money3;
    private TextView txtProgress1,txtProgress2,txtProgress3;
    private ProgressBar progressbar1,progressbar2,progressbar3;
    private List<TenYuanGrabBean> listTenYuanGrab = new ArrayList<TenYuanGrabBean>();
    private List<AnnouncedTheLatestBean> listAnnounced = new ArrayList<AnnouncedTheLatestBean>();
    private List<OneYuanBean> listGrid = new ArrayList<OneYuanBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_yuan);
        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        titleright = (ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
        title.setText("一元夺宝");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        gridView = (MyGridView) findViewById(R.id.gridview);
        tenImage1= (ImageView) findViewById(R.id.ten_image1);
        tenImage2= (ImageView) findViewById(R.id.ten_image2);
        tenImage3= (ImageView) findViewById(R.id.ten_image3);
        money1= (TextView) findViewById(R.id.money1);
        money2= (TextView) findViewById(R.id.money2);
        money3= (TextView) findViewById(R.id.money3);
        txtProgress1= (TextView) findViewById(R.id.txt_progress1);
        txtProgress2= (TextView) findViewById(R.id.txt_progress2);
        txtProgress3= (TextView) findViewById(R.id.txt_progress3);
        progressbar1= (ProgressBar) findViewById(R.id.progressbar1);
        progressbar1.setProgress(50);
        progressbar2= (ProgressBar) findViewById(R.id.progressbar2);
        progressbar3= (ProgressBar) findViewById(R.id.progressbar3);
    }

    void initData() {
        for (int i = 0; i < 5; i++) {
            TenYuanGrabBean example = new TenYuanGrabBean();
            example.setmMoney(50 + i + "");
            example.setmTotalPeo(500);
            example.setmTakingPeo(200);
            listTenYuanGrab.add(example);
        }

        for (int i = 0; i < 5; i++) {
            AnnouncedTheLatestBean atb = new AnnouncedTheLatestBean();
            atb.setmName("name" + i);
            listAnnounced.add(atb);
        }

        for (int i = 0; i < 5; i++) {
            OneYuanBean oyb = new OneYuanBean();
//            oyb.setmPeriod(i);
//            oyb.setmName("name" + i);
//            oyb.setmTotalPeo((long) i);i
            listGrid.add(oyb);
        }
        gridView.setAdapter(new OneyuanGridAdapter(getApplication(), listGrid));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(OneYuan.this,OneYuanGrabItem.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.tengrb_more:
            case R.id.One_tengrab:
                Intent intent1 = new Intent(OneYuan.this, TenyuanGrid.class);
                startActivity(intent1);

                break;
            case R.id.last_more:
            case R.id.One_Yuan:
                Intent intent2 = new Intent(OneYuan.this, OneYuanGrab.class);
                startActivity(intent2);

                break;
            case R.id.One_record:
                Toast.makeText(this, "rec", Toast.LENGTH_LONG).show();
                break;
            case R.id.One_question:
                Toast.makeText(this, "que", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
