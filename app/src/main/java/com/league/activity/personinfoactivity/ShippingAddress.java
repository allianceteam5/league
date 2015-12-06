package com.league.activity.personinfoactivity;

import android.os.Bundle;

import com.league.utils.PersonInfoBaseActivity;
import com.mine.league.R;

public class ShippingAddress extends PersonInfoBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("收货地址");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shippingaddress;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
