package com.league.activity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.adapter.JobInfoAdapter;
import com.league.bean.JobInfoBean;
import com.league.utils.Constants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
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

import org.apache.http.Header;
import org.json.JSONObject;

import io.paperdb.Paper;

public class MyJobHunting extends BaseActivity implements OnClickListener{

	private ImageView back,title_right,right,delete;
	private ListView list;
	private TextView title;
	private List<JobInfoBean> jobInfoList=new ArrayList<JobInfoBean>();
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
//		delete.setVisibility(View.VISIBLE);
		list=(ListView) findViewById(R.id.list_myhunting);
		
	}
	void initData(){
		ApiUtil.applyjobSearchByPhone(getApplicationContext(),"2",new BaseJsonHttpResponseHandler<ArrayList<JobInfoBean>>() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<JobInfoBean> response) {
				jobInfoList = response;
				closeProgressDialog();
				updateApplyJobView();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<JobInfoBean> errorResponse) {
				closeProgressDialog();
				ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
			}

			@Override
			protected ArrayList<JobInfoBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				JSONObject jsonObject = new JSONObject(rawJsonData);
				return new ObjectMapper().readValue(jsonObject.optJSONArray("items").toString(), new TypeReference<ArrayList<JobInfoBean>>() {
				});
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

	public void updateApplyJobView(){
		adapter = new JobInfoAdapter(getApplicationContext(), jobInfoList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), NearItemActivity.class);
				Paper.book().write(Constants.SingleJobInfoName,jobInfoList.get(position));
				startActivity(intent);
			}
		});
	}
}
