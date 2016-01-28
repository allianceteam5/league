package com.league.bean;

/**
 * Created by laoshi on 2016/1/28.
 */
public class RegisterUserBean {
    private String id;
    private String thumb;
    private String phone;
    private String nickname;
    private int isfriend;
    private int isregisted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public int getIsregisted() {
        return isregisted;
    }

    public void setIsregisted(int isregisted) {
        this.isregisted = isregisted;
    }
}
