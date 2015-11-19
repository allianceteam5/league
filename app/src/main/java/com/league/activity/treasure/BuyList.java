package com.league.activity.treasure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.BuySucessBean;
import com.league.utils.Constants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

public class BuyList extends Activity implements View.OnClickListener{

    private ImageView back1, back2, titleright, right1, right2;
    private TextView title;
    private ImageView balancepay,coinpay,alliancepay,bankcardpay;
    private ImageView balancepayed,coinpayed,alliancepayed,bankcardpayed;
    int type=-1;
    private Button buy;
    private String id,number;
    private int buytype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_list);
        id=getIntent().getStringExtra("id");
        number=getIntent().getStringExtra("number");
        buytype=getIntent().getIntExtra("buytype",-1);//0表示正常购买 1表示购买全部
        if(buytype==-1)
            return ;
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
        titleright = (ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
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
                type=0;
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
                type=1;
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
                type=2;
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
                type=4;
                break;
            case R.id.makepayment:
                if(type==-1){
                    ToastUtils.showShortToast(getApplication(),"请选择支付方式");

                }else{
                    if(buytype==0){
                        ApiUtil.grabcoinBuy(getApplication(), id, Constants.PHONENUM, number, type + "", new BaseJsonHttpResponseHandler<BuySucessBean>() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, BuySucessBean response) {
                                onBackPressed();
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, BuySucessBean errorResponse) {

                            }

                            @Override
                            protected BuySucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {

                                return null;
                            }
                        });
                    }else if(buytype==1){
                        ApiUtil.grabcoinBuyAll(getApplication(), id, Constants.PHONENUM, type + "", new BaseJsonHttpResponseHandler<BuySucessBean>() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, BuySucessBean response) {
                                onBackPressed();
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, BuySucessBean errorResponse) {

                            }

                            @Override
                            protected BuySucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                return null;
                            }
                        });
                    }
                }
                break;
        }

    }
}
