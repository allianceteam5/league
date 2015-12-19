package com.league.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.personactivity.AlianceReward;
import com.league.activity.personactivity.MyCollection;
import com.league.activity.personactivity.MyMoneyBag;
import com.league.activity.personactivity.PersonInformationSetup;
import com.league.activity.personactivity.PersonSetup;
import com.league.bean.UserInfoBean;
import com.league.utils.Constants;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import io.paperdb.Paper;

/**
 * @author liugang
 * @date 2015年9月15日
 */
public class PersonFragment extends Fragment implements View.OnClickListener {
    protected Dialog loadingDialog;
    private View layout;
    private Activity ctx;
    private UserInfoBean userInfoBean;
//    @Bind(R.id.mythumb)
    CircleImageView mThumb;
//    @Bind(R.id.nickname)
    TextView mNickname;
//    @Bind(R.id.phonenumber)
    TextView mPhone;
//    @Bind(R.id.zhijiealliancenum)
    TextView mDirectAllianceCount;
//    @Bind(R.id.fivefloartotal)
    TextView mAllFive;
//    @Bind(R.id.award)
    TextView mAward;

    private static PersonFragment instance;
    public PersonFragment(){}
    public static synchronized PersonFragment getInstance(){
        if(instance==null){
            instance=new PersonFragment();
        }
        return instance;
    }
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
        mThumb= (CircleImageView) layout.findViewById(R.id.mythumb);
        mNickname= (TextView) layout.findViewById(R.id.nickname);
        mPhone= (TextView) layout.findViewById(R.id.phonenumber);
        mDirectAllianceCount= (TextView) layout.findViewById(R.id.zhijiealliancenum);
        mAllFive= (TextView) layout.findViewById(R.id.fivefloartotal);
        mAward= (TextView) layout.findViewById(R.id.award);
        showProgressDialog();
        initData();
        setOnClickListener();
        return layout;
    }

    private void setOnClickListener() {

        layout.findViewById(R.id.setup).setOnClickListener(this);
        layout.findViewById(R.id.myown).setOnClickListener(this);
        layout.findViewById(R.id.aliancereward).setOnClickListener(this);
        layout.findViewById(R.id.mycollection).setOnClickListener(this);
        layout.findViewById(R.id.mymoneybag).setOnClickListener(this);
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
            case R.id.mymoneybag:
                Intent intent4=new Intent(ctx, MyMoneyBag.class);
                startActivity(intent4);
                break;
            case R.id.invitefriend:
//                startActivity(new Intent(ctx, InviteFriendActivity.class));
                break;
        }
    }
    private void initData(){
        ApiUtil.getUserDetail(ctx, Constants.PHONENUM, new BaseJsonHttpResponseHandler<UserInfoBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserInfoBean response) {
                userInfoBean = response;
                Paper.book().write("UserInfoBean", response);
                Picasso.with(ctx).load(response.getThumb()).into(mThumb);
                mNickname.setText(response.getNickname());
                mPhone.setText(response.getPhone());
                mDirectAllianceCount.setText(response.getDirectalliancecount() + "");
                mAllFive.setText(response.getAllalliancecount() + "");
                mAward.setText(response.getAlliancerewards() + "");
                closeProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserInfoBean errorResponse) {

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
        initData();
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
}
