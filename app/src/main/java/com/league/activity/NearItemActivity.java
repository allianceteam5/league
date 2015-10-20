package com.league.activity;

import com.mine.league.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**  
 *   
 * @author liugang  
 * @date 2015年9月26日   
 */
public class NearItemActivity extends Activity implements OnClickListener{

	private ImageView back1,back2,titleright,right1,right2;
	private TextView title;
	private Button addFriend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_near_jobinfo_item);
		initView();
	}

	private void initView(){
		back1=(ImageView) findViewById(R.id.near_back);
		back2=(ImageView) findViewById(R.id.near_back_item);
		back1.setVisibility(View.GONE);
		back2.setVisibility(View.VISIBLE);
		back2.setOnClickListener(this);
		titleright=(ImageView) findViewById(R.id.near_ti_right);
		titleright.setVisibility(View.GONE);
		title=(TextView) findViewById(R.id.near_centertitle);
		title.setText("我要求职");
		right1=(ImageView) findViewById(R.id.near_right);
		right1.setVisibility(View.GONE);
		right2=(ImageView) findViewById(R.id.near_right_item);
		right2.setVisibility(View.VISIBLE);
		addFriend=(Button) findViewById(R.id.addfriend);
		addFriend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.near_back_item:
			onBackPressed();
			break;
		case R.id.addfriend:
			Toast.makeText(getApplicationContext(), "add friend", Toast.LENGTH_LONG).show();
			break;
			
		}
	}
}
