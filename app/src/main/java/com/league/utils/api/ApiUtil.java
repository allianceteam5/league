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

    public static void applyjobSearch(Context context, boolean isMy, int professionid, String searchString, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        if (isMy)
            params.add("phone", testPhone);
        if (!TextUtils.isEmpty(searchString)) {
            params.add("title", searchString);
            professionid = 0;
        }
        if (professionid > 0)
            params.add("professionid", String.valueOf(professionid));
        printHttp(IClientUrl.ApplyJobSearch, params);
        client.post(context, IClientUrl.ApplyJobSearch + currentPage, params, responseHandler);
    }

    public static void recommendationList(Context context, TextHttpResponseHandler responseHandler) {
        client.post(context, IClientUrl.RecomendationList, new RequestParams(), responseHandler);
    }

    public static void recommendationsSearch(Context context, boolean isMy, int kindid, String title, int recommendationId, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        if (isMy)
            params.add("phone", testPhone);
        if (!TextUtils.isEmpty(title)) {
            params.add("title", title);
            recommendationId = 0;
        }
        if (kindid > 0)
            params.add("kindid", String.valueOf(kindid));
        if (recommendationId > 0)
            params.add("recommendationid", String.valueOf(recommendationId));
        printHttp(IClientUrl.RecomendationSearch + currentPage, params);
        client.post(context, IClientUrl.RecomendationSearch + currentPage, params, responseHandler);
    }

    public static void recommendationCreated(Context context, String title, int kindid, String location, String phone, String reason, String pictures, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        params.add("kindid", String.valueOf(kindid));
        params.add("title", title);
        params.add("sellerphone", phone);
        params.add("reason", reason);
        params.add("pictures", pictures);
        params.add("location", location);
        printHttp(IClientUrl.RecomendationCreated, params);
        client.post(context, IClientUrl.RecomendationCreated, params, responseHandler);
    }

    public static void recommendationCommentCreated(Context context, int recommendationid, String content, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        params.add("recommendationid", String.valueOf(recommendationid));
        params.add("content", content);
        client.post(context, IClientUrl.RecomendationCommentCreated, params, responseHandler);
    }

    public static void hobbyList(Context context, TextHttpResponseHandler responseHandler) {
        client.post(context, IClientUrl.HobbyList, new RequestParams(), responseHandler);
    }

    public static void hobbyCreated(Context context, String picture, int sex, int age, int hobbyid, String content, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        params.add("picture", picture);
        params.add("sex", String.valueOf(sex));
        params.add("age", String.valueOf(age));
        params.add("hobbyid", String.valueOf(hobbyid));
        params.add("content", content);
        printHttp(IClientUrl.HobbyCreated, params);
        client.post(context, IClientUrl.HobbyCreated, params, responseHandler);
    }

    public static void hobbySearch(Context context, boolean isMy, int hobbyid, String searchString, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        if (isMy)
            params.add("phone", testPhone);
        if (!TextUtils.isEmpty(searchString)) {
            params.add("content", searchString);
            hobbyid = 0;
        }
        if (hobbyid > 0) {
            params.add("hobbyid", String.valueOf(hobbyid));
        }
        printHttp(IClientUrl.ApplyJobSearch, params);
        client.post(context, IClientUrl.HobbySearch + currentPage, params, responseHandler);
    }

    public static void grabcornsSearch(Context context, int type, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("type", String.valueOf(type));
        printHttp(IClientUrl.GrabcornsSearch, params);
        client.post(context, IClientUrl.GrabcornsSearch + currentPage, params, responseHandler);
    }

    public static void grabcornsGetthree(Context context, TextHttpResponseHandler responseHandler) {
        client.post(context, IClientUrl.GrabcornsGetthree, new RequestParams(), responseHandler);
    }

    public static void grabCommoditiesSearch(Context context, int type, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("type", String.valueOf(type));
        printHttp(IClientUrl.GrabCommoditiesSearch, params);
        client.post(context, IClientUrl.GrabCommoditiesSearch + currentPage, params, responseHandler);
    }

    public static void grabcornsGetDetail(Context context, String grabcoinid, String phone, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcornid", grabcoinid);
        params.put("phone", phone);
        client.post(context, IClientUrl.GrabcornsGetTenDetail, params, responseHandler);
    }

    public static void grabcommoditiesGetDetail(Context context, String grabcommodityid, String phone, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcommodityid", grabcommodityid);
        params.put("phone", phone);
        client.post(context, IClientUrl.GrabCommoditiesDetail, params, responseHandler);
    }

    public static void grabcoinBuy(Context context, String grabcornid, String phone, String count, String type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcornid", grabcornid);
        params.put("phone", phone);
        params.put("count", count);
        params.put("type", type);
        client.post(context, IClientUrl.GrabcoinBuy, params, responseHandler);
    }

    public static void grabcoinBuyAll(Context context, String grabcornid, String phone, String type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcornid", grabcornid);
        params.put("phone", phone);
        params.put("type", type);
        client.post(context, IClientUrl.getGrabcoinBuyAll, params, responseHandler);
    }


    public static void getQiniuToken(Context context, TextHttpResponseHandler responseHandler) {
        client.get(context, IClientUrl.GetQiniuToken, responseHandler);
    }

    public static void oneYuanBuy(Context context, String grabcommodityid, String phone, String count, String type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcommodityid", grabcommodityid);
        params.put("phone", phone);
        params.put("count", count);
        params.put("type", type);
        client.post(context, IClientUrl.OneyuanBuy, params, responseHandler);
    }

    public static void oneyuanBuyAll(Context context, String grabcommodityid, String phone, String type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcommodityid", grabcommodityid);
        params.put("phone", phone);
        params.put("type", type);
        client.post(context, IClientUrl.OneyuanBuyAll, params, responseHandler);
    }

    //聊吧获取最新发布接口
    public static void liaobagetlatest(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaobaGetLatest + currentPage, params, responseHandler);
    }

    //获取聊吧最热接口
    public static void liaobaGetHot(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaobaGetHot + currentPage, params, responseHandler);
    }

    //获取我的关注
    public static void liaobaGetConcern(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaobaGetConcern + currentPage, params, responseHandler);
    }

    //获取聊吧人气
    public static void liaobaGetPopularity(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaobaGetPopularity + currentPage, params, responseHandler);
    }

    //点赞
    public static void liaobaLike(Context context, String tbmessageid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("tbmessageid", tbmessageid);
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaobaLike, params, responseHandler);
    }

    //取消点赞
    public static void liaobaCancellike(Context context, String tbmessageid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("tbmessageid", tbmessageid);
        client.post(context, IClientUrl.LiaobaCancellike, params, responseHandler);
    }

    /**
     * @param context
     * @param concernphone
     * @param type            type=1 关注 type=0 取消关注
     * @param responseHandler
     */
    public static void liaobaConcern(Context context, String concernphone, int type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("myphone", testPhone);
        params.put("concernphone", concernphone);
        if (type == 1)
            client.post(context, IClientUrl.LiaoBaConcernAdd, params, responseHandler);
        if (type == 0) {
            client.post(context, IClientUrl.LiaoBaConcernDelete, params, responseHandler);
        }
    }

    public static void liaobaTbmessagesCreated(Context context, String title, String content, String pictures, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        params.add("title", title);
        params.add("content", content);
        params.add("pictures", pictures);
        printHttp(IClientUrl.LiaoBaTbmessagesSend, params);
        client.post(context, IClientUrl.LiaoBaTbmessagesSend, params, responseHandler);
    }

    public static void liaobaGetMyConcernList(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaoBaTbusersConcernsList + currentPage, params, responseHandler);
    }

    public static void liaobaTbmessagesMyLikesList(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaoBaTbmessagesMyLikesList + currentPage, params, responseHandler);
    }

    public static void liaobaTbmessagesMyList(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaoBaTbmessagesMyList + currentPage, params, responseHandler);
    }

    //获取往期揭晓 一元夺宝的
    public static void grabcommoditiesPassAnnounced(Context context, String kind, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("kind", kind);
        client.post(context, IClientUrl.getGrabCommoditiesPass + currentPage, params, responseHandler);
    }
    //获取往期揭晓 10夺金
    public static void grabcornsPassAnnounced(Context context, String kind, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("kind", kind);
        client.post(context, IClientUrl.getGrabCornsPass + currentPage, params, responseHandler);
    }
    //获取夺金记录
    public static void getGrabCoinRecords(Context context, String phone, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        client.post(context, IClientUrl.getGrabCoinRecords + currentPage, params, responseHandler);
    }

    //获取夺宝记录
    public static void getGrabCommodyRecords(Context context, String phone, int currentpage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        client.post(context, IClientUrl.getGrabCommodyReocrds + currentpage, params, responseHandler);
    }

    //获取中奖记录
    public static void getGrabWinRecords(Context context, String phone, int currentpage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        client.post(context, IClientUrl.getGrabWinRecords + currentpage, params, responseHandler);
    }

    //获取最新揭晓
    public static void getGrabLatestAnnounced(Context context, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("type", "1");
        client.post(context, IClientUrl.getGrabLatestAnnounced, params, responseHandler);
    }

    //抢红包接口 type=1 金币  =2 夺宝币
    public static void getGrabEnvelope(Context context, String phone, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        client.post(context, IClientUrl.getGrabEnvelopes, params, responseHandler);
    }
    //获取更多购买记录
    public static void getMoreRecordCorn(Context context,String grabcornid,int currentPage,TextHttpResponseHandler responseHandler){
        RequestParams params=new RequestParams();
        params.put("grabcornid",grabcornid);
        client.post(context,IClientUrl.getMoreRecordCorn+currentPage,params,responseHandler);
    }
    //获取更多购买记录一元夺宝的
    public static void getMoreRecordCommodity(Context context,String grabcommodityid,int currentPage,TextHttpResponseHandler responseHandler){
        RequestParams params=new RequestParams();
        params.put("grabcommodityid",grabcommodityid);
        client.post(context,IClientUrl.getMoreRecordCommodity+currentPage,params,responseHandler);
    }
    //申请提取
    public static void applyForCorns(Context context,String phone,String grabcornid,TextHttpResponseHandler responseHandler){
        RequestParams params=new RequestParams();
        params.put("phone",phone);
        params.put("grabcornid",grabcornid);
        client.post(context,IClientUrl.applyForCorns,params,responseHandler);
    }
    //获取收货地址
    public static void getShipAddress(Context context,String phone,TextHttpResponseHandler responseHandler){
        RequestParams params=new RequestParams();
        params.put("phone",phone);
        client.post(context,IClientUrl.getShippingAddress,params,responseHandler);
    }
    //获取即将揭晓的三个
    public static void getTheLatest(Context context,int currentPage,TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        client.post(context,IClientUrl.getTheLatest+currentPage,params,responseHandler);
    }
}
