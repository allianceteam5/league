package com.league.utils;

/**
 * Created by pfy on 2015/12/22.
 */

import android.content.Context;
import android.text.TextUtils;

import com.league.bean.LocationBean;
import com.league.bean.UserInfoBean;
import com.tumblr.remember.Remember;

import java.text.DateFormat;
import java.util.Date;

import io.paperdb.Paper;

/**
 * Created by pfy on 2015/12/22.
 */
public class StoreUtils {
    public static final String StoreName = "allpeopleleague";
    private static final String SkipGuidState = "skip_guid_state";
    private static final String LoginState = "login_state";
    public static final String UserInfo = "user_info";
    private static final String Phone = "phone";
    private static final String HuanXinId = "huanxin_id";
    private static final String HuanXinPwd = "huanxin_pwd";
    private static final String Avatar = "avatar";
    private static final String Nickname = "nickname";
    private static final String RealMoney = "realmoney";
    private static final String BankNum = "banknum";
    private static final String LocationBean = "locationbean";
    private static final String WidthScreen = "width_screen";
    private static final String TodayGrabNum = "sharenum";
    private static final String date = "date";

    //初始化
    public static void init(Context context) {
        Paper.init(context);
        Remember.init(context, StoreName);
    }

    //销毁所有数据
    public static void destory() {
        Paper.book().destroy();
        Remember.clear();
        setSkipGuidState(true);
    }

    public static Boolean getSkipGuidState() {
        return Remember.getBoolean(SkipGuidState, false);
    }

    public static void setSkipGuidState(Boolean value) {
        Remember.putBoolean(SkipGuidState, value);
    }

    public static Boolean getLoginState() {
        return Remember.getBoolean(LoginState, false);
    }

    public static void setLoginState(Boolean value) {
        Remember.putBoolean(LoginState, value);
    }

    public static void setUserInfo(UserInfoBean value) {
        Paper.book().write(UserInfo, value);
        if (!TextUtils.isEmpty(value.getPhone()))
            setPhone(value.getPhone());
        if (!TextUtils.isEmpty(value.getThumb()))
            setAvatar(value.getThumb());
        if (!TextUtils.isEmpty(value.getNickname()))
            setNickname(value.getNickname());
    }

    public static UserInfoBean getUserInfo() {
        return Paper.book().exist(UserInfo) ? (UserInfoBean) Paper.book().read(UserInfo) : new UserInfoBean();
    }

    public static void setPhone(String value) {
        Remember.putString(Phone, value);
    }

    public static String getPhone() {
        return Remember.getString(Phone, "1");

    }

    public static void setHuanXinId(String value) {
        Remember.putString(HuanXinId, value);
    }

    public static void setHuanXinPwd(String pwd) {
        Remember.putString(HuanXinPwd, pwd);
    }

    public static String getHuanXinPwd() {
        return Remember.getString(HuanXinPwd, "");
    }

    public static String getHuanXinId() {
        return Remember.getString(HuanXinId, "");
    }

    public static void setAvatar(String value) {
        Remember.putString(Avatar, value);
    }

    public static String getAvatar() {
        return Remember.getString(Avatar, "");
    }

    public static void setNickname(String value) {
        Remember.putString(Nickname, value);
    }

    public static String getNickname() {
        return Remember.getString(Nickname, "");
    }

    public static void setRealMoney(Float value) {
        Remember.putFloat(RealMoney, value);
    }

    public static Float getRealMoney() {
        return Remember.getFloat(RealMoney, 0.0f);
    }

    public static void setBankNum(int value) {
        Remember.putInt(BankNum, value);
    }

    public static int getBankNum() {
        return Remember.getInt(BankNum, 0);
    }

    public static void setLocationBean(LocationBean value) {
        Paper.book().write(LocationBean, value);
    }

    public static LocationBean getLocationBean() {
        return Paper.book().exist(LocationBean) ? (LocationBean) Paper.book().read(LocationBean) : new LocationBean();
    }

    public static void setWidthScreen(int value) {
        Remember.putInt(WidthScreen, value);
    }

    public static int getWidthScreen() {
        return Remember.getInt(WidthScreen, 100);
    }

    public static void setLastDate(String temp) {
        Remember.putString(date, temp);
    }

    public static String getLastDate() {
        return Remember.getString(date, "");
    }

    public static int getGrabNum() {
        int result;
        DateFormat df = DateFormat.getDateInstance();
        String current = df.format(new Date());
        String last = getLastDate();
        if (current.equals(last)) {
            int tem = getTotalNum(current);
            result = tem;
            tem--;
            if (tem <= 0) {
                setTotalNum(current, 0);
            } else {
                setTotalNum(current, tem);
            }
        } else {
            int tem = getTotalNum(current);
            result = tem;
            tem--;
            setTotalNum(current, tem);
            setLastDate(current);
        }
        if (result > 0) {
            int grabnum = getTodayGrabNum(current);
            if (grabnum <= 0) {
                result = 0;
            }
            grabnum--;
            setTodayGrabNum(current, grabnum);

        }
        return result;
    }

    public static int getTotalNum(String key) {
        return Remember.getInt(key, 1);
    }

    public static void setTotalNum(String key, int num) {
        Remember.putInt(key, num);
    }

    public static void setGrabNum(int type) {
        DateFormat df = DateFormat.getDateInstance();
        String current = df.format(new Date());
        int tem = getTotalNum(current);
        switch (type) {
            case 0:
                boolean wxcircle = getsharetype(current+"wxcircle");
                if(wxcircle == false){
                    tem++;
                    setsharetype(current+"wxcircle",true);
                }
                break;
            case 1:
                boolean wx = getsharetype(current+"wx");
                if(wx == false){
                    tem++;
                    setsharetype(current+"wx",true);
                }
                break;
            case 2:
                boolean qqzone = getsharetype(current+"qqzone");
                if(qqzone == false){
                    tem++;
                    setsharetype(current+"qqzone",true);
                }
                break;
            case 3:
                boolean qq = getsharetype(current+"qq");
                if(qq == false){
                    tem++;
                    setsharetype(current+"qq",true);
                }
                break;
            case 4:
                boolean sina = getsharetype(current+"sina");
                if(sina == false){
                    tem++;
                    setsharetype(current+"sina",true);
                }
                break;
            case 5:
                boolean message = getsharetype(current+"message");
                if(message == false){
                    tem++;
                    setsharetype(current+"message",true);
                }
                break;
        }
        if (tem > 3) {
            setTotalNum(current, 3);
        } else {
            setTotalNum(current, tem);
        }
    }

    //    public static int getTotalShareNum(String key){
//        return Remember.getInt(key+"sharenum",0);
//    }
//    public static void setTotalShareNum(String key,int num){
//        Remember.putInt(key+"sharenum",num);
//    }
    public static int getTodayGrabNum(String key) {
        return Remember.getInt(key + TodayGrabNum, 3);
    }

    public static void setTodayGrabNum(String key, int num) {
        Remember.putInt(key + TodayGrabNum, num);
    }
    public static void setsharetype(String key,boolean value){
        Remember.putBoolean(key, value);
    }
    public static boolean getsharetype(String key){
        return Remember.getBoolean(key,false);
    }
}