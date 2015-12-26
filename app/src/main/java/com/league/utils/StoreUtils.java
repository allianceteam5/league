package com.league.utils;

/**
 * Created by pfy on 2015/12/22.
 */

import android.content.Context;
import android.text.TextUtils;

import com.league.bean.UserInfoBean;
import com.tumblr.remember.Remember;

import io.paperdb.Paper;

/**
 * Created by pfy on 2015/12/22.
 */
public class StoreUtils {
    public static final String StoreName = "allpeopleleague";
    private static final String SkipGuidState = "skip_guid_state";
    private static final String LoginState = "login_state";
    private static final String UserInfo = "user_info";
    private static final String Phone = "phone";
    private static final String HuanXinId = "huanxin_id";
    private static final String HuanXinPwd = "huanxin_pwd";
    private static final String Avatar = "avatar";
    private static final String Nickname = "nickname";
    //初始化
    public static void init(Context context){
        Paper.init(context);
        Remember.init(context, StoreName);
    }
    //销毁所有数据
    public static void destory(){
        Paper.book().destroy();
        Remember.clear();
        setSkipGuidState(true);
    }

    public static Boolean getSkipGuidState(){
        return Remember.getBoolean(SkipGuidState, false);
    }

    public static void setSkipGuidState(Boolean value){
        Remember.putBoolean(SkipGuidState, value);
    }

    public static Boolean getLoginState(){
        return Remember.getBoolean(LoginState, false);
    }

    public static void setLoginState(Boolean value){
        Remember.putBoolean(LoginState, value);
    }

    public static void setUserInfo(UserInfoBean value){
        Paper.book().write(UserInfo, value);
        setPhone(value.getPhone());
        if (!TextUtils.isEmpty(value.getThumb()))
            setAvatar(value.getThumb());
        if (!TextUtils.isEmpty(value.getNickname()))
            setNickname(value.getNickname());
    }

    public static UserInfoBean getUserInfo(){
        return Paper.book().read(UserInfo);
    }

    public static void setPhone(String value){
        Remember.putString(Phone, value);
    }
    public static String getPhone(){
        return Remember.getString(Phone, "1");

    }

    public static void setHuanXinId(String value){
        Remember.putString(HuanXinId, value);
    }

    public static void setHuanXinPwd(String pwd){
        Remember.putString(HuanXinPwd, pwd);
    }

    public static String getHuanXinPwd(){
        return Remember.getString(HuanXinPwd, "");
    }
    public static String getHuanXinId(){
        return Remember.getString(HuanXinId, "");
    }

    public static void setAvatar(String value){
        Remember.putString(Avatar, value);
    }

    public static String getAvatar(){
        return Remember.getString(Avatar,"");
    }

    public static void setNickname(String value){
        Remember.putString(Nickname, value);
    }

    public static String getNickname(){
        return Remember.getString(Nickname, "");
    }
}