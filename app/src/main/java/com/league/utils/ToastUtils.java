package com.league.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by pfy on 2015/11/8.
 */
public class ToastUtils {
    public static void showLongToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_SHORT).show();
    }
}
