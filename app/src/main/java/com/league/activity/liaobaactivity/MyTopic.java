package com.league.activity.liaobaactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.league.bean.LiaoBaUserInfo;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class MyTopic extends Activity {

    private ImageButton back,publish,mine,search;
    private TextView title;
    private ListView listView;
    private List<LiaoBaUserInfo> list=new ArrayList<LiaoBaUserInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_topic);
        initView();
        initData();
    }
    private void initView(){
        back= (ImageButton) findViewById(R.id.liaoba_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        publish= (ImageButton) findViewById(R.id.liaoba_publish);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mine =(ImageButton) findViewById(R.id.liaoba_mine);
        mine.setVisibility(View.GONE);
        search= (ImageButton) findViewById(R.id.liaoba_search);
        search.setVisibility(View.VISIBLE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        title= (TextView) findViewById(R.id.liaoba_centertitle);
        title.setText("我的话题");
        listView= (ListView) findViewById(R.id.mytopic_listview);
    }
    private void initData(){
//        for(int i=0;i<3;i++){
//            LiaoBaUserInfo lbi=new LiaoBaUserInfo();
////            lbi.setName("王思聪"+i);
////            lbi.setTime(i+"分钟前");
////            lbi.setHot_new_flag(1);
////            lbi.setTitle("标题"+i);
////            lbi.setContent("内容"+i);
////            lbi.setFlag_concern(i%2);
////            lbi.setDianzannum(i);
////            lbi.setCommentnum(i);
//            list.add(lbi);
//        }
//        listView.setAdapter(new LiaoBaAdapter(list,getApplication(),-1));
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplication(), TopicContent.class);
//                startActivity(intent);
//            }
//        });
    }
}
