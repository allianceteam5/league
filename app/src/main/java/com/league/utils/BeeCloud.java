package com.league.utils;

import android.app.Activity;
import android.widget.Toast;

import cn.beecloud.BCPay;

/**
 * Created by pfy on 2016/1/13.
 */
public class BeeCloud {
    public static void init(Activity activity){
        cn.beecloud.BeeCloud.setAppIdAndSecret("d2a277e0-a80c-4ef1-9fe8-3930c0ab512f",
                "dd999bbf-b50c-4a8f-a5af-6cb3900ae6ea");
        // 如果用到微信支付，在用到微信支付的Activity的onCreate函数里调用以下函数.
        // 第二个参数需要换成你自己的微信AppID.
        String initInfo = BCPay.initWechatPay(activity, "wxf1aa465362b4c8f1");
        if (initInfo != null) {
            Toast.makeText(activity, "微信初始化失败：" + initInfo, Toast.LENGTH_LONG).show();
        }
    }
}
