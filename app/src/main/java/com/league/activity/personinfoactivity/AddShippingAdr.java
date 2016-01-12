package com.league.activity.personinfoactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.SucessBean;
import com.league.dialog.WheelCascade;
import com.league.utils.PersonInfoBaseActivity;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddShippingAdr extends PersonInfoBaseActivity implements View.OnClickListener {

    private Button mSave;
    private TextView province;

    @Bind(R.id.consignee)
    EditText mConsignee;
    @Bind(R.id.phonenum)
    EditText mPhoneNum;
    @Bind(R.id.postid)
    EditText mPostId;
    @Bind(R.id.addressdetail)
    EditText mDetail;
    @Bind(R.id.delete)
    Button mDelete;
    @Bind(R.id.setdefault)
    Button mSetDefault;
    private String withSpaceProvice;
    private String[] pro_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("收货地址");
        if (getIntent().getIntExtra("flag", 0) == 0) {

        } else {
            mDelete.setOnClickListener(this);
            mSetDefault.setOnClickListener(this);
            setData();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_shipping_adr;
    }

    @Override
    protected void initView() {
        mSave = (Button) findViewById(R.id.newadd);
        mSave.setVisibility(View.VISIBLE);
        mSave.setOnClickListener(this);
        province = (TextView) findViewById(R.id.province);
        province.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.province:
                WheelCascade dialog = new WheelCascade(AddShippingAdr.this, new WheelCascade.ProvinceListener() {

                    @Override
                    public void refreshProvince(String string, String postid) {
                        withSpaceProvice = new String(string);
                        province.setText(string.replace(" ", ""));
                        mPostId.setText(postid);
                    }
                });
                dialog.show();
                break;
            case R.id.newadd:
                showProgressDialog();
                if (getIntent().getIntExtra("flag", 0) == 0) {
                    ApiUtil.addShipAddress(getApplicationContext(), mPhoneNum.getText().toString(),
                            mConsignee.getText().toString(), mPostId.getText().toString(), withSpaceProvice + mDetail.getText().toString(), new BaseJsonHttpResponseHandler<SucessBean>() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                    closeProgressDialog();
                                    if (response.getFlag().equals("1")) {
                                        onBackPressed();
                                        finish();
                                    } else {
                                        ToastUtils.showShortToast(getApplicationContext(), "添加失败，再试试");
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                    closeProgressDialog();
                                    ToastUtils.showShortToast(getApplicationContext(), "网络连接失败，再试试");
                                }

                                @Override
                                protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                    });
                                }
                            });
                } else {
                    int index = 0;
                    for (int j = 0; j < 3; index++) {
                        if (withSpaceProvice.charAt(index) == ' ') {
                            j++;
                        }
                    }
                    index--;
                    ApiUtil.modifyShipAddress(getApplicationContext(), getIntent().getLongExtra("addressID", -1) + "",
                            mPhoneNum.getText().toString(),
                            mConsignee.getText().toString(), mPostId.getText().toString(), withSpaceProvice.substring(0, index) + " " + mDetail.getText().toString(),
                            new BaseJsonHttpResponseHandler<SucessBean>() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                    closeProgressDialog();
                                    if (response.getFlag().equals("1")) {
                                        onBackPressed();
                                        finish();
                                    } else {
                                        ToastUtils.showShortToast(getApplicationContext(), "添加失败，再试试");
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                    closeProgressDialog();
                                    ToastUtils.showShortToast(getApplicationContext(), "网络连接失败，再试试");
                                }

                                @Override
                                protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                    });
                                }
                            });
                }

                break;
            case R.id.delete:
                ApiUtil.deleteShipAddress(getApplicationContext(), getIntent().getLongExtra("addressID", 0) + "", new BaseJsonHttpResponseHandler<SucessBean>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                        if (response.getFlag().equals("1")) {
                            onBackPressed();
                            finish();
                        } else {
                            ToastUtils.showShortToast(getApplicationContext(), "删除失败，再试试");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {

                    }

                    @Override
                    protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                        });
                    }
                });
                break;
            case R.id.setdefault:
                ApiUtil.setDefaultAddress(getApplicationContext(), getIntent().getLongExtra("addressID", 0) + "", new BaseJsonHttpResponseHandler<SucessBean>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                        if (response.getFlag().equals("1")) {
                            ToastUtils.showShortToast(getApplicationContext(), "设置成功");
                        } else {
                            ToastUtils.showShortToast(getApplicationContext(), "设置失败");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                        ToastUtils.showShortToast(getApplicationContext(), "设置失败");
                    }

                    @Override
                    protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                        });
                    }
                });
        }
    }

    private void setData() {
        mConsignee.setText(getIntent().getStringExtra("name"));
        mPhoneNum.setText(getIntent().getStringExtra("aphone"));
        mPostId.setText(getIntent().getStringExtra("postcode"));
        withSpaceProvice = getIntent().getStringExtra("address");
        pro_city = withSpaceProvice.split(" ", 4);
        mDetail.setText(pro_city[3]);
        province.setText(pro_city[0] + pro_city[1] + pro_city[2]);
        mSave.setText("修改");
        mDelete.setVisibility(View.VISIBLE);
        mSetDefault.setVisibility(View.VISIBLE);
    }
}
