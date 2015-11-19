package com.league.utils.api;

/**
 * Created by pfy on 2015/11/6.
 */
public class IClientUrl {
    public static final String testServerUrl = "http://183.129.190.82:50001/v1/";
    public static final String ProfessionList = testServerUrl + "professions/list";
    public static final String ApplyJobCreated = testServerUrl + "applyjobs/create";
    public static final String ApplyJobUpdated = testServerUrl + "applyjobs/update";
    public static final String ApplyJobDeleted = testServerUrl + "applyjobs/delete";
    public static final String ApplyJobSearch = testServerUrl + "applyjobs/search?page=";

    public static final String RecomendationList = testServerUrl + "kindsofrecommendation/list";
    public static final String RecomendationSearch = testServerUrl + "recommendations/search?page=";
    public static final String RecomendationCreated = testServerUrl + "recommendations/create";

    public static final String HobbyList = testServerUrl + "hobbies/list";
    public static final String HobbySearch = testServerUrl + "daters/search?page=";
    public static final String HobbyCreated = testServerUrl + "daters/create";

    public static final String GrabcornsSearch = testServerUrl + "grabcorns/search?page=";
    public static final String GrabcornsGetthree = testServerUrl + "grabcorns/getthree";
    public static final String GrabcornsGetTenDetail = testServerUrl+ "grabcorns/view";

    public static final String GrabCommoditiesSearch = testServerUrl + "grabcommodities/search?page=";
    public static final String OneYuanGrabGetSix="grabcommodities/getthree";

    public static final String GetQiniuToken = testServerUrl + "users/token";

}
