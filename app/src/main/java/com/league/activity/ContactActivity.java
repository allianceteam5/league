package com.league.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chatuidemo.Constant;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.easemob.chatuidemo.adapter.ContactAdapter;
import com.easemob.chatuidemo.adapter.NewFriendsMsgAdapter;
import com.easemob.chatuidemo.domain.InviteMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.adapter.ContactsAdapter;
import com.league.bean.ContactBean;
import com.league.bean.RegisterUserBean;
import com.league.bean.SearchUserBean;
import com.league.utils.ContactsUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends BaseActivity {

    private ListView listview;
    private ContactsAdapter adapter;
    private ImageButton ibBack;
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        listview = (ListView)findViewById(R.id.listview);
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        ibBack.setVisibility(View.VISIBLE);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("手机联系人");
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<String> phones = ContactsUtils.getLocalContactsPhone(this);
        ApiUtil.friendGetInfoByPhone(this, phones, new BaseJsonHttpResponseHandler<ArrayList<RegisterUserBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<RegisterUserBean> response) {
                if (response == null || response.size() == 0){
                    ToastUtils.showShortToast(ContactActivity.this, "您的联系人还没有人注册");
                    return;
                }

                List<RegisterUserBean> list = new ArrayList<RegisterUserBean>();
                for (RegisterUserBean registerUserBean : response)
                    if (registerUserBean.getIsregisted() == 1)
                        list.add(registerUserBean);
                adapter = new ContactsAdapter(ContactActivity.this, list);
                listview.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<RegisterUserBean> errorResponse) {
                Toast.makeText(getApplicationContext(), "哎呀网络不好", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected ArrayList<RegisterUserBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<RegisterUserBean>>() {
                });
            }
        });
    }

}
