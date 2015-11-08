package com.league.utils.api;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * Created by pfy on 2015/11/6.
 */
public class ApiUtil {

    private static String testPhone = "2";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static void printHttp(String baseUrl, RequestParams params) {
        Log.d("http", "http请求: " + baseUrl + "?" + params);
    }

    public static void professionList(Context context, TextHttpResponseHandler responseHandler) {
        client.post(context, IClientUrl.ProfessionList, new RequestParams(), responseHandler);
    }

    public static void applyJobCreated(Context context, String phone, int jobproperty, String title, int degree, long workat, String status, int hidephone, String content, int professionid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        params.add("jobproperty", String.valueOf(jobproperty));
        params.add("title", title);
        params.add("degree", String.valueOf(degree));
        params.add("workat", String.valueOf(workat));
        params.add("status", status);
        params.add("hidephone", String.valueOf(hidephone));
        params.add("content", content);
        params.add("professionid", String.valueOf(professionid));
        printHttp(IClientUrl.ApplyJobCreated, params);
        client.post(context, IClientUrl.ApplyJobCreated, params, responseHandler);
    }

    public static void applyjobSearch(){

    }
}
