package com.league.activity;

import com.mine.league.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WantAJob extends Activity implements OnClickListener{

	private ImageView back1,back2,title_right,right,jobfull,jobpart;
	private Button publish;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wangjob);
		init();
	}

	void init(){
		back1=(ImageView) findViewById(R.id.near_back);
		back1.setVisibility(View.GONE);
		back2=(ImageView) findViewById(R.id.near_back_item);
		back2.setVisibility(View.VISIBLE);
		back2.setOnClickListener(this);
		title_right=(ImageView) findViewById(R.id.near_ti_right);
		title_right.setVisibility(View.GONE);
		title=(TextView) findViewById(R.id.near_centertitle);
		title.setText("我要求职");
		right=(ImageView) findViewById(R.id.near_right);
		right.setVisibility(View.GONE);
		jobfull=(ImageView) findViewById(R.id.img_full);
		jobpart=(ImageView) findViewById(R.id.img_part);
		publish=(Button) findViewById(R.id.publish);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.near_back_item:
			onBackPressed();
			finish();
			break;
		case R.id.fulljob:
			jobfull.setVisibility(View.VISIBLE);
			jobpart.setVisibility(View.INVISIBLE);
			
			break;
		case R.id.parttimejob:
			jobfull.setVisibility(View.INVISIBLE);
			jobpart.setVisibility(View.VISIBLE);
			
			break;
		case R.id.publish:
			break;
		}
	}
}
