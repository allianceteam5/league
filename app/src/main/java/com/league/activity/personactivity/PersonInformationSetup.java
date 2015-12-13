package com.league.activity.personactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.activity.personinfoactivity.MyAreaActivity;
import com.league.activity.personinfoactivity.MyGenderActivity;
import com.league.activity.personinfoactivity.NickNameActivity;
import com.league.activity.personinfoactivity.ShippingAddress;
import com.league.activity.personinfoactivity.SignatureActivity;
import com.league.bean.UserInfoBean;
import com.league.widget.CircleImageView;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class PersonInformationSetup extends Activity implements View.OnClickListener{

    private ImageView back2, titleright, right1, right2;
    private TextView title;
    private UserInfoBean userInfoBean;
    @Bind(R.id.thumbnail)
    CircleImageView mThumbnail;
    @Bind(R.id.nickname)
    TextView mNickName;
    @Bind(R.id.numberid)
    TextView mNumberID;
    @Bind(R.id.gender)
    TextView mGender;
    @Bind(R.id.area)
    TextView mArea;
    @Bind(R.id.signature)
    TextView mSignature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_information_setup);
        ButterKnife.bind(this);
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
        title.setText("个人资料");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
    }
    private void initData(){
        userInfoBean= Paper.book().read("UserInfoBean");
        Picasso.with(this).load(userInfoBean.getThumb()).into(mThumbnail);
        mNickName.setText(userInfoBean.getNickname());
        mNumberID.setText(userInfoBean.getPhone());
        mGender.setText(userInfoBean.getGender() == 0 ? "女" : "男");
        mArea.setText(userInfoBean.getArea());
        mSignature.setText(userInfoBean.getSignature());
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
            case R.id.mysignature:
                Intent signature=new Intent(PersonInformationSetup.this, SignatureActivity.class);
                startActivity(signature);
                break;
            case R.id.myarea:
                startActivity(new Intent(PersonInformationSetup.this, MyAreaActivity.class));
                break;
            case R.id.mygender:
                startActivity(new Intent(PersonInformationSetup.this, MyGenderActivity.class));
                break;
            case R.id.mynickname:
                startActivity(new Intent(PersonInformationSetup.this, NickNameActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
