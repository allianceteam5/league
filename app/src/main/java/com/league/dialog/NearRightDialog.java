package com.league.dialog;


import com.league.activity.near.HobbyInfoPublishActivity;
import com.league.activity.near.MyPost;
import com.league.activity.near.RecommendationInfoPublishActivity;
import com.league.activity.near.JobInfoPublishActivity;
import com.league.utils.IContants;
import com.mine.league.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NearRightDialog extends Dialog implements
		android.view.View.OnClickListener,IContants {

	Context context;
	private LinearLayout layout_want, layout_my;
	private int Flag=0;
	private TextView txt1,txt2;

	public NearRightDialog(Context context,int _flag) {
		super(context, R.style.FullHeightDialog);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_resource_tab);
		this.context = context;
		Flag=_flag;
		layout_want= (LinearLayout) findViewById(R.id.want_job);
		layout_want.setOnClickListener(this);
		layout_my = (LinearLayout) findViewById(R.id.mysearchjob);
		layout_my.setOnClickListener(this);
		txt1=(TextView) findViewById(R.id.searchjob);
		txt2=(TextView) findViewById(R.id.mine);
		init();
	}

	void init(){
		switch(Flag){
			case 1:
				txt1.setText("我要求职");
				txt2.setText("我的求职");
				break;
			case 2:
				txt1.setText("发布帖子");
				txt2.setText("我的帖子");
				break;
			case 3:
				txt1.setText("我要交友");
				txt2.setText("我的交友");
				break;
			case 4:
				txt1.setText("发布帖子");
				txt2.setText("我的帖子");
				break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.want_job:
			switch (Flag) {
				case 1:
					context.startActivity(new Intent(context, JobInfoPublishActivity.class));
					break;
				case 2:
					context.startActivity(new Intent(context, RecommendationInfoPublishActivity.class));
					break;
				case 3:
					context.startActivity(new Intent(context, HobbyInfoPublishActivity.class));
					break;
				case 4:
					break;
			}
			dismiss();
			break;
		case R.id.mysearchjob:
			Intent intent = new Intent(context, MyPost.class);
			intent.putExtra(MYPOST_FLAG,Flag);
			context.startActivity(intent);
			dismiss();
			break;
		default:
			break;
		}
	}
}
