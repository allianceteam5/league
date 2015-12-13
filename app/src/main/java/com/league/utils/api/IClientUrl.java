package com.league.utils.api;

/**
 * Created by pfy on 2015/11/6.
 */
public class IClientUrl {
    public static final String testServerUrl = "http://183.129.190.82:50001/v1/";
    public static final String ProfessionList = testServerUrl + "professions/list";
    public static final String ApplyJobCreated = testServerUrl + "applyjobs/create";
    public static final String ApplyJobSearch = testServerUrl + "applyjobs/search?page=";

    public static final String RecomendationList = testServerUrl + "kindsofrecommendation/list";
    public static final String RecomendationSearch = testServerUrl + "recommendations/search?page=";
    public static final String RecomendationCreated = testServerUrl + "recommendations/create";
    public static final String RecomendationCommentCreated = testServerUrl + "recommendationcomments/create";

    public static final String HobbyList = testServerUrl + "hobbies/list";
    public static final String HobbySearch = testServerUrl + "daters/search?page=";
    public static final String HobbyCreated = testServerUrl + "daters/create";

    public static final String GrabcornsSearch = testServerUrl + "grabcorns/search?page=";
    public static final String GrabcornsGetthree = testServerUrl + "grabcorns/getthree";
    public static final String GrabcornsGetTenDetail = testServerUrl + "grabcorns/view";

    public static final String GrabCommoditiesSearch = testServerUrl + "grabcommodities/search?page=";
    public static final String GrabCommoditiesDetail = testServerUrl + "grabcommodities/view";
    public static final String GrabcoinBuy = testServerUrl + "grabcorns/buy";
    public static final String getGrabcoinBuyAll = testServerUrl + "grabcorns/buyall";
    public static final String OneYuanGrabGetSix = "grabcommodities/getthree";

    public static final String GetQiniuToken = testServerUrl + "users/token";
    public static final String OneyuanBuy = testServerUrl + "grabcommodities/buy";
    public static final String OneyuanBuyAll = testServerUrl + "grabcorns/buyall";

    public static final String LiaobaGetLatest = testServerUrl + "tbmessages/new?page=";
    public static final String LiaobaGetHot = testServerUrl + "tbmessages/hot?page=";
    public static final String LiaobaLike = testServerUrl + "tbmessages/like";
    public static final String LiaobaCancellike = testServerUrl + "tbmessages/cancellike";
    public static final String LiaobaGetConcern = testServerUrl + "tbmessages/myconcerns?page=";
    public static final String LiaobaGetPopularity = testServerUrl + "tbusers/hot?page=";
    public static final String LiaoBaConcernAdd = testServerUrl + "concerns/add";
    public static final String LiaoBaConcernDelete = testServerUrl + "concerns/delete";
    public static final String LiaoBaTbmessagesSend = testServerUrl + "tbmessages/send";
    public static final String LiaoBaTbmessagesDelete = testServerUrl + "tbmessages/delete";
    public static final String LiaoBaTbusersConcernsList = testServerUrl + "tbusers/myconcerns?page=";
    public static final String LiaoBaTbmessagesMyLikesList = testServerUrl + "tbmessages/mylikes?page=";
    public static final String LiaoBaTbmessagesMyList = testServerUrl + "tbmessages/me?page=";
    public static final String LiaoBaTbmessagesView = testServerUrl + "tbmessages/view";


    public static final String getGrabCommoditiesPass = testServerUrl + "grabcommodities/formeractivities?page=";
    public static final String getGrabCornsPass = testServerUrl + "grabcorns/formeractivities?page=";
    public static final String getGrabCoinRecords = testServerUrl + "grabcornrecords/list?page=";
    public static final String getGrabCommodyReocrds = testServerUrl + "grabcommodityrecords/list?page=";
    public static final String getGrabWinRecords = testServerUrl + "grabcornrecords/win?page=";
    public static final String getGrabEnvelopes = testServerUrl + "envelopes/draw";
    public static final String getMoreRecordCorn = testServerUrl + "grabcorns/morerecords?page=";
    public static final String getMoreRecordCommodity = testServerUrl + "grabcommodities/morerecords?page=";
    public static final String applyForCorns = testServerUrl + "grabcorns/getcorns";
    public static final String getShippingAddress = testServerUrl + "users/listaddresses";
    public static final String getTheLatest = testServerUrl + "grab/list?page=";
    public static final String addShippingAddress = testServerUrl + "users/createaddress";
    public static final String modifyShippingAddress=testServerUrl+"users/modifyaddress";
    public static final String deleteShipAddress = testServerUrl + "users/deleteaddress";
    public static final String getUserDetail = testServerUrl + "users/view";
    public static final String modifyUserDetail = testServerUrl + "users/modify";
}
