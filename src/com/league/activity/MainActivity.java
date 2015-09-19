package com.league.activity;


import com.league.fragment.FriendsFragment;
import com.league.fragment.MessageFragment;
import com.league.fragment.PersonFragment;
import com.league.fragment.ResourceFragment;
import com.mine.league.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

	private int index;
	private TextView title;//当前页标题
	private ImageView[] tabButtons;//下方四个tab
	private TextView[] tabText;
	private int currentTabIndex=0;//当前tab
	
	private Fragment[] fragments;
	private MessageFragment messageFragment;
	private FriendsFragment friendsFragment;
	private ResourceFragment resourceFragment;
	private PersonFragment personFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		init();
	}
	public void init(){
		title=(TextView) findViewById(R.id.txt_title);
		tabButtons=new ImageView[4];
		tabButtons[0]=(ImageView) findViewById(R.id.ib_home);
		tabButtons[1]=(ImageView) findViewById(R.id.ib_interact);
		tabButtons[2]=(ImageView) findViewById(R.id.ib_recom);
		tabButtons[3]=(ImageView) findViewById(R.id.ib_personal);		
		tabButtons[0].setSelected(true);//默认第一个tab被选中
		title.setText("消息");
		
		tabText=new TextView[4];
		tabText[0]=(TextView) findViewById(R.id.tv_home);
		tabText[1]=(TextView) findViewById(R.id.tv_interact);
		tabText[2]=(TextView) findViewById(R.id.tv_recom);
		tabText[3]=(TextView) findViewById(R.id.tv_personal);
		tabText[0].setTextColor(Color.GREEN);
		
		resourceFragment = new ResourceFragment();
		messageFragment = new MessageFragment();
		friendsFragment = new FriendsFragment();
		personFragment =new PersonFragment();
		fragments=new Fragment[]{messageFragment,friendsFragment,resourceFragment,personFragment};
		getFragmentManager().beginTransaction()
		.add(R.id.fragment_container, resourceFragment)
		.add(R.id.fragment_container, messageFragment)
		.add(R.id.fragment_container, friendsFragment)
		.add(R.id.fragment_container, personFragment)
		.hide(friendsFragment)
		.hide(resourceFragment)
		.hide(personFragment).show(messageFragment).commit();
	}
	public void onTabClicked(View view) {
		
		switch (view.getId()) {
		case R.id.re_home:
			title.setText("消息");			
			index = 0;			
			break;
		case R.id.re_interact:
			index = 1;
			title.setText("好友");
			break;
		case R.id.re_recommend:
			index = 2;
			title.setText("资源");			
			break;
		case R.id.re_personal:
			index = 3;
			title.setText("我");
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getFragmentManager()
					.beginTransaction();

			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}

			trx.hide(fragments[currentTabIndex]).show(fragments[index])
					.commit();
		}
		tabButtons[currentTabIndex].setSelected(false);
		tabText[currentTabIndex].setTextColor(Color.BLACK);
		tabButtons[index].setSelected(true);// 把当前tab设为选中状态
		tabText[index].setTextColor(Color.GREEN);
		currentTabIndex = index;
	}
}
