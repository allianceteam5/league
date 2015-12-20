package com.league.activity.personinfoactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.SucessBean;
import com.league.bean.UserInfoBean;
import com.league.utils.Constants;
import com.league.utils.PersonInfoBaseActivity;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import io.paperdb.Paper;

public class MyGenderActivity extends PersonInfoBaseActivity implements View.OnClickListener{
    private Button mOption;
    private UserInfoBean userInfoBean;
    private ImageView mImgWoman,mImgMan;
    private View view1,view2;
    private int isFlag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("性别");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_gender;
    }

    @Override
    protected void initView() {
        mOption= (Button) findViewById(R.id.newadd);
        mOption.setVisibility(View.VISIBLE);
        mOption.setText("提交");
        mOption.setOnClickListener(this);
        mImgMan= (ImageView) findViewById(R.id.img_man);
        mImgWoman= (ImageView) findViewById(R.id.img_woman);
        view1=findViewById(R.id.woman);
        view2=findViewById(R.id.man);
        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        userInfoBean= Paper.book().read("UserInfoBean");
        if(userInfoBean!=null&&userInfoBean.getGender()==0){
            setGender(true);
        }else{
            setGender(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newadd:
                ApiUtil.modifyUserDetaiSex(getApplicationContext(), Constants.PHONENUM, isFlag, new BaseJsonHttpResponseHandler<SucessBean>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                        if(response.getFlag().equals("1")){
                            userInfoBean.setGender(isFlag);
                            Paper.book().write("UserInfoBean",userInfoBean);
                            onBackPressed();
                            finish();
                        }else{
                            ToastUtils.showShortToast(getApplicationContext(),"网络不好，再试试");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                        ToastUtils.showShortToast(getApplicationContext(), "网络不好，再试试");
                    }

                    @Override
                    protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                        });
                    }
                });
                break;
            case R.id.woman:
                setGender(true);
                break;
            case R.id.man:
                setGender(false);
                break;
        }
    }
    private void setGender(boolean flag){
        if(flag){
            isFlag=0;
            mImgWoman.setVisibility(View.VISIBLE);
            mImgMan.setVisibility(View.INVISIBLE);
        }else{
            isFlag=1;
            mImgWoman.setVisibility(View.INVISIBLE);
            mImgMan.setVisibility(View.VISIBLE);
        }
    }
}
