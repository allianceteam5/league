package com.league.activity.personactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.league.adapter.BankCardAdapter;
import com.league.bean.BankCardInfo;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class SelectBankcard extends Activity implements View.OnClickListener{

    private List<BankCardInfo> list=new ArrayList<BankCardInfo>();
    private ImageView back2, titleright, right1, right2;
    private TextView title;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bankcard);
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
        titleright = (ImageView) findViewById(R.id.near_title_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("选择银行卡");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        listView= (ListView) findViewById(R.id.listview);
    }
    private void initData(){
        for (int i=0;i<3;i++){
            BankCardInfo bci=new BankCardInfo();
            bci.setCardID("123456789");
            bci.setKaihudi("杭州");
            bci.setName("刘刚");
            bci.setUserID("987654321");
            bci.setUserNumber("17244738345");
            bci.setBankName("招行银行");
            list.add(bci);
        }
        listView.setAdapter(new BankCardAdapter(list,getApplication()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onBackPressed();
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addbankcard:
                Intent intent=new Intent(getApplication(),AddBankCard.class);
                startActivity(intent);
                break;
        }
    }
}
