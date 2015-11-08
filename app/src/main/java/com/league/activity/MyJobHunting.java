package com.league.activity;

import java.util.ArrayList;
import java.util.List;

import com.league.adapter.JobInfoAdapter;
import com.league.bean.JobInfoBean;
import com.mine.league.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyJobHunting extends Activity implements OnClickListener{

	private ImageView back,title_right,right,delete;
	private ListView list;
	private TextView title;
	private List<JobInfoBean> listdata=new ArrayList<JobInfoBean>();
	private JobInfoAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myjobhunting);
		init();
		initData();
	}
	void init(){
		back=(ImageView) findViewById(R.id.near_back);
		back.setOnClickListener(this);
		title_right=(ImageView) findViewById(R.id.near_ti_right);
		title_right.setVisibility(View.GONE);
		title=(TextView) findViewById(R.id.near_centertitle);
		title.setText("我的求职");
		right=(ImageView) findViewById(R.id.near_right);
		right.setVisibility(View.GONE);
		delete=(ImageView) findViewById(R.id.near_right_delete);
		delete.setVisibility(View.VISIBLE);
		list=(ListView) findViewById(R.id.list_myhunting);
		
	}
	void initData(){
		//从服务器拉取数据
		for(int i=0;i<1;i++){
			JobInfoBean jib=new JobInfoBean();
			jib.setUserNickname("nickname "+i);
			jib.setFullorpart_timejob(i%2);
			jib.setProfession("profession "+i);
			jib.setLasttime("lasttime "+i);
			jib.setInfoContent("infocontent "+i);
			jib.setEduction("edution "+i);
			jib.setWorktime("worktime "+i);
			jib.setCurrent_status("status "+i);
			jib.setLeave_message("leave message "+i);
			listdata.add(jib);
			
		}
		adapter=new JobInfoAdapter(getApplicationContext(), listdata);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(),NearItemActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.near_back:
			onBackPressed();
			finish();
			break;
		}
	}
}
