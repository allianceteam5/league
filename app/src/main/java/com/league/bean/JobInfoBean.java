package com.league.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author liugang
 * @date 2015年9月19日
 */
public class JobInfoBean {
    private String id;
    private String userid;
    private String title;
    private String thumb;
    private String nickname;
    private String phone;
    private int jobproperty;
    private int degree;
    private int hidephone;
    private int professionid;
    private String profession;
    @JsonProperty("work_at")
    private long worktime;
    @JsonProperty("created_at")
    private long createdtime;
    private String status;
    @JsonProperty("content")
    private String intro;
    private String herphone;
    private double longitude;
    private double latitude;
    private int distance;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getHerphone() {
        return herphone;
    }

    public void setHerphone(String herphone) {
        this.herphone = herphone;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public long getWorktime() {
        return worktime;
    }

    public void setWorktime(long worktime) {
        this.worktime = worktime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getJobproperty() {
        return jobproperty;
    }

    public void setJobproperty(int jobproperty) {
        this.jobproperty = jobproperty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getHidephone() {
        return hidephone;
    }

    public void setHidephone(int hidephone) {
        this.hidephone = hidephone;
    }

    public int getProfessionid() {
        return professionid;
    }

    public void setProfessionid(int professionid) {
        this.professionid = professionid;
    }

    public long getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(long createdtime) {
        this.createdtime = createdtime;
    }

    public String getJobPropertyName() {
        if (jobproperty == 0)
            return "全职";
        else
            return "兼职";
    }
}
