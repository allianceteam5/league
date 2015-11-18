package com.league.utils.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * Created by pfy on 2015/11/6.
 */
public class ApiUtil {

    private static String testPhone = "1";
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
        params.add("work_at", String.valueOf(workat));
        params.add("status", status);
        params.add("hidephone", String.valueOf(hidephone));
        params.add("content", content);
        params.add("professionid", String.valueOf(professionid));
        printHttp(IClientUrl.ApplyJobCreated, params);
        client.post(context, IClientUrl.ApplyJobCreated, params, responseHandler);
    }

    public static void applyjobSearchAll(Context context, TextHttpResponseHandler responseHandler) {
        client.post(context, IClientUrl.ApplyJobSearch, new RequestParams(), responseHandler);
    }

    public static void applyjobSearchByPhone(Context context, String phone, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", phone);
        client.post(context, IClientUrl.ApplyJobSearch, params, responseHandler);
    }

    public static void applyjobSearchByTitle(Context context, String title, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("title", title);
        client.post(context, IClientUrl.ApplyJobSearch, new RequestParams(), responseHandler);
    }

    public static void applyjobSearch(Context context, int professionid, String searchString, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(searchString)) {
            params.add("title", searchString);
            professionid = 0;
        }
        if (professionid == 0) {
            client.post(context, IClientUrl.ApplyJobSearch + currentPage, params, responseHandler);
        } else {
            params.add("professionid", String.valueOf(professionid));
            printHttp(IClientUrl.ApplyJobSearch, params);
            client.post(context, IClientUrl.ApplyJobSearch + currentPage, params, responseHandler);
        }
    }

    public static void recommendationList(Context context, TextHttpResponseHandler responseHandler) {
        client.post(context, IClientUrl.RecomendationList, new RequestParams(), responseHandler);
    }

    public static void recommendationCreated(Context context,String title,int kindid, String position,String phone,String reason,String pictures, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone",testPhone);
        params.add("kindid",String.valueOf(kindid));
        params.add("title",title);
        params.add("sellerphone",phone);
        params.add("reason",reason);
        params.add("pictures",pictures);
//        params.add("");
        printHttp(IClientUrl.RecomendationCreated,params);
        client.post(context, IClientUrl.RecomendationCreated, params, responseHandler);
    }



    public static void hobbyList(Context context, TextHttpResponseHandler responseHandler) {
        client.post(context, IClientUrl.HobbyList, new RequestParams(), responseHandler);
    }

    public static void hobbyCreated(Context context,String picture,int sex,int age,int hobbyid,String content,TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.add("picture",picture);
        params.add("sex",String.valueOf(sex));
        params.add("age",String.valueOf(age));
        params.add("hobbyid",String.valueOf(hobbyid));
        params.add("content",content);
        client.post(context,IClientUrl.HobbyCreated,params,responseHandler);
    }

    public static void hobbySearch(Context context, int hobbyid, String searchString, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(searchString)) {
            params.add("title", searchString);
            hobbyid = 0;
        }
        if (hobbyid == 0) {
            client.post(context, IClientUrl.HobbySearch + currentPage, params, responseHandler);
        } else {
            params.add("hobbyid", String.valueOf(hobbyid));
            printHttp(IClientUrl.ApplyJobSearch, params);
            client.post(context, IClientUrl.HobbySearch + currentPage, params, responseHandler);
        }
    }

    public static void grabcornsSearch(Context context,int type,int currentPage, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.add("type",String.valueOf(type));
        printHttp(IClientUrl.GrabcornsSearch, params);
        client.post(context,IClientUrl.GrabcornsSearch + currentPage, params,responseHandler);
    }

    public static void grabcornsGetthree(Context context,TextHttpResponseHandler responseHandler){
        client.post(context,IClientUrl.GrabcornsGetthree ,new RequestParams(),responseHandler);
    }

    public static void grabCommoditiesSearch(Context context,int type,int currentPage, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.add("type",String.valueOf(type));
        printHttp(IClientUrl.GrabCommoditiesSearch, params);
        client.post(context,IClientUrl.GrabCommoditiesSearch + currentPage, params,responseHandler);
    }
    public static void grabcornsGetDetail(Context context,String grabcoinid,String phone,TextHttpResponseHandler responseHandler){
        RequestParams params=new RequestParams();
        params.put("grabcornid", grabcoinid);
        params.put("phone", phone);
        client.post(context,IClientUrl.GrabcornsGetTenDetail,params,responseHandler);
    }
    public static void oneYuanGrabGetSix(Context context,TextHttpResponseHandler responseHandler){
        client.post(context,IClientUrl.OneYuanGrabGetSix,new RequestParams(),responseHandler);
    }
    public static void getQiniuToken(Context context, TextHttpResponseHandler responseHandler){
        client.get(context,IClientUrl.GetQiniuToken,responseHandler);
    }
}
