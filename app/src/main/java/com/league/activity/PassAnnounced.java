package com.league.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.league.adapter.PassAnnouncedAdapter;
import com.league.bean.PassAnnouncedBean;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class PassAnnounced extends Activity implements View.OnClickListener{

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    private ListView listView;
    private List<PassAnnouncedBean> list=new ArrayList<PassAnnouncedBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_announced);
        initView();
        initData();
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
        titleright = (ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
        title.setText("往期揭晓");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.INVISIBLE);
        listView= (ListView) findViewById(R.id.pass_list);
    }
    private void initData(){
        for(int i=0;i<5;i++){
            PassAnnouncedBean pab=new PassAnnouncedBean();
            pab.setPeroid(i);
            pab.setTime("2014-11-6");
            pab.setHolderName("小杜" + i);
            pab.setHolderId(i + "");
            pab.setLuckNum(i + "");
            pab.setTakPeoNum(i);
            list.add(pab);
        }
        listView.setAdapter(new PassAnnouncedAdapter(list,getApplication()));
    }

    @Override
    public void onClick(View v) {

    }
}
