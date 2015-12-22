package com.league.utils;

/**
 * Created by pfy on 2015/12/22.
 */

import com.tumblr.remember.Remember;

/**
 * Created by laoshi on 2015/12/22.
 */
public class StoreUtils {
    private static final String SkipGuidState = "skip_guid_state";
    private static final String LoginState = "login_state";

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
}