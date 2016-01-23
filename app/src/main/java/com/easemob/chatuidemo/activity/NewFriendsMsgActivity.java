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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chatuidemo.Constant;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.SearchUserBean;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.easemob.chatuidemo.adapter.NewFriendsMsgAdapter;
import com.easemob.chatuidemo.db.InviteMessgeDao;
import com.easemob.chatuidemo.domain.InviteMessage;

import org.apache.http.Header;

/**
 * 申请与通知
 *
 */
public class NewFriendsMsgActivity extends BaseActivity {
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_friends_msg);

		listView = (ListView) findViewById(R.id.list);
		InviteMessgeDao dao = new InviteMessgeDao(this);
		final List<InviteMessage> msgs = dao.getMessagesList();
		//设置adapter
		final List<String> hxIdList = new ArrayList<>();
		if (msgs != null)
			for (int i = 0 ; i < msgs.size() ;i++)
				if (TextUtils.isEmpty(msgs.get(i).getGroupId()))
					hxIdList.add(msgs.get(i).getFrom());
		if (hxIdList.size() > 0)
			ApiUtil.friendGetInfoByArray(this,hxIdList , new BaseJsonHttpResponseHandler<ArrayList<SearchUserBean>>() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<SearchUserBean> response) {
					List<InviteMessage> newMsgs = new ArrayList<InviteMessage>();
					for (int i = 0; i< msgs.size();i++)
						for (int j = 0; j < response.size(); j++)
							if (msgs.get(i).getFrom().equals(response.get(j).getHuanxinid()))
								newMsgs.add(msgs.get(i));
					NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(NewFriendsMsgActivity.this, 1,newMsgs, response);
					listView.setAdapter(adapter);
					((DemoHXSDKHelper)HXSDKHelper.getInstance()).getContactList().get(Constant.NEW_FRIENDS_USERNAME).setUnreadMsgCount(0);
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
	}

	public void back(View view) {
		finish();
	}
	


}