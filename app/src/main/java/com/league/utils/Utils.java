package com.league.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final long MONTH = 2678400000l;//31*24*60*60*1000
    private static final long YEAR = 31536000000l;//365*24*60*60*1000

    public static String generateStringByTime(long addtime) {
        long tt = System.currentTimeMillis() - addtime * 1000;
        if (tt < 10 * 60 * 1000) {
            return "刚刚发布";
        } else if (tt < 60 * 60 * 1000) {
            return tt / 60 / 1000 + "分钟前";
        } else if (tt < 24 * 60 * 60 * 1000) {
            return tt / 60 / 60 / 1000 + "小时前";
        } else if (tt < MONTH) {
            return tt / 24 / 60 / 60 / 1000 + "天前";
        } else if (tt < YEAR) {
            return tt / 31 / 24 / 60 / 60 / 1000 + "个月前";
        } else {
            return tt / 365 / 24 / 60 / 60 / 1000 + "年前";
        }
    }

    /**
     * 判断是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivity == null) {
                Log.w("Utility", "couldn't get connectivity manager");
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].isAvailable()) {
                            Log.d("Utility", "network is available");
                            return true;
                        }
                    }
                }
            }
        }
        Log.d("Utility", "network is not available");
        return false;
    }

    private static float sDensity = 0;

    /**
     * DP转换为像素
     *
     * @param context
     * @param nDip
     * @return
     */
    public static int dipToPixel(Context context, int nDip) {
        if (sDensity == 0) {
            final WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return (int) (sDensity * nDip);
    }

    public static String TimeStamp2Date(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                timestamp * 1000));
    }

    public static String TimeStamp2SystemNotificationDate(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
                timestamp));
    }

    public static String TimeStamp2DateChinese(long timestamp) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date(
                timestamp * 1000));
    }

}
