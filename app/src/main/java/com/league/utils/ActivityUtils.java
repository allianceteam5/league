package com.league.utils;

import android.app.Activity;
import android.content.Intent;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by pfy on 2015/11/8.
 */
public class ActivityUtils {
    /**
     * 打开Activity
     *
     * @param activity
     * @param cls
     * @param name
     */
    public static void start_Activity(Activity activity, Class<?> cls,
                                      BasicNameValuePair... name) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (name != null)
            for (int i = 0; i < name.length; i++) {
                intent.putExtra(name[i].getName(), name[i].getValue());
            }
        activity.startActivity(intent);

    }
}
