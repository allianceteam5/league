package com.league.utils.api;

/**
 * Created by pfy on 2015/11/6.
 */
public class IClientUrl {
//    public static final String testServerUrl = "http://183.129.190.82:50001/v1/";
    public static final String testServerUrl = "http://120.27.196.128:80/v1/";
    public static final String ProfessionList = testServerUrl + "professions/list";
    public static final String ApplyJobCreated = testServerUrl + "applyjobs/create";
    public static final String ApplyJobSearch = testServerUrl + "applyjobs/search?page=";
    public static final String RecomendationList = testServerUrl + "kindsofrecommendation/list";

    public static final String Login = testServerUrl + "users/login";
    public static final String FriendList = testServerUrl + "friends/list";
    public static final String FriendGetInfoByArray = testServerUrl + "friends/getinfobyarray";
    public static final String FriendGetInfoByPhone = testServerUrl + "friends/getinfobyphonearray";
    public static final String FriendApprove = testServerUrl + "friends/approve";

    public static final String RecomendationSearch = testServerUrl + "recommendations/search?page=";
    public static final String RecomendationCreated = testServerUrl + "recommendations/create";
    public static final String RecomendationCommentCreated = testServerUrl + "recommendationcomments/create";

    public static final String HobbyList = testServerUrl + "hobbies/list";
    public static final String HobbySearch = testServerUrl + "daters/search?page=";
    public static final String HobbyCreated = testServerUrl + "daters/create";

    public static final String OtherList = testServerUrl + "tbothers/list?page=";
    public static final String OtherCreated = testServerUrl + "tbothers/send";

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
    public static final String LiaoBaTbmessagesReply = testServerUrl + "tbmessages/reply";
    public static final String LiaoBaTbmessageMoreReply = testServerUrl + "tbmessages/morereplys?page=";

    public static final String getGrabCommoditiesPass = testServerUrl + "grabcommodities/formeractivities?page=";
    public static final String getGrabCornsPass = testServerUrl + "grabcorns/formeractivities?page=";
    public static final String getGrabCoinRecords = testServerUrl + "grabcornrecords/list?page=";
    public static final String getGrabCommodyReocrds = testServerUrl + "grabcommodityrecords/list?page=";
    public static final String getGrabWinRecords = testServerUrl + "grab/win?page=";
    public static final String getGrabEnvelopes = testServerUrl + "envelopes/draw";
    public static final String getMoreRecordCorn = testServerUrl + "grabcorns/morerecords?page=";
    public static final String getMoreRecordCommodity = testServerUrl + "grabcommodities/morerecords?page=";
    public static final String applyForCorns = testServerUrl + "grabcorns/getcorns";
    public static final String applyForCommodity = testServerUrl + "grabcommodities/getcommodity";
    public static final String getShippingAddress = testServerUrl + "users/listaddresses";
    public static final String getTheLatest = testServerUrl + "grab/list?page=";
    public static final String addShippingAddress = testServerUrl + "users/createaddress";
    public static final String modifyShippingAddress = testServerUrl + "users/modifyaddress";
    public static final String deleteShipAddress = testServerUrl + "users/deleteaddress";
    public static final String getUserDetail = testServerUrl + "users/view";
    public static final String modifyUserDetail = testServerUrl + "users/modify";

    //圈子
    public static final String CircleMessageSend = testServerUrl + "messages/send";
    public static final String CircleMessageDelete = testServerUrl + "messages/delete";
    public static final String CircleMessageGet = testServerUrl + "messages/get?page=";
    public static final String CircleMessageGetMy = testServerUrl + "messages/getmy?page=";
    public static final String CircleMessageMoreReply = testServerUrl + "messages/morereplys?page=";
    public static final String CircleMessageConcelZan = testServerUrl + "messages/cancelzan";
    public static final String CircleMessageZan = testServerUrl + "messages/zan";
    public static final String CircleMessageReply = testServerUrl + "messages/reply";
    public static final String CircleMessageCollect = testServerUrl + "messages/collect";
    public static final String CircleMessageCollectCancel = testServerUrl + "messages/cancelcollect";
    public static final String CircleMessageCollectList = testServerUrl + "messages/getmycollect?page=";

    public static final String getSignUpUrl = testServerUrl + "users/getsignupurl";
    public static final String getUserMoneyBag = testServerUrl + "users/allmoney";
    public static final String sendCpText = testServerUrl + "users/sendcptext";
    public static final String sendrgtext = testServerUrl + "users/sendrgtext";
    public static final String signup = testServerUrl + "users/signup";
    public static final String usertocardslist = testServerUrl + "usertocards/list";
    public static final String changepwdbypwd = testServerUrl + "users/changepwdbypwd";
    public static final String checkpstext = testServerUrl + "users/checkcptext";
    public static final String changepwd = testServerUrl + "users/changepwd";
    public static final String getmorenumberCorn = testServerUrl + "grabcornrecords/viewallnumbers";
    public static final String getmorenumberCommodity = testServerUrl + "grabcommodityrecords/viewallnumbers";
    public static final String realauth = testServerUrl + "users/realauth";
    public static final String createBankcard = testServerUrl + "usertocards/create";
    public static final String setdefaultaddress = testServerUrl + "users/setdefaultaddress";
    public static final String envelopesList = testServerUrl + "envelopes/list";
    public static final String setpaypwd = testServerUrl + "users/setpaypwd";
    public static final String deleteBankCard = testServerUrl + "usertocards/delete";
    public static final String realinfo = testServerUrl + "users/realinfo";
    public static final String usersSearch = testServerUrl + "users/search";
    public static final String tradingRecord = testServerUrl + "users/traderecords?page=";
    public static final String recharge = testServerUrl + "users/moneyin";
    public static final String withdraw = testServerUrl + "users/moneyout";
    public static final String redeem = testServerUrl + "grabcorns/getback";
    public static final String grabcornslast50 =testServerUrl + "grabcorns/last50";
    public static final String grabcommoditieslast50 = testServerUrl +"grabcommodities/last50";
    public static final String rewardout = testServerUrl + "users/rewardout";

    public static final String SetInviteCode = testServerUrl + "users/setcode";
}
