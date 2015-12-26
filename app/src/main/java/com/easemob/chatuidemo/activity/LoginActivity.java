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
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chatuidemo.Constant;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.easemob.chatuidemo.db.UserDao;
import com.easemob.chatuidemo.domain.User;
import com.easemob.chatuidemo.utils.CommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.personactivity.FindCode;
import com.league.bean.UserInfoBean;
import com.league.utils.Constants;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

/**
 * 登陆页面
 * 
 */
public class LoginActivity extends BaseActivity {
	private static final String TAG = "LoginActivity";
	public static final int REQUEST_CODE_SETNICK = 1;
	private EditText usernameEditText;
	private EditText passwordEditText;

	private String currentUsername;
	private String currentPassword;

	private UserInfoBean userInfoBean;

	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		// 如果用户名密码都有，直接进入主页面
//		if (DemoHXSDKHelper.getInstance().isLogined()) {
//			autoLogin = true;
//			startActivity(new Intent(LoginActivity.this, com.league.activity.MainActivity.class));
//			return;
//		}

		if (StoreUtils.getLoginState()){
			startActivity(new Intent(LoginActivity.this, com.league.activity.MainActivity.class));
			ApiUtil.testPhone = StoreUtils.getPhone();
			return ;
		}

		setContentView(R.layout.activity_login);
		Constants.addIntoCollection(this);
		usernameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);

		// 如果用户名改变，清空密码
		usernameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				passwordEditText.setText(null);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		pd = new ProgressDialog(LoginActivity.this);
//		if (DemoApplication.getInstance().getUserName() != null) {
//			usernameEditText.setText(DemoApplication.getInstance().getUserName());
//		}
	}

	/**
	 * 登录
	 * 
	 * @param view
	 */
	public void login(View view) {
		if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
			return;
		}
		currentUsername = usernameEditText.getText().toString().trim();
		currentPassword = passwordEditText.getText().toString().trim();

		if (TextUtils.isEmpty(currentUsername)) {
			Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(currentPassword)) {
			Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
			return;
		}

		pd.setCanceledOnTouchOutside(false);
		pd.setMessage(getString(R.string.Is_landing));
		pd.show();

		ApiUtil.login(this, currentUsername, currentPassword, new BaseJsonHttpResponseHandler<UserInfoBean>() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserInfoBean response) {
				userInfoBean = response;
				if (userInfoBean.getFlag() == 0) {
					pd.dismiss();
					ToastUtils.showShortToast(LoginActivity.this, "密码错误！");
					return;
				}
				ApiUtil.testPhone = StoreUtils.getPhone();
				initHuanXin();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserInfoBean errorResponse) {
				Toast.makeText(LoginActivity.this, "网络不好", Toast.LENGTH_SHORT).show();
				pd.dismiss();
			}

			@Override
			protected UserInfoBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				if (new JSONObject(rawJsonData).optInt("flag") == 0)
					return new UserInfoBean();
				return new ObjectMapper().readValue(rawJsonData, new TypeReference<UserInfoBean>() {
				});
			}
		});

	}

	private void initHuanXin(){
		final long start = System.currentTimeMillis();

		// 调用sdk登陆方法登陆聊天服务器
		EMChatManager.getInstance().login(userInfoBean.getHxId(), currentPassword, new EMCallBack() {

			@Override
			public void onSuccess() {
				// 登陆成功，保存用户名密码
				DemoApplication.getInstance().setUserName(currentUsername);
				DemoApplication.getInstance().setPassword(currentPassword);
				Paper.book().write("userkey",currentUsername);
				try {
					// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
					// ** manually load all local groups and
					EMGroupManager.getInstance().loadAllGroups();
					EMChatManager.getInstance().loadAllConversations();
					// 处理好友和群组
					initializeContacts();
				} catch (Exception e) {
					e.printStackTrace();
					// 取好友或者群聊失败，不让进入主页面
					runOnUiThread(new Runnable() {
						public void run() {
							pd.dismiss();
							DemoHXSDKHelper.getInstance().logout(true, null);
							Toast.makeText(getApplicationContext(), R.string.login_failure_failed, Toast.LENGTH_LONG).show();
						}
					});
					return;
				}
				// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
				boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
						userInfoBean.getNickname());
				if (!updatenick) {
					Log.e("LoginActivity", "update current user nick fail");
				}
				if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
					pd.dismiss();
				}
				// 进入主页面
				Intent intent = new Intent(LoginActivity.this,
						com.league.activity.MainActivity.class);
				startActivity(intent);
				StoreUtils.setUserInfo(userInfoBean);
				StoreUtils.setLoginState(true);
				finish();
			}

			@Override
			public void onProgress(int progress, String status) {
			}

			@Override
			public void onError(final int code, final String message) {
				runOnUiThread(new Runnable() {
					public void run() {
						pd.dismiss();
						Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	private void initializeContacts() {
		Map<String, User> userlist = new HashMap<String, User>();
		// 添加user"申请与通知"
		User newFriends = new User();
		newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
		String strChat = getResources().getString(
				R.string.Application_and_notify);
		newFriends.setNick(strChat);

		userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
		// 添加"群聊"
		User groupUser = new User();
		String strGroup = getResources().getString(R.string.group_chat);
		groupUser.setUsername(Constant.GROUP_USERNAME);
		groupUser.setNick(strGroup);
		groupUser.setHeader("");
		userlist.put(Constant.GROUP_USERNAME, groupUser);
		
		// 添加"Robot"
		User robotUser = new User();
		String strRobot = getResources().getString(R.string.robot_chat);
		robotUser.setUsername(Constant.CHAT_ROBOT);
		robotUser.setNick(strRobot);
		robotUser.setHeader("");
//		userlist.put(Constant.CHAT_ROBOT, robotUser);

		//添加"朋友圈"
		User circle = new User();
		String strCircle = getResources().getString(R.string.friend_circle);
		circle.setUsername(Constant.FRIEDN_CIRCLE);
		circle.setNick(strCircle);
		circle.setHeader("");
		userlist.put(Constant.FRIEDN_CIRCLE,circle);

		// 存入内存
		((DemoHXSDKHelper)HXSDKHelper.getInstance()).setContactList(userlist);
		// 存入db
		UserDao dao = new UserDao(LoginActivity.this);
		List<User> users = new ArrayList<User>(userlist.values());
		dao.saveContactList(users);
	}
	
	/**
	 * 注册
	 * 
	 * @param view
	 */
	public void register(View view) {
		startActivityForResult(new Intent(this, RegisterActivity.class), 0);
	}
	public void findpwd(View view){
		startActivity(new Intent(this, FindCode.class));
	}

}
