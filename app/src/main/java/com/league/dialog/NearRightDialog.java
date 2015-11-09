package com.league.dialog;


import com.league.activity.MyJobHunting;
import com.league.activity.MyPost;
import com.league.activity.PostCard;
import com.league.activity.WantAJob;
import com.mine.league.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NearRightDialog extends Dialog implements
		android.view.View.OnClickListener {

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
				break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.want_job:
			if(Flag==1){
				Intent intent=new Intent(context,WantAJob.class);
				context.startActivity(intent);

			}else if(Flag==2){
				Intent intent2=new Intent(context, PostCard.class);
				context.startActivity(intent2);
			}
			dismiss();
			break;
		case R.id.mysearchjob:
			if(Flag==1){
				Intent intent1=new Intent(context,MyJobHunting.class);
				context.startActivity(intent1);

			}else if(Flag==2){
				Intent intent3= new Intent(context, MyPost.class);
				context.startActivity(intent3);
			}
			dismiss();
			break;
		
		default:
			break;
		}
	}
}
