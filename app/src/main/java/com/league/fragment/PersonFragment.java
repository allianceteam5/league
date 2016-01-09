package com.league.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.RuleActivity;
import com.league.activity.circle.CircleActivity;
import com.league.activity.personactivity.AlianceReward;
import com.league.activity.personactivity.MyCollection;
import com.league.activity.personactivity.MyMoneyBag;
import com.league.activity.personactivity.PersonInformationSetup;
import com.league.activity.personactivity.PersonSetup;
import com.league.activity.personinfoactivity.InviteFriendActivity;
import com.league.bean.UserInfoBean;
import com.league.utils.ActivityUtils;
import com.league.utils.IContants;
import com.league.utils.StoreUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import io.paperdb.Paper;

/**
 * @author liugang
 * @date 2015年9月15日
 */
public class PersonFragment extends Fragment implements View.OnClickListener,IContants {
    protected Dialog loadingDialog;
    private View layout;
    private Activity ctx;
    private UserInfoBean userInfoBean;
//    @Bind(R.id.mythumb)
    ImageView mThumb;
//    @Bind(R.id.nickname)
    TextView mNickname;
//    @Bind(R.id.phonenumber)
    TextView mPhone;
//    @Bind(R.id.zhijiealliancenum)
//    @Bind(R.id.fivefloartotal)
    TextView mAllFive;
//    @Bind(R.id.award)
    TextView mAward;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (layout == null) {
            ctx = this.getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_person, null);

        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
//        ButterKnife.bind(ctx,layout);
        mThumb= (ImageView) layout.findViewById(R.id.mythumb);
        mNickname= (TextView) layout.findViewById(R.id.nickname);
        mPhone= (TextView) layout.findViewById(R.id.phonenumber);
        mAllFive= (TextView) layout.findViewById(R.id.fivefloartotal);
        mAward= (TextView) layout.findViewById(R.id.award);
        showProgressDialog();
        initData();
        getUrl();
        setOnClickListener();
        return layout;
    }

    private void setOnClickListener() {

        layout.findViewById(R.id.setup).setOnClickListener(this);
        layout.findViewById(R.id.myown).setOnClickListener(this);
        layout.findViewById(R.id.aliancereward).setOnClickListener(this);
        layout.findViewById(R.id.mycollection).setOnClickListener(this);
        layout.findViewById(R.id.mymoneybag).setOnClickListener(this);
        layout.findViewById(R.id.invitefriend).setOnClickListener(this);
        layout.findViewById(R.id.mycircle).setOnClickListener(this);
        layout.findViewById(R.id.rl_allancecount).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setup:
                Intent intent = new Intent(ctx, PersonSetup.class);
                startActivity(intent);
                break;
            case R.id.myown:
                Intent intent1=new Intent(ctx, PersonInformationSetup.class);
                startActivity(intent1);
                break;
            case R.id.aliancereward:
                Intent intent2 = new Intent(ctx, AlianceReward.class);
                startActivity(intent2);
                break;
            case R.id.mycollection:
                Intent intent3=new Intent(ctx, MyCollection.class);
                startActivity(intent3);
                break;
            case R.id.mycircle:
                ActivityUtils.start_Activity(getActivity(), CircleActivity.class);
                break;
            case R.id.mymoneybag:
                Intent intent4=new Intent(ctx, MyMoneyBag.class);
                startActivity(intent4);
                break;
            case R.id.invitefriend:
                startActivity(new Intent(ctx, InviteFriendActivity.class));
                break;
            case R.id.rl_allancecount:
                Intent intent5 = new Intent(ctx, RuleActivity.class);
                intent5.putExtra(RuleType,5);
                startActivity(intent5);
                break;
        }
    }
    private void initData(){
        ApiUtil.getUserDetail(ctx, StoreUtils.getPhone(), new BaseJsonHttpResponseHandler<UserInfoBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserInfoBean response) {
                userInfoBean = response;
                Paper.book().write(UserInfo, response);
                StoreUtils.setUserInfo(userInfoBean);
                if(!TextUtils.isEmpty(response.getThumb())){
                    Picasso.with(ctx).load(response.getThumb()).resize(120,120).centerCrop().placeholder(R.drawable.example).into(mThumb);
                }

                mNickname.setText(response.getNickname());
                mPhone.setText(response.getPhone());
                mAllFive.setText(response.getAllalliancecount() + "");
                mAward.setText(response.getAlliancerewards() + "");
                closeProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserInfoBean errorResponse) {
                closeProgressDialog();
            }

            @Override
            protected UserInfoBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<UserInfoBean>() {
                });
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
//        initData();
    }
    private Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;

    }
    /**
     * 显示等待对话框
     */
    public void showProgressDialog() {
        loadingDialog = createLoadingDialog(ctx);
        loadingDialog.show();
    }

    /**
     * 关闭等待对话框
     */
    public void closeProgressDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
    //这个有问题啊 ！！！！！
    private void getUrl(){
        ApiUtil.getSignupUrl(ctx, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                String url=responseString.substring(1,responseString.length()-1);
                Paper.book().write("signupurl",url);
            }
        });

    }
}
