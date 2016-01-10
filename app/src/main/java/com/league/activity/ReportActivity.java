package com.league.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.chatuidemo.Constant;
import com.league.adapter.RadioSelectAdapter;
import com.league.utils.Constants;
import com.league.utils.ToastUtils;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back, title_right, right;
    private TextView title, save;
    private ListView listview;
    RadioSelectAdapter radioSelectAdapter;
    List<String> items = new ArrayList<String>();
    int selectedIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        title_right = (ImageView) findViewById(R.id.near_title_right);
        title_right.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("我要求职");
        right = (ImageView) findViewById(R.id.near_right);
        right.setVisibility(View.GONE);
        save = (TextView) findViewById(R.id.near_save);
        save.setVisibility(View.VISIBLE);
        save.setText("提交");
        save.setOnClickListener(this);
        listview = (ListView) findViewById(R.id.listview);

        items = Constants.REPORTITEMS;
        radioSelectAdapter = new RadioSelectAdapter(getApplicationContext(), items);
        radioSelectAdapter.setSelectedIndex(selectedIndex);
        listview.setAdapter(radioSelectAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.near_save:
                if (radioSelectAdapter.getSelectedIndex() < 0){
                    ToastUtils.showShortToast(ReportActivity.this,"请选择");
                    return;
                }
                finish();
                ToastUtils.showShortToast(ReportActivity.this,"您的举报已提交成功");
                break;
        }
    }
}
