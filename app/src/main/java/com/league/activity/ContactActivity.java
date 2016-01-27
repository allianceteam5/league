package com.league.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.easemob.chatuidemo.adapter.ContactAdapter;
import com.league.adapter.ContactsAdapter;
import com.league.bean.ContactBean;
import com.league.utils.ContactsUtils;
import com.mine.league.R;

import java.util.List;

public class ContactActivity extends BaseActivity {

    List<ContactBean> contactBeanList;
    private ListView listview;
    private ContactsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactBeanList = ContactsUtils.getLocalContacts(this);
        listview = (ListView)findViewById(R.id.listview);
        adapter = new ContactsAdapter(this, contactBeanList);
        listview.setAdapter(adapter);
    }

}
