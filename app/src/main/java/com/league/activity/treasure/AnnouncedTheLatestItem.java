package com.league.activity.treasure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.adapter.OneYuanGrabTakRecodAdapter;
import com.league.bean.OneYuanTakingMember;
import com.league.widget.ListViewForScrollView;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class AnnouncedTheLatestItem extends Activity implements View.OnClickListener{

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    private ListViewForScrollView listView;
    private List<OneYuanTakingMember> list=new ArrayList<OneYuanTakingMember>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announced_the_latest_item);
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
        title.setText("折叠单车");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.VISIBLE);
        listView= (ListViewForScrollView) findViewById(R.id.record_list);

    }
    public void initData(){
        for(int i=0;i<5;i++){
            OneYuanTakingMember oytm=new OneYuanTakingMember();
            oytm.setName("小杜 "+i);
            oytm.setTakeTime("15:34:13");
            oytm.setTakeNum(i);
            list.add(oytm);
        }
        listView.setAdapter(new OneYuanGrabTakRecodAdapter(list,getApplication()));
    }

    @Override
    public void onClick(View v) {

    }
}
