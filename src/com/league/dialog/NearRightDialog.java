package com.league.dialog;


import com.mine.league.R;

import android.app.Dialog;
import android.content.Context;
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
		txt2=(TextView) findViewById(R.id.mysearchjob);
		
		init();
	}

	void init(){
		switch(Flag){
		case 1 :
			txt1.setText("我要求职");
			txt2.setText("我的求职");
			
			break;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.want_job:
			
			dismiss();
			break;
		case R.id.mysearchjob:
			
			
			dismiss();
			break;
		
		default:
			break;
		}
	}
}
