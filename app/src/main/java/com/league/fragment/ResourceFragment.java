package com.league.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.league.activity.near.NearActivity;
import com.league.activity.postbar.LiaoBaActivity;
import com.league.activity.redpacket.GrabRedEnvolope;
import com.league.activity.treasure.OneYuan;
import com.league.adapter.ViewPaperAdapter;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author liugang
 * @date 2015年9月15日
 */
public class ResourceFragment extends Fragment implements OnPageChangeListener,OnClickListener{

	private View layout;
	private Activity ctx;
	private List<ImageView> imageViewList;
	private TextView tvDescription;
	private LinearLayout llPoints,viewpaperLayout;
	private String[] imageDescriptions;
	private int previousSelectPosition = 0;
	private ViewPager mViewPager;
	private boolean isLoop = true;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1)%imageViewList.size());
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(
					R.layout.fragment_resource, null);
			
			setView();
			initView();
			setOnClickListener();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	public void setView() {
		// 自动切换页面功能
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (isLoop) {
					SystemClock.sleep(2000);
					handler.sendEmptyMessage(0);
				}
			}
		}).start();
	}

	public void initView() {
		viewpaperLayout = (LinearLayout) layout.findViewById(R.id.autoviewpaper);
		mViewPager=new ViewPager(getActivity());
		DisplayMetrics dm=new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		mViewPager.setLayoutParams(new LinearLayout.LayoutParams(dm.widthPixels, dm.heightPixels  / 3));
		viewpaperLayout.addView(mViewPager);
		tvDescription = (TextView) layout.findViewById(R.id.tv_image_description);
		llPoints = (LinearLayout)layout.findViewById(R.id.ll_points);

		prepareData();

		ViewPaperAdapter adapter = new ViewPaperAdapter(imageViewList);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(this);

		tvDescription.setText(imageDescriptions[previousSelectPosition]);
		llPoints.getChildAt(previousSelectPosition).setEnabled(true);

	}

	private void setOnClickListener(){
		layout.findViewById(R.id.resource_yiyuanduobao).setOnClickListener(this);
		layout.findViewById(R.id.resource_qianghongbao).setOnClickListener(this);
		layout.findViewById(R.id.resource_fujin).setOnClickListener(this);
		layout.findViewById(R.id.resource_liaoba).setOnClickListener(this);
	}
	private void prepareData() {
		imageViewList = new ArrayList<ImageView>();
		int[] imageResIDs = getImageResIDs();
		imageDescriptions = getImageDescription();

		ImageView iv;
		View view;
		for (int i = 0; i < imageResIDs.length; i++) {
			iv = new ImageView(ctx);
			iv.setBackgroundResource(imageResIDs[i]);
			imageViewList.add(iv);

			// 添加点view对象
			view = new View(ctx);
			view.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
			LayoutParams lp = new LayoutParams(5, 5);
			lp.leftMargin = 10;
			view.setLayoutParams(lp);
			view.setEnabled(false);
			llPoints.addView(view);
		}
	}

	private int[] getImageResIDs() {
		return new int[] { R.drawable.back1, R.drawable.back2, R.drawable.back3,
				R.drawable.back1, R.drawable.back2 };
	}

	private String[] getImageDescription() {
		return new String[] { "1", "2", "3", "4",
				"5" };
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		// 改变图片的描述信息
		tvDescription
				.setText(imageDescriptions[arg0 % imageViewList.size()]);
		// 切换选中的点,把前一个点置为normal状态
		llPoints.getChildAt(previousSelectPosition).setEnabled(false);
		// 把当前选中的position对应的点置为enabled状态
		llPoints.getChildAt(arg0 % imageViewList.size()).setEnabled(true);
		previousSelectPosition = arg0 % imageViewList.size();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isLoop = false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.resource_yiyuanduobao:
			Intent intent3=new Intent(ctx, OneYuan.class);
			startActivity(intent3);
			break;
		case R.id.resource_qianghongbao:
			Intent intent2=new Intent(ctx, GrabRedEnvolope.class);
			startActivity(intent2);
			break;
		case R.id.resource_fujin:
			Intent intent=new Intent(ctx,NearActivity.class);
			startActivity(intent);
			break;
			case R.id.resource_liaoba:
				Intent intent4=new Intent(ctx, LiaoBaActivity.class);
				startActivity(intent4);
		}
	}
	
}
