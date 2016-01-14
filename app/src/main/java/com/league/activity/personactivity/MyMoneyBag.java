package com.league.activity.personactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.activity.RuleActivity;
import com.league.bean.MoneyBean;
import com.league.bean.UserRealInfoBean;
import com.league.utils.IContants;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyMoneyBag extends BaseActivity implements View.OnClickListener, IContants {

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    private MoneyBean moneyBean = new MoneyBean();

    @Bind(R.id.mymoney)
    TextView myMoney;
    @Bind(R.id.corns)
    TextView mCorns;
    @Bind(R.id.cornsforgrab)
    TextView mCornsForgrab;
    @Bind(R.id.cardcount)
    TextView mCardCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money_bag);
        ButterKnife.bind(this);
        initView();
//        initData();
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
        title.setText("钱包");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
    }

    private void initData() {
        showProgressDialog();
        ApiUtil.getUserAllmoney(this, new BaseJsonHttpResponseHandler<MoneyBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, MoneyBean response) {
                moneyBean = response;
                myMoney.setText(moneyBean.getMoney() + "");
                StoreUtils.setRealMoney(moneyBean.getMoney());
                mCorns.setText(moneyBean.getCorns() + "");
                mCornsForgrab.setText(moneyBean.getCornsforgrab() + "");
                mCardCount.setText(moneyBean.getCardcount());
                StoreUtils.setBankNum(Integer.valueOf(moneyBean.getCardcount()));
                closeProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, MoneyBean errorResponse) {
                ToastUtils.showShortToast(MyMoneyBag.this, "获取数据失败");
                closeProgressDialog();
            }

            @Override
            protected MoneyBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<MoneyBean>() {
                });
            }
        });
        ApiUtil.getCertificationInfo(this, new BaseJsonHttpResponseHandler<UserRealInfoBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserRealInfoBean response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserRealInfoBean errorResponse) {

            }

            @Override
            protected UserRealInfoBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<UserRealInfoBean>() {
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recharge:
                Intent intent1 = new Intent(getApplication(), Recharge.class);
                startActivity(intent1);
                break;
            case R.id.tixian:
                Intent intent2 = new Intent(getApplication(), WithDraw.class);
                startActivity(intent2);
                break;
            case R.id.bankcardnum:
                Intent intent3 = new Intent(getApplication(), SelectBankcard.class);
                startActivity(intent3);
                break;
//            case R.id.goldcoin:
//                Intent intent4 = new Intent(getApplication(),MyGoldCoin.class);
//                startActivity(intent4);
//                break;
            case R.id.paypassword:
                Intent intent5 = new Intent(getApplication(), PayPassword.class);
                startActivity(intent5);
                break;
            case R.id.rl_question:
                Intent intent6 = new Intent(getApplication(), RuleActivity.class);
                intent6.putExtra(RuleType, 6);
                startActivity(intent6);
                break;
            case R.id.ll_tradingrecord:
                startActivity(new Intent(MyMoneyBag.this,TradingRecordActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
