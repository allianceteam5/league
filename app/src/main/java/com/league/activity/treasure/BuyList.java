package com.league.activity.treasure;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.bean.SucessBean;
import com.league.bean.UserInfoBean;
import com.league.utils.Constants;
import com.league.utils.IContants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import io.paperdb.Paper;

public class BuyList extends BaseActivity implements View.OnClickListener,IContants{

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    private ImageView balancepay,coinpay,alliancepay,bankcardpay;
    private ImageView balancepayed,coinpayed,alliancepayed,bankcardpayed;
    private TextView tvBalance,tvCoin,tvAlliance;
    private int paytype=-1,type;
    private Button buy;
    private String id,number;
    private int buytype;
    private TextView totalText;
    private UserInfoBean userInfoBean=new UserInfoBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_list);
        id=getIntent().getStringExtra("id");
        number=getIntent().getStringExtra("number");
        buytype=getIntent().getIntExtra("buytype", -1);//0表示正常购买 1表示购买全部
        type=getIntent().getIntExtra("type",-1);//type=0 10夺金   =1一元夺宝
        if(buytype==-1||type==-1)
            return ;
        initView();
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
        title.setText("结算");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        balancepay= (ImageView) findViewById(R.id.select1);
        balancepayed= (ImageView) findViewById(R.id.selected1);
        coinpay= (ImageView) findViewById(R.id.select2);
        coinpayed= (ImageView) findViewById(R.id.selected2);
        alliancepay= (ImageView) findViewById(R.id.select3);
        alliancepayed= (ImageView) findViewById(R.id.selected3);
        bankcardpay= (ImageView) findViewById(R.id.select4);
        bankcardpayed= (ImageView) findViewById(R.id.selected4);
        balancepay.setOnClickListener(this);
        coinpay.setOnClickListener(this);
        alliancepay.setOnClickListener(this);
        bankcardpay.setOnClickListener(this);
        buy= (Button) findViewById(R.id.makepayment);
        buy.setOnClickListener(this);
        totalText= (TextView) findViewById(R.id.number);
        tvBalance= (TextView) findViewById(R.id.balance);
        tvCoin= (TextView) findViewById(R.id.coin);
        tvAlliance= (TextView) findViewById(R.id.alliance);
    }

    private void initData(){
        totalText.setText(number);
        userInfoBean= Paper.book().read(UserInfo);
        tvBalance.setText(userInfoBean.getMoney()+"");
        tvCoin.setText(userInfoBean.getCorns()+"");
        tvAlliance.setText(userInfoBean.getAlliancerewards()+"");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select1:
                balancepay.setVisibility(View.GONE);
                balancepayed.setVisibility(View.VISIBLE);
                coinpay.setVisibility(View.VISIBLE);
                coinpayed.setVisibility(View.GONE);
                alliancepay.setVisibility(View.VISIBLE);
                alliancepayed.setVisibility(View.GONE);
                bankcardpay.setVisibility(View.VISIBLE);
                bankcardpayed.setVisibility(View.GONE);
                paytype=0;
                break;
            case R.id.select2:
                balancepay.setVisibility(View.VISIBLE);
                balancepayed.setVisibility(View.GONE);
                coinpay.setVisibility(View.GONE);
                coinpayed.setVisibility(View.VISIBLE);
                alliancepay.setVisibility(View.VISIBLE);
                alliancepayed.setVisibility(View.GONE);
                bankcardpay.setVisibility(View.VISIBLE);
                bankcardpayed.setVisibility(View.GONE);
                paytype=1;
                break;
            case R.id.select3:
                balancepay.setVisibility(View.VISIBLE);
                balancepayed.setVisibility(View.GONE);
                coinpay.setVisibility(View.VISIBLE);
                coinpayed.setVisibility(View.GONE);
                alliancepay.setVisibility(View.GONE);
                alliancepayed.setVisibility(View.VISIBLE);
                bankcardpay.setVisibility(View.VISIBLE);
                bankcardpayed.setVisibility(View.GONE);
                paytype=2;
                break;
            case R.id.select4:
                balancepay.setVisibility(View.VISIBLE);
                balancepayed.setVisibility(View.GONE);
                coinpay.setVisibility(View.VISIBLE);
                coinpayed.setVisibility(View.GONE);
                alliancepay.setVisibility(View.VISIBLE);
                alliancepayed.setVisibility(View.GONE);
                bankcardpay.setVisibility(View.GONE);
                bankcardpayed.setVisibility(View.VISIBLE);
                paytype=4;
                break;
            case R.id.makepayment:
                if(paytype==-1){
                    ToastUtils.showShortToast(getApplication(),"请选择支付方式");

                }else{
                    if(buytype==0&&type==0){
                        ApiUtil.grabcoinBuy(getApplication(), id,number, paytype + "", new BaseJsonHttpResponseHandler<SucessBean>() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                if(response.getFlag().equals("1")){
                                    ToastUtils.showShortToast(getApplicationContext(),"支付成功");
                                    onBackPressed();
                                    finish();
                                }else{
                                    ToastUtils.showShortToast(getApplicationContext(),"支付失败");
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                ToastUtils.showShortToast(getApplicationContext(),"支付失败");
                            }

                            @Override
                            protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {

                                return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                });
                            }
                        });
                    }else if(buytype==1&&type==0){
                        ApiUtil.grabcoinBuyAll(getApplication(), id, paytype + "", new BaseJsonHttpResponseHandler<SucessBean>() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                if(response.getFlag().equals("1")){
                                    ToastUtils.showShortToast(getApplicationContext(),"支付成功");
                                    onBackPressed();
                                    finish();
                                }else{
                                    ToastUtils.showShortToast(getApplicationContext(),"支付失败");
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                ToastUtils.showShortToast(getApplicationContext(),"支付失败");
                            }

                            @Override
                            protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                });
                            }
                        });
                    }else if(buytype==0&&type==1){
                        ApiUtil.oneYuanBuy(getApplication(), id,number, paytype + "", new BaseJsonHttpResponseHandler<SucessBean>() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                if(response.getFlag().equals("1")){
                                    ToastUtils.showShortToast(getApplicationContext(),"支付成功");
                                    onBackPressed();
                                    finish();
                                }else{
                                    ToastUtils.showShortToast(getApplicationContext(),"支付失败");
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                ToastUtils.showShortToast(getApplicationContext(),"支付失败");
                            }

                            @Override
                            protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {

                                return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                });
                            }
                        });
                    }else if(buytype==1&&type==1){
                        ApiUtil.oneyuanBuyAll(getApplication(), id, paytype + "", new BaseJsonHttpResponseHandler<SucessBean>() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                if(response.getFlag().equals("1")){
                                    ToastUtils.showShortToast(getApplicationContext(),"支付成功");
                                    onBackPressed();
                                    finish();
                                }else{
                                    ToastUtils.showShortToast(getApplicationContext(),"支付失败");
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                ToastUtils.showShortToast(getApplicationContext(),"支付失败");
                            }

                            @Override
                            protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                });
                            }
                        });
                    }
                }
                break;
        }

    }
}
