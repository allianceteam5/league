package com.league.activity.treasure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.league.adapter.HorizonLastAdapter;
import com.league.adapter.HorizonListAdapter;
import com.league.adapter.OneyuanGridAdapter;
import com.league.bean.AnnouncedTheLatestBean;
import com.league.bean.OneYuanBean;
import com.league.bean.TenYuanGrabBean;
import com.league.view.HorizontalListView;
import com.league.view.MyGridView;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class OneYuan extends Activity implements View.OnClickListener {

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private HorizontalListView horizonList, horizonLast;
    private MyGridView gridView;
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
        horizonList = (HorizontalListView) findViewById(R.id.horizon_listview);
        horizonLast = (HorizontalListView) findViewById(R.id.horizon_last);
        gridView = (MyGridView) findViewById(R.id.gridview);
    }

    void initData() {
        for (int i = 0; i < 5; i++) {
            TenYuanGrabBean example = new TenYuanGrabBean();
            example.setmMoney(50 + i + "");
            example.setmTotalPeo(500);
            example.setmTakingPeo(200);
            listTenYuanGrab.add(example);
        }
        horizonList.setAdapter(new HorizonListAdapter(getApplication(), listTenYuanGrab));
        horizonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OneYuan.this, TenYuanGrabItem.class);
                startActivity(intent);
            }
        });
        for (int i = 0; i < 5; i++) {
            AnnouncedTheLatestBean atb = new AnnouncedTheLatestBean();
            atb.setmName("name" + i);
            listAnnounced.add(atb);
        }
        horizonLast.setAdapter(new HorizonLastAdapter(getApplication(), listAnnounced));
        horizonLast.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(OneYuan.this,AnnouncedTheLatestItem.class);
                startActivity(intent);
            }
        });
        for (int i = 0; i < 5; i++) {
            OneYuanBean oyb = new OneYuanBean();
            oyb.setmPeriod(i);
            oyb.setmName("name" + i);
            oyb.setmTotalPeo((long) i);
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
