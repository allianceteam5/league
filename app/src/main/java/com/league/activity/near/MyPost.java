package com.league.activity.near;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.league.adapter.RecommendationInfoAdapter;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class MyPost extends Activity implements OnClickListener{

    private ImageView back,title_right,right,delete;
    private ListView list;
    private TextView title;
//    private List<FeatureComInfo> listdata=new ArrayList<FeatureComInfo>();
    private RecommendationInfoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        init();
        initdata();
    }
    void init(){
        back=(ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        title_right=(ImageView) findViewById(R.id.near_ti_right);
        title_right.setVisibility(View.GONE);
        title=(TextView) findViewById(R.id.near_centertitle);
        title.setText("我的推荐");
        right=(ImageView) findViewById(R.id.near_right);
        right.setVisibility(View.GONE);
        delete=(ImageView) findViewById(R.id.near_right_delete);
        delete.setVisibility(View.VISIBLE);
        list=(ListView) findViewById(R.id.list_mypost);

    }
    void initdata(){
        for (int i = 0; i < 1; i++) {
//            FeatureComInfo fci = new FeatureComInfo();
//            fci.setUserNickname("userNickname" + i);
//            fci.setFea_location("fea_location" + i);
//            fci.setType("type" + i);
//            fci.setLasttime("lasttime" + i);
//            fci.setComnumber("" + i);
//            fci.setInfoContent("infoContent" + i);
//            fci.setSecContent("secContent" + i);
//            listdata.add(fci);
        }
//        adapter = new RecommendationInfoAdapter(getApplicationContext(), listdata);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                Intent intent2 = new Intent(getApplicationContext(), RecommendationInfoActivity.class);
                startActivity(intent2);

            }
        });
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
