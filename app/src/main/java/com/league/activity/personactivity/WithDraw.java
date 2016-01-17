package com.league.activity.personactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class WithDraw extends BaseActivity implements View.OnClickListener {

    private ImageView back2, titleright, right1, right2;
    private TextView title;
    @Bind(R.id.withdrawnum)
    TextView withDrawNum;
    @Bind(R.id.listview)
    ListView listView;
    @Bind(R.id.btn_confirm)
    Button confirm;
    @Bind(R.id.et_withdrawnum)
    EditText etWithdrawNum;
    private List<BankCardInfo> list = new ArrayList<BankCardInfo>();
    BankCardAdapter adapter;
    private boolean[] select = new boolean[3];
    private String cardID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);
        ButterKnife.bind(this);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
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
        title.setText("提现");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);

        confirm.setOnClickListener(this);

        withDrawNum.setText(StoreUtils.getRealMoney() + "元");
        adapter = new BankCardAdapter(list, getApplication(), select);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        select[i] = true;
                        cardID = list.get(position).getId()+"";
                    } else {
                        select[i] = false;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addbankcard:
                if (StoreUtils.getBankNum() > 2) {
                    ToastUtils.showShortToast(WithDraw.this, "最多只可以添加3张银行卡");
                } else {
                    Intent intent = new Intent(getApplication(), AddBankCard.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_confirm:
                if(TextUtils.isEmpty(etWithdrawNum.getText().toString())){
                    ToastUtils.showShortToast(WithDraw.this, "请输入金额");
                }else {
                    float now = StoreUtils.getRealMoney();
                    float out = Float.valueOf(etWithdrawNum.getText().toString());
                    if(out>now){
                        ToastUtils.showShortToast(WithDraw.this, "没那么多钱让您提现啊");
                    }else{
                        if(TextUtils.isEmpty(cardID)){
                            ToastUtils.showShortToast(WithDraw.this, "请选择银行卡");
                        }else{
                            showProgressDialog();
                            ApiUtil.withdraw(WithDraw.this, etWithdrawNum.getText().toString(), cardID, new BaseJsonHttpResponseHandler<SucessBean>() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                    closeProgressDialog();
                                    if(response.getFlag().equals("0")){
                                        ToastUtils.showShortToast(WithDraw.this, "提现请求失败");
                                    }else{
                                        ToastUtils.showShortToast(WithDraw.this, "提现请求发送成功");
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                    closeProgressDialog();
                                    ToastUtils.showShortToast(WithDraw.this, "网络请求失败");
                                }

                                @Override
                                protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                    });
                                }
                            });
                        }
//
                    }
                }

                break;
        }
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
                ToastUtils.showShortToast(WithDraw.this, "网络不给力");
            }

            @Override
            protected ArrayList<BankCardInfo> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<BankCardInfo>>() {
                });
            }
        });

    }

}
