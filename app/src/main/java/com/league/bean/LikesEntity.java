package com.league.bean;

/**
 * Created by liug on 15/11/21.
 */
public class LikesEntity {
    private String phone;
    private String nickname;
    private String thumb;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhone() {
        return phone;
    }

    public String getNickname() {
        return nickname;
    }

    public String getThumb() {
        return thumb;
    }
}
