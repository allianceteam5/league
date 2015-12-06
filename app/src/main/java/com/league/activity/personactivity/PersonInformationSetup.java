package com.league.activity.personactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.activity.personinfoactivity.ShippingAddress;
import com.mine.league.R;

public class PersonInformationSetup extends Activity implements View.OnClickListener{

    private ImageView back2, titleright, right1, right2;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_information_setup);
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
        title.setText("个人资料");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changeportrail:

                break;
            case R.id.truenamemake:
                Intent intent=new Intent(PersonInformationSetup.this,Certification.class);
                startActivity(intent);
                break;
            case R.id.shippingaddress:
                Intent address=new Intent(PersonInformationSetup.this, ShippingAddress.class);
                startActivity(address);
                break;
        }
    }
}
