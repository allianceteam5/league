/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.chatuidemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.adapter.SearchUsersAdapter;
import com.league.bean.CircleMessageBean;
import com.league.bean.SearchUserBean;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends BaseActivity{
	private EditText editText;
	private TextView mTextView;
	private Button searchBtn;
	private InputMethodManager inputMethodManager;
	private ProgressDialog progressDialog;
	private ListView listview;
	private List<SearchUserBean> list;
	private SearchUsersAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		mTextView = (TextView) findViewById(R.id.add_list_friends);

		editText = (EditText) findViewById(R.id.edit_note);
		String strAdd = getResources().getString(R.string.add_friend);
		mTextView.setText(strAdd);
//		String strUserName = getResources().getString(R.string.user_name);
//		editText.setHint(strUserName);
		searchBtn = (Button) findViewById(R.id.search);
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		listview = (ListView) findViewById(R.id.listview);
		adapter = new SearchUsersAdapter(AddContactActivity.this, new ArrayList<SearchUserBean>());
		listview.setAdapter(adapter);
	}
	
	
	/**
	 * 查找contact
	 * @param v
	 */
	public void searchContact(View v) {
		final String name = editText.getText().toString();
		if (TextUtils.isEmpty(name)){
			ToastUtils.showShortToast(AddContactActivity.this, getString(R.string.input_string));
			return;
		}

		ApiUtil.searchUsers(this, name, new BaseJsonHttpResponseHandler<ArrayList<SearchUserBean>>() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<SearchUserBean> response) {
				adapter.setData(response);
				if (response == null || response.size() == 0){
					ToastUtils.showShortToast(AddContactActivity.this, "没有你要查找的用户");
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<SearchUserBean> errorResponse) {
				Toast.makeText(getApplicationContext(), "哎呀网络不好", Toast.LENGTH_SHORT).show();
			}

			@Override
			protected ArrayList<SearchUserBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<SearchUserBean>>() {
				});
			}
		});
			// TODO 从服务器获取此contact,如果不存在提示不存在此用户
			
			//服务器存在此用户，显示此用户和添加按钮
//			searchedUserLayout.setVisibility(View.VISIBLE);
//			nameText.setText(toAddUsername);
			
	}
	
//	/**
//	 *  添加contact
//	 * @param view
//	 */
//	public void addContact(View view){
//		if(DemoApplication.getInstance().getUserName().equals(nameText.getText().toString())){
//			String str = getString(R.string.not_add_myself);
//			startActivity(new Intent(this, AlertDialog.class).putExtra("msg", str));
//			return;
//		}
//
//		if(((DemoHXSDKHelper)HXSDKHelper.getInstance()).getContactList().containsKey(nameText.getText().toString())){
//		    //提示已在好友列表中，无需添加
//		    if(EMContactManager.getInstance().getBlackListUsernames().contains(nameText.getText().toString())){
//		        startActivity(new Intent(this, AlertDialog.class).putExtra("msg", "此用户已是你好友(被拉黑状态)，从黑名单列表中移出即可"));
//		        return;
//		    }
//			String strin = getString(R.string.This_user_is_already_your_friend);
//			startActivity(new Intent(this, AlertDialog.class).putExtra("msg", strin));
//			return;
//		}
//
//		progressDialog = new ProgressDialog(this);
//		String stri = getResources().getString(R.string.Is_sending_a_request);
//		progressDialog.setMessage(stri);
//		progressDialog.setCanceledOnTouchOutside(false);
//		progressDialog.show();
//
//		new Thread(new Runnable() {
//			public void run() {
//
//				try {
//					//demo写死了个reason，实际应该让用户手动填入
//					String s = getResources().getString(R.string.Add_a_friend);
//					EMContactManager.getInstance().addContact(toAddUsername, s);
//					runOnUiThread(new Runnable() {
//						public void run() {
//							progressDialog.dismiss();
//							String s1 = getResources().getString(R.string.send_successful);
//							Toast.makeText(getApplicationContext(), s1, 1).show();
//						}
//					});
//				} catch (final Exception e) {
//					runOnUiThread(new Runnable() {
//						public void run() {
//							progressDialog.dismiss();
//							String s2 = getResources().getString(R.string.Request_add_buddy_failure);
//							Toast.makeText(getApplicationContext(), s2 + e.getMessage(), 1).show();
//						}
//					});
//				}
//			}
//		}).start();
//	}
	
	public void back(View v) {
		finish();
	}
}
