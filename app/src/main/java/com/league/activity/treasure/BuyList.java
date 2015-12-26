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
import com.league.widget.MyRadioGroup;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import io.paperdb.Paper;

public class BuyList extends BaseActivity implements View.OnClickListener,IContants,MyRadioGroup.OnCheckedChangeListener{

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    private TextView tvBalance,tvCoin,tvGrabCoin;
    private int paytype=-1,type;
    private Button buy;
    private String id,number;
    private int buytype;
    private TextView totalText;
    private UserInfoBean userInfoBean=new UserInfoBean();
    private MyRadioGroup radioGroup;
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

        buy= (Button) findViewById(R.id.makepayment);
        buy.setOnClickListener(this);
        totalText= (TextView) findViewById(R.id.number);
        tvBalance= (TextView) findViewById(R.id.balance);
        tvCoin= (TextView) findViewById(R.id.coin);
        tvGrabCoin= (TextView) findViewById(R.id.grabcorn);
        radioGroup= (MyRadioGroup) findViewById(R.id.paytypegroup);
        if(type==0){
            findViewById(R.id.rl_coin).setVisibility(View.GONE);
            findViewById(R.id.view).setVisibility(View.GONE);
        }else if(type==1){
            findViewById(R.id.rl_grabcorn).setVisibility(View.GONE);
            findViewById(R.id.view).setVisibility(View.GONE);
        }
    }

    private void initData(){
        totalText.setText(number);
        userInfoBean= Paper.book().read(UserInfo);
        tvBalance.setText(userInfoBean.getMoney()+"");
        tvCoin.setText(userInfoBean.getCorns()+"");
        tvGrabCoin.setText(userInfoBean.getCornsforgrab()+"");
        radioGroup.setOnCheckedChangeListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
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


    @Override
    public void onCheckedChanged(MyRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_balancepay:
                paytype=0;
                break;
            case R.id.rb_cornpay:
                paytype=1;
                break;
            case R.id.rb_grabcornpay:
                paytype=2;
                break;
            case R.id.rb_alipay:
                paytype=3;
                break;
        }
    }
}
