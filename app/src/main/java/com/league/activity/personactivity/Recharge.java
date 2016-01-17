package com.league.activity.personactivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.PayMoneyAmountAdapter;
import com.league.bean.SucessBean;
import com.league.utils.BeeCloud;
import com.league.utils.BillUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.league.widget.MyRadioGroup;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.beecloud.BCPay;
import cn.beecloud.BCQuery;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCBillOrder;
import cn.beecloud.entity.BCPayResult;
import cn.beecloud.entity.BCQueryBillResult;
import cn.beecloud.entity.BCReqParams;

public class Recharge extends BaseActivity implements MyRadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    private ImageView back2, titleright, right1, right2;
    private TextView title, tvNum;
    private Button sure;
    private MyRadioGroup radioGroup;
    private List<Integer> listData = new ArrayList<Integer>();
    private GridView gridView;
    private PayMoneyAmountAdapter adapter;
    private EditText etInputNum;
    private String type = "";
    private boolean[] control = new boolean[6];

    private static final String TAG = "ReCharge";
    private Map<String, String> mapOptional = new HashMap<String, String>();
    private int price;

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            tvNum.setText(s);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
        initData();
    }

    private void initData() {
        int[] data = {10, 20, 50, 100, 150, 200};
        for (int m : data) {
            listData.add(m);
        }
        control[0] = true;
        adapter = new PayMoneyAmountAdapter(listData, this, control);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
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
        title.setText("充值");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        sure = (Button) findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("")){
                    ToastUtils.showShortToast(Recharge.this,"请选择支付方式");
                }else{
//                    showProgressDialog();
                    if (type.equals("2"))
                        wxpay();
                    if (type.equals("1"))
                        alipay();
                }

//                Intent intent = new Intent(getApplication(), PaymentOrder.class);
//                startActivity(intent);

            }
        });
        radioGroup = (MyRadioGroup) findViewById(R.id.paytypegroup);
        radioGroup.setOnCheckedChangeListener(this);
        gridView = (GridView) findViewById(R.id.gv_recharge);
        tvNum = (TextView) findViewById(R.id.tv_num);
        etInputNum = (EditText) findViewById(R.id.et_inputnum);
        etInputNum.addTextChangedListener(watcher);
        BeeCloud.init(this);
    }

    private void afterPaySuccess(){
        ApiUtil.rechargeMoney(Recharge.this, tvNum.getText().toString(), type, new BaseJsonHttpResponseHandler<SucessBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
//                            closeProgressDialog();
                if(response.getFlag().equals("1")){
                    ToastUtils.showShortToast(Recharge.this,"充值成功");
                    finish();
                }else{
                    ToastUtils.showShortToast(Recharge.this,"充值失败");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
//                            closeProgressDialog();
                ToastUtils.showShortToast(Recharge.this, "网络不好，请检查网络再试一次");
            }

            @Override
            protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                });
            }
        });
    }
    @Override
    public void onCheckedChanged(MyRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_wxpay:
                type = "2";
                break;
            case R.id.rb_alipay:
                type = "1";
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int i = 0; i < control.length; i++) {
            if (i == position) {
                control[i] = true;
            } else {
                control[i] = false;
            }
        }
        tvNum.setText(listData.get(position) + "");
        adapter.notifyDataSetChanged();
    }

    public void alipay() {

        BCPay.getInstance(Recharge.this).reqAliPaymentAsync(
                "自己人联盟支付宝支付",
                Integer.valueOf(tvNum.getText().toString()) * 100,
                BillUtils.genBillNum(),
                mapOptional,
                bcCallback);
    }

    public void wxpay() {

        if (BCPay.isWXAppInstalledAndSupported() &&
                BCPay.isWXPaySupported()) {

            BCPay.getInstance(Recharge.this).reqWXPaymentAsync(
                    "三个老师微信支付",               //订单标题
                    Integer.valueOf(tvNum.getText().toString()) * 100,                           //订单金额(分)
                    BillUtils.genBillNum(),  //订单流水号
                    mapOptional,            //扩展参数(可以null)
                    bcCallback);            //支付完成后回调入口

        } else {
            Toast.makeText(Recharge.this,
                    "您尚未安装微信或者安装的微信版本不支持", Toast.LENGTH_LONG).show();
        }
    }

    //定义回调
    BCCallback bcCallback = new BCCallback() {
        @Override
        public void done(final BCResult bcResult) {
            //此处根据业务需要处理支付结果
            final BCPayResult bcPayResult = (BCPayResult) bcResult;

            Recharge.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //对于JRE6的用户请参考demo中使用if else判断
                    switch (bcPayResult.getResult()) {
                        case BCPayResult.RESULT_SUCCESS:
                            afterPaySuccess();
                            break;
                        case BCPayResult.RESULT_CANCEL:
                            Toast.makeText(Recharge.this, "用户取消支付", Toast.LENGTH_LONG).show();
                            break;
                        case BCPayResult.RESULT_FAIL:
                            Toast.makeText(Recharge.this, "支付失败, 原因: " + bcPayResult.getErrMsg()
                                    + ", " + bcPayResult.getDetailInfo(), Toast.LENGTH_LONG).show();
                    }

                    if (bcPayResult.getId() != null) {
                        //你可以把这个id存到你的订单中，下次直接通过这个id查询订单
                        Log.w(TAG, "bill id retrieved : " + bcPayResult.getId());

                        //根据ID查询，详细请查看demo
                        getBillInfoByID(bcPayResult.getId());
                    }
                }
            });
        }
    };

    void getBillInfoByID(String id) {

        BCQuery.getInstance().queryBillByIDAsync(id,
                new BCCallback() {
                    @Override
                    public void done(BCResult result) {
                        BCQueryBillResult billResult = (BCQueryBillResult) result;

                        Log.d(TAG, "------ response info ------");
                        Log.d(TAG, "------getResultCode------" + billResult.getResultCode());
                        Log.d(TAG, "------getResultMsg------" + billResult.getResultMsg());
                        Log.d(TAG, "------getErrDetail------" + billResult.getErrDetail());

                        Log.d(TAG, "------- bill info ------");
                        BCBillOrder billOrder = billResult.getBill();
                        Log.d(TAG, "订单号:" + billOrder.getBillNum());
                        Log.d(TAG, "订单金额, 单位为分:" + billOrder.getTotalFee());
                        Log.d(TAG, "渠道类型:" + BCReqParams.BCChannelTypes.getTranslatedChannelName(billOrder.getChannel()));
                        Log.d(TAG, "子渠道类型:" + BCReqParams.BCChannelTypes.getTranslatedChannelName(billOrder.getSubChannel()));

                        Log.d(TAG, "订单是否成功:" + billOrder.getPayResult());

                        if (billOrder.getPayResult())
                            Log.d(TAG, "渠道返回的交易号，未支付成功时，是不含该参数的:" + billOrder.getTradeNum());
                        else
                            Log.d(TAG, "订单是否被撤销，该参数仅在线下产品（例如二维码和扫码支付）有效:"
                                    + billOrder.getRevertResult());

                        Log.d(TAG, "订单创建时间:" + new Date(billOrder.getCreatedTime()));
                        Log.d(TAG, "扩展参数:" + billOrder.getOptional());
                        Log.w(TAG, "订单是否已经退款成功(用于后期查询): " + billOrder.getRefundResult());
                        Log.w(TAG, "渠道返回的详细信息，按需处理: " + billOrder.getMessageDetail());

                    }
                });
    }
}
