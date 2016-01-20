package com.league.utils.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.league.bean.LocationBean;
import com.league.utils.StoreUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

/**
 * Created by pfy on 2015/11/6.
 */
public class ApiUtil {

    public static String testPhone = "1";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static void printHttp(String baseUrl, RequestParams params) {
        Log.d("http", "http请求: " + baseUrl + "?" + params);
    }

    public static RequestParams getLocationCommonParams(){
        RequestParams params = new RequestParams();
        LocationBean locationBean = StoreUtils.getLocationBean();
        params.add("longitude",String.valueOf(locationBean.getLongitude()));
        params.add("latitude", String.valueOf(locationBean.getLatitude()));
        return params;
    }

    public static void login(Context context, String phone, String pwd, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", phone);
        params.add("pwd", pwd);
        printHttp(IClientUrl.Login, params);
        client.post(context, IClientUrl.Login, params, responseHandler);
    }

    public static void friendList(Context context, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        new SyncHttpClient().post(context, IClientUrl.FriendList, params, responseHandler);
    }

    public static void professionList(Context context, TextHttpResponseHandler responseHandler) {
        client.post(context, IClientUrl.ProfessionList, new RequestParams(), responseHandler);
    }

    public static void applyJobCreated(Context context, int jobproperty, String title, int degree, long workat, String status, int hidephone, String herphone, String content, int professionid, TextHttpResponseHandler responseHandler) {
        RequestParams params = getLocationCommonParams();
        params.add("phone", testPhone);
        params.add("jobproperty", String.valueOf(jobproperty));
        params.add("title", title);
        params.add("degree", String.valueOf(degree));
        params.add("work_at", String.valueOf(workat));
        params.add("status", status);
        params.add("hidephone", String.valueOf(hidephone));
        params.add("herphone", herphone);
        params.add("content", content);
        params.add("professionid", String.valueOf(professionid));
        printHttp(IClientUrl.ApplyJobCreated, params);
        client.post(context, IClientUrl.ApplyJobCreated, params, responseHandler);
    }

    public static void applyjobSearch(Context context, boolean isMy, int professionid, String searchString, int jobproperty, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = getLocationCommonParams();
        if (isMy)
            params.add("phone", testPhone);
        if (!TextUtils.isEmpty(searchString)) {
            params.add("title", searchString);
            professionid = 0;
        }
        if (professionid > 0)
            params.add("professionid", String.valueOf(professionid));
        if (jobproperty >= 0)
            params.add("jobproperty", String.valueOf(jobproperty));
        printHttp(IClientUrl.ApplyJobSearch, params);
        client.post(context, IClientUrl.ApplyJobSearch + currentPage, params, responseHandler);
    }

    public static void recommendationList(Context context, TextHttpResponseHandler responseHandler) {
        client.post(context, IClientUrl.RecomendationList, new RequestParams(), responseHandler);
    }

    public static void recommendationsSearch(Context context, boolean isMy, int kindid, String title, int recommendationId, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = getLocationCommonParams();
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
        RequestParams params = getLocationCommonParams();
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
        RequestParams params = getLocationCommonParams();
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
        RequestParams params = getLocationCommonParams();
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

    public static void othersList(Context context, boolean isMy, String search, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = getLocationCommonParams();
        if (isMy)
            params.add("phone", testPhone);
        if (!TextUtils.isEmpty(search)) {
            params.add("content", search);
        }
        printHttp(IClientUrl.OtherList, params);
        client.post(context, IClientUrl.OtherList + currentPage, params, responseHandler);
    }

    public static void otherCreated(Context context, String title, String content, String pictures, TextHttpResponseHandler responseHandler) {
        RequestParams params = getLocationCommonParams();
        params.add("phone", testPhone);
        params.add("title", title);
        params.add("content", content);
        params.add("pictures", pictures);
        printHttp(IClientUrl.OtherCreated, params);
        client.post(context, IClientUrl.OtherCreated, params, responseHandler);
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

    public static void grabcornsGetDetail(Context context, String grabcoinid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcornid", grabcoinid);
        params.put("phone", testPhone);
        client.post(context, IClientUrl.GrabcornsGetTenDetail, params, responseHandler);
    }

    public static void grabcommoditiesGetDetail(Context context, String grabcommodityid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcommodityid", grabcommodityid);
        params.put("phone", testPhone);
        client.post(context, IClientUrl.GrabCommoditiesDetail, params, responseHandler);
    }

    public static void grabcoinBuy(Context context, String grabcornid, String count, String type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcornid", grabcornid);
        params.put("phone", testPhone);
        params.put("count", count);
        params.put("type", type);
        client.post(context, IClientUrl.GrabcoinBuy, params, responseHandler);
    }

    public static void grabcoinBuyAll(Context context, String grabcornid, String type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcornid", grabcornid);
        params.put("phone", testPhone);
        params.put("type", type);
        client.post(context, IClientUrl.getGrabcoinBuyAll, params, responseHandler);
    }


    public static void getQiniuToken(Context context, TextHttpResponseHandler responseHandler) {
        client.get(context, IClientUrl.GetQiniuToken, responseHandler);
    }

    public static void oneYuanBuy(Context context, String grabcommodityid, String count, String type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcommodityid", grabcommodityid);
        params.put("phone", testPhone);
        params.put("count", count);
        params.put("type", type);
        client.post(context, IClientUrl.OneyuanBuy, params, responseHandler);
    }

    public static void oneyuanBuyAll(Context context, String grabcommodityid, String type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcommodityid", grabcommodityid);
        params.put("phone", testPhone);
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

    //聊吧新建消息
    public static void liaobaTbmessagesCreated(Context context, String title, String content, String pictures, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        params.add("title", title);
        params.add("content", content);
        params.add("pictures", pictures);
        printHttp(IClientUrl.LiaoBaTbmessagesSend, params);
        client.post(context, IClientUrl.LiaoBaTbmessagesSend, params, responseHandler);
    }

    //圈子状态评论
    public static void liaobaTbmessageComment(Context context, String tbmessageid, String content, String tphone, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("tbmessageid", tbmessageid);
        params.add("fphone", testPhone);
        params.add("content", content);
        params.add("tphone", tphone);
        printHttp(IClientUrl.LiaoBaTbmessagesReply, params);
        client.post(context, IClientUrl.LiaoBaTbmessagesReply, params, responseHandler);
    }

    //聊吧获取我的关注列表
    public static void liaobaGetMyConcernList(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaoBaTbusersConcernsList + currentPage, params, responseHandler);
    }

    //聊吧获取我的点赞列表
    public static void liaobaTbmessagesMyLikesList(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaoBaTbmessagesMyLikesList + currentPage, params, responseHandler);
    }

    //聊吧获取我的消息列表
    public static void liaobaTbmessagesMyList(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.LiaoBaTbmessagesMyList + currentPage, params, responseHandler);
    }

    //聊吧消息详情
    public static void liaobaTbmessagesView(Context context, String tbmessageid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("tbmessageid", tbmessageid);
        client.post(context, IClientUrl.LiaoBaTbmessagesView, params, responseHandler);
    }

    //聊吧消息更多回复
    public static void liaobaTbmessagesMoreReply(Context context, String tbmessageid, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("tbmessageid", tbmessageid);
        client.post(context, IClientUrl.LiaoBaTbmessageMoreReply + currentPage, params, responseHandler);
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
    public static void getGrabCoinRecords(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.getGrabCoinRecords + currentPage, params, responseHandler);
    }

    //获取夺宝记录
    public static void getGrabCommodyRecords(Context context, int currentpage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.getGrabCommodyReocrds + currentpage, params, responseHandler);
    }

    //获取中奖记录
    public static void getGrabWinRecords(Context context, int currentpage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.getGrabWinRecords + currentpage, params, responseHandler);
    }


    //抢红包接口 type=1 金币  =2 夺宝币
    public static void getGrabEnvelope(Context context, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.getGrabEnvelopes, params, responseHandler);
    }

    //获取更多购买记录
    public static void getMoreRecordCorn(Context context, String grabcornid, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcornid", grabcornid);
        client.post(context, IClientUrl.getMoreRecordCorn + currentPage, params, responseHandler);
    }

    //获取更多购买记录一元夺宝的
    public static void getMoreRecordCommodity(Context context, String grabcommodityid, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcommodityid", grabcommodityid);
        client.post(context, IClientUrl.getMoreRecordCommodity + currentPage, params, responseHandler);
    }

    //夺金申请提取
    public static void applyForCorns(Context context, String grabcornid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("grabcornid", grabcornid);
        client.post(context, IClientUrl.applyForCorns, params, responseHandler);
    }
    //夺宝申请提取
    public static void applyForCommodity(Context context, String grabcommodityid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("grabcommodityid", grabcommodityid);
        printHttp(IClientUrl.applyForCommodity, params);
        client.post(context, IClientUrl.applyForCommodity, params, responseHandler);
    }

    //获取收货地址
    public static void getShipAddress(Context context, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.getShippingAddress, params, responseHandler);
    }

    //获取即将揭晓的三个
    public static void getTheLatest(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        client.post(context, IClientUrl.getTheLatest + currentPage, params, responseHandler);
    }

    //增加收货地址
    public static void addShipAddress(Context context, String aphone, String name, String postcode, String address, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("aphone", aphone);
        params.put("name", name);
        params.put("isdefault", 0);
        params.put("postcode", postcode);
        params.put("address", address);
        client.post(context, IClientUrl.addShippingAddress, params, responseHandler);
    }

    //修改收货地址
    public static void modifyShipAddress(Context context, String addressid, String aphone, String name, String postcode, String address, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("addressid", addressid);
        params.put("aphone", aphone);
        params.put("name", name);
        params.put("postcode", postcode);
        params.put("address", address);
        client.post(context, IClientUrl.modifyShippingAddress, params, responseHandler);
    }

    //删除收货地址
    public static void deleteShipAddress(Context context, String addressID, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("addressid", addressID);
        client.post(context, IClientUrl.deleteShipAddress, params, responseHandler);
    }

    //设置默认收货地址
    public static void setDefaultAddress(Context context, String addressid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("addressid", addressid);
        client.post(context, IClientUrl.setdefaultaddress, params, responseHandler);
    }

    //获取用户详情
    public static void getUserDetail(Context context, String phone, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        printHttp(IClientUrl.getUserDetail, params);
        client.post(context, IClientUrl.getUserDetail, params, responseHandler);
    }

    //修改用户个性签名
    public static void modifyUserDetailSignature(Context context, String sign, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("signature", sign);
        client.post(context, IClientUrl.modifyUserDetail, params, responseHandler);
    }

    //修改用户地区
    public static void modifyUserDetailArea(Context context, String area, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("area", area);
        client.post(context, IClientUrl.modifyUserDetail, params, responseHandler);
    }

    //修改用户性别
    public static void modifyUserDetaiSex(Context context, int gender, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("gender", gender);
        client.post(context, IClientUrl.modifyUserDetail, params, responseHandler);
    }

    //修改用户昵称
    public static void modifyUserDetailNickname(Context context, String nick, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("nickname", nick);
        client.post(context, IClientUrl.modifyUserDetail, params, responseHandler);
    }

    //修改用户头像
    public static void modifyUserThumb(Context context, String thumbUrl, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("thumb", thumbUrl);
        client.post(context, IClientUrl.modifyUserDetail, params, responseHandler);
    }

    public static void modifyUserRegister(Context context, String nickname, int gender, String area, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("area", area);
        params.put("gender", gender);
        params.put("nickname", nickname);
        client.post(context, IClientUrl.modifyUserDetail, params, responseHandler);
    }

    //修改用户背景
    public static void modifyUserBackground(Context context, String backgroundUrl, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("background", backgroundUrl);
        client.post(context, IClientUrl.modifyUserDetail, params, responseHandler);
    }

    //圈子创建内容
    public static void circleMessagesCreated(Context context, String content, String pictures, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        params.add("content", content);
        params.add("pictures", pictures);
        printHttp(IClientUrl.CircleMessageSend, params);
        client.post(context, IClientUrl.CircleMessageSend, params, responseHandler);
    }

    //圈子删除内容
    public static void circleMessageDelete(Context context, String messageid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        params.add("messageid", messageid);
        printHttp(IClientUrl.CircleMessageDelete, params);
        client.post(context, IClientUrl.CircleMessageDelete, params, responseHandler);
    }

    //获取好友动态
    public static void circleMessageGet(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        printHttp(IClientUrl.CircleMessageGet, params);
        client.post(context, IClientUrl.CircleMessageGet + currentPage, params, responseHandler);
    }

    //圈子获取更多评论
    public static void circleMessageMoreReply(Context context, String messageid, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("messageid", messageid);
        printHttp(IClientUrl.CircleMessageMoreReply + currentPage, params);
        client.post(context, IClientUrl.CircleMessageMoreReply + currentPage, params, responseHandler);
    }

    //圈子状态评论
    public static void circleMessageComment(Context context, String messageid, String content, String tphone, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("messageid", messageid);
        params.add("fphone", testPhone);
        params.add("content", content);
        if (!TextUtils.isEmpty(tphone))
            params.add("tphone", tphone);
        else
            params.add("tphone", "");
        printHttp(IClientUrl.CircleMessageReply, params);
        client.post(context, IClientUrl.CircleMessageReply, params, responseHandler);
    }

    //圈子消息点赞 type=0取消点赞 type=1 点赞
    public static void circleMessageZanOrCancel(Context context, String messageid, int type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("messageid", messageid);
        params.add("phone", testPhone);
        if (type == 1)
            client.post(context, IClientUrl.CircleMessageZan, params, responseHandler);
        if (type == 0)
            client.post(context, IClientUrl.CircleMessageConcelZan, params, responseHandler);
    }

    //圈子消息收藏 type=0取消收藏 type=1 收藏
    public static void circleMessageCollectOrCancel(Context context, String messageid, int type, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("messageid", messageid);
        params.add("phone", testPhone);
        if (type == 1)
            client.post(context, IClientUrl.CircleMessageCollect, params, responseHandler);
        if (type == 0)
            client.post(context, IClientUrl.CircleMessageCollectCancel, params, responseHandler);
    }

    //圈子获取我的收藏
    public static void circleMessageCollectList(Context context, int currentPage, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("phone", testPhone);
        client.post(context, IClientUrl.CircleMessageCollectList + currentPage, params, responseHandler);
    }

    //获取邀请好友注册的url
    public static void getSignupUrl(Context context, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.getSignUpUrl, params, responseHandler);
    }

    //获取用户各种钱
    public static void getUserAllmoney(Context context, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.getUserMoneyBag, params, responseHandler);
    }

    //发送验证码
    public static void sendCpText(Context context, String phone, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        client.post(context, IClientUrl.sendCpText, params, responseHandler);
    }

    //发送注册验证码
    public static void sendRGText(Context context, String phone, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        client.post(context, IClientUrl.sendrgtext, params, responseHandler);
    }

    //注册
    public static void signup(Context context, String phone, String code, String pwd, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("pwd", pwd);
        params.put("text", code);
        client.post(context, IClientUrl.signup, params, responseHandler);
    }

    //获取我的银行卡列表
    public static void getUserBankcardList(Context context, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.usertocardslist, params, responseHandler);
    }

    //添加银行卡
    public static void addBankCard(Context context, String bankid, String name, String id, String userphone, String area, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("name", name);
        params.put("cardnumber", bankid);
        params.put("idcard", id);
        params.put("lphone", userphone);
        params.put("location", area);
        client.post(context, IClientUrl.createBankcard, params, responseHandler);
    }

    //删除银行卡
    public static void deleteBanCard(Context context, String usertocardid, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("usertocardid", usertocardid);
        printHttp(IClientUrl.deleteBankCard, params);
        client.post(context, IClientUrl.deleteBankCard, params, responseHandler);
    }

    //修改用户名密码
    public static void changePwd(Context context, String pwd, String newpwd, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("pwd", pwd);
        params.put("newpwd", newpwd);
        client.post(context, IClientUrl.changepwdbypwd, params, responseHandler);
    }

    public static void checkPXText(Context context, String phone, String text, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("text", text);
        client.post(context, IClientUrl.checkpstext, params, responseHandler);
    }

    //修改密码
    public static void changepwd(Context context, String pwd, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("pwd", pwd);
        client.post(context, IClientUrl.changepwd, params, responseHandler);
    }

    //一元夺宝 自己的购买记录获取更多号码
    public static void getMoreNumCommodity(Context context, String id, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcommodityrecordid", id);
        client.post(context, IClientUrl.getmorenumberCommodity, params, responseHandler);
    }

    //夺金购买 自己的购买号码获取更多
    public static void getMoreNumCorn(Context context, String id, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("grabcornrecordid", id);
        client.post(context, IClientUrl.getmorenumberCorn, params, responseHandler);
    }

    //实名认证
    public static void realAuth(Context context, String realname, String idcard, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("realname", realname);
        params.put("idcard", idcard);
        client.post(context, IClientUrl.realauth, params, responseHandler);
    }

    //获取红包中奖列表
    public static void getEnvelopeWinnerList(Context context, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        client.post(context, IClientUrl.envelopesList, params, responseHandler);
    }

    //修改 支付密码
    public static void changePayPwd(Context context, String oldpaypwd, String newpaypwd, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("oldpaypwd", oldpaypwd);
        params.put("newpaypwd", newpaypwd);
        client.post(context, IClientUrl.setpaypwd, params, responseHandler);
    }

    //设置 支付密码
    public static void setPayPwd(Context context, String newpaypwd, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("newpaypwd", newpaypwd);
        printHttp(IClientUrl.setpaypwd, params);
        client.post(context, IClientUrl.setpaypwd, params, responseHandler);
    }
    //获取实名信息
    public static void getCertificationInfo(Context context,TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        printHttp(IClientUrl.realinfo, params);
        client.post(context,IClientUrl.realinfo,params,responseHandler);
    }

    //查找用户
    public static void searchUsers(Context context, String searchString, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("search", searchString);
        client.post(context, IClientUrl.usersSearch, params, responseHandler);
    }

    //根据环信id查找用户信息
    public static void friendGetInfoByArray(Context context, List<String> list, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("huanxinids",list);
        printHttp(IClientUrl.FriendGetInfoByArray, params);
        client.post(context, IClientUrl.FriendGetInfoByArray, params,responseHandler);
    }

    //同意好友申请
    public static void approveFriend(Context context, String friendphone, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("myphone", testPhone);
        params.put("friendphone", friendphone);
        new SyncHttpClient().post(context, IClientUrl.FriendApprove, params, responseHandler);
    }

    //获取交易记录
    public static void getTradingRecord(Context context,int currentPage,TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        client.post(context, IClientUrl.tradingRecord+currentPage, params, responseHandler);
    }
    //充值
    public static void rechargeMoney(Context context,String count,String type,TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("count",count);
        params.put("type",type);
        client.post(context,IClientUrl.recharge,params,responseHandler);
    }
    //提现
    public static void withdraw(Context context,String count, String cardid,TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("phone", testPhone);
        params.put("count",count);
        params.put("cardid",cardid);
        client.post(context,IClientUrl.withdraw,params,responseHandler);
    }
}

