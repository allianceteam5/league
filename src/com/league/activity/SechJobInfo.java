package com.league.activity;

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
	
	private ListView infoseach;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_searchinfo);
		init();
	}
	private void init(){
		near_back=(ImageButton) findViewById(R.id.near_back);
		near_title=(TextView) findViewById(R.id.near_centertitle);
		near_t_rig=(ImageView) findViewById(R.id.near_ti_right);
		near_right=(ImageButton) findViewById(R.id.near_right);
		infoseach=(ListView) findViewById(R.id.infosearch_list);
		near_title.setText("求职信息");
		near_back.setOnClickListener(this);
		near_right.setOnClickListener(this);
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
