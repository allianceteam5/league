package com.league.activity.personactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.BankCardAdapter;
import com.league.bean.BankCardInfo;
import com.league.utils.Constants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class SelectBankcard extends BaseActivity implements View.OnClickListener{

    private List<BankCardInfo> list=new ArrayList<BankCardInfo>();
    private ImageView back2, titleright, right1, right2;
    private TextView title;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bankcard);
        initView();
    }

    private void initView() {

        back2 = (ImageView) findViewById(R.id.near_back);

        back2.setVisibility(View.VISIBLE);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        titleright = (ImageView) findViewById(R.id.near_title_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("选择银行卡");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        listView= (ListView) findViewById(R.id.listview);
    }
    private void initData(){
        showProgressDialog();
        ApiUtil.getUserBankcardList(this, Constants.PHONENUM, new BaseJsonHttpResponseHandler<ArrayList<BankCardInfo>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<BankCardInfo> response) {
                closeProgressDialog();
                list.clear();
                list.addAll(response);
                listView.setAdapter(new BankCardAdapter(list, getApplication()));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        onBackPressed();
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<BankCardInfo> errorResponse) {
                closeProgressDialog();
                ToastUtils.showShortToast(SelectBankcard.this,"网络不给力");
            }

            @Override
            protected ArrayList<BankCardInfo> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<BankCardInfo>>() {
                });
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addbankcard:
                Intent intent=new Intent(getApplication(),AddBankCard.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
