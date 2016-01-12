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
import com.league.bean.SucessBean;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class SelectBankcard extends BaseActivity implements View.OnClickListener {

    private List<BankCardInfo> list = new ArrayList<BankCardInfo>();
    private ImageView back2, titleright, right1, right2;
    private TextView title, edit;
    private ListView listView;
    private boolean[] select = new boolean[3];
    private boolean[] delete = new boolean[3];
    BankCardAdapter adapter;

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
        edit = (TextView) findViewById(R.id.near_commit);
        edit.setVisibility(View.VISIBLE);
        edit.setText("编辑");
        edit.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview);
        adapter = new BankCardAdapter(list, getApplication(), select, delete);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ApiUtil.deleteBanCard(SelectBankcard.this, list.get(position).getIdcard(), new BaseJsonHttpResponseHandler<SucessBean>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                        list.remove(position);
                        StoreUtils.setBankNum(list.size());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                        ToastUtils.showShortToast(SelectBankcard.this, "删除失败，请检查网络");
                    }

                    @Override
                    protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                        });
                    }
                });
            }
        });
    }

    private void initData() {
        showProgressDialog();
        ApiUtil.getUserBankcardList(this, new BaseJsonHttpResponseHandler<ArrayList<BankCardInfo>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<BankCardInfo> response) {
                closeProgressDialog();
                list.clear();
                list.addAll(response);
                StoreUtils.setBankNum(list.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<BankCardInfo> errorResponse) {
                closeProgressDialog();
                ToastUtils.showShortToast(SelectBankcard.this, "网络不给力");
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
        switch (v.getId()) {
            case R.id.addbankcard:
                if (list.size() > 2) {
                    ToastUtils.showShortToast(SelectBankcard.this, "最多只可以添加3张银行卡");
                } else {
                    Intent intent = new Intent(getApplication(), AddBankCard.class);
                    startActivity(intent);
                }

                break;
            case R.id.near_commit:
                for (int i = 0; i < delete.length; i++) {
                    delete[i] = true;
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for(int i = 0;i<delete.length;i++){
            delete[i] = false;
        }
        initData();
    }
}
