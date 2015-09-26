package com.league.activity;

import java.util.ArrayList;
import java.util.List;

import com.league.adapter.JobInfoAdapter;
import com.league.bean.JobInfoBean;
import com.mine.league.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**  
 *   
 * @author liugang  
 * @date 2015年9月19日   
 */
public class SechJobInfo extends Activity implements OnClickListener{

	private ImageButton near_back,near_right;
	private TextView near_title;
	private ImageView near_t_rig;
	private int Flag;
	private ListView infoseach;
	private List<JobInfoBean> listdata=new ArrayList<JobInfoBean>();
	private JobInfoAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_searchinfo);
		Flag=getIntent().getIntExtra("mode", -1);
		if(Flag==-1){
			return ;
		}
		
		init();
		initData();
	}
	private void init(){
		near_back=(ImageButton) findViewById(R.id.near_back);
		near_title=(TextView) findViewById(R.id.near_centertitle);
		near_t_rig=(ImageView) findViewById(R.id.near_ti_right);
		near_right=(ImageButton) findViewById(R.id.near_right);
		infoseach=(ListView) findViewById(R.id.infosearch_list);
		if(Flag==1){
			
		}
		switch (Flag) {
		case 1:
			near_title.setText("求职信息");
			break;
		case 2:
			near_title.setText("特色推荐");
			break;
		case 3:
			near_title.setText("爱好交友");
			break;
		case 4:
			near_title.setText("顺风车");
			near_t_rig.setVisibility(View.INVISIBLE);
			break;
		case 5:
			near_title.setText("寻人启事");
			break;
		case 6:
			near_title.setText("其他");
			near_t_rig.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
		near_back.setOnClickListener(this);
		near_right.setOnClickListener(this);
	}
	public void initData(){
		switch (Flag) {
		case 1:
			//从服务器拉取数据
			for(int i=0;i<10;i++){
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
			infoseach.setAdapter(adapter);
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		default:
			break;
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.near_back:
			onBackPressed();
			finish();
			break;
		case R.id.near_centertitle:
			break;
		case R.id.near_right:
			break;
			
		}
	}
}
