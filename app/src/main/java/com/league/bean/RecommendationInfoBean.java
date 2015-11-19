package com.league.bean;

import java.util.ArrayList;

/**
 * Created by pfy on 2015/11/19.
 */
public class RecommendationInfoBean {

    /**
     * id : 1
     * userid : 3
     * title : 第一条特色推荐
     * kindid : 1
     * kind :
     * location : 348675432
     * sellerphone :
     * reason : 好吃好吃
     * pictures : wggerhghfh
     * created_at : 1446816282
     */

    private int id;
    private int userid;
    private String title;
    private int kindid;
    private String kind;
    private String location;
    private String sellerphone;
    private String reason;
    private String pictures;
    private long created_at;

    private ArrayList<CommentBean> comments;
    /**
     * longitude : 1.1
     * latitude : 1.1
     */

    private double longitude;
    private double latitude;

    public RecommendationInfoBean() {
    }

    public ArrayList<CommentBean> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentBean> comments) {
        this.comments = comments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setKindid(int kindid) {
        this.kindid = kindid;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSellerphone(String sellerphone) {
        this.sellerphone = sellerphone;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public String getTitle() {
        return title;
    }

    public int getKindid() {
        return kindid;
    }

    public String getKind() {
        return kind;
    }

    public String getLocation() {
        return location;
    }

    public String getSellerphone() {
        return sellerphone;
    }

    public String getReason() {
        return reason;
    }

    public String getPictures() {
        return pictures;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
