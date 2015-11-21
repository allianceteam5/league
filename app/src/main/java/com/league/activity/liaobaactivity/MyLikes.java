package com.league.activity.liaobaactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.league.bean.LiaoBaUserInfo;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class MyLikes extends Activity {

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    private ListView listView;
    private List<LiaoBaUserInfo> list=new ArrayList<LiaoBaUserInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_likes);
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
        title.setText("我的点赞");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        listView= (ListView) findViewById(R.id.mylikes_listview);
    }
    private void initData(){

//        for(int i=0;i<10;i++){
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
