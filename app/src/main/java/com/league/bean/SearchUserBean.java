package com.league.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by pfy on 2016/1/16.
 */
public class SearchUserBean {

    /**
     * id : 29
     * huanxinid : 29
     * phone : 15088783890
     * nickname : 无想峰
     * concerncount : 0
     * thumb : http://7xoc8r.com2.z0.glb.qiniucdn.com/Fh59g_z9eguSZN_Qh5TZKFQf_gLX-style
     * friendcount : 2
     */

    @JsonIgnore
    private String id;
    private String huanxinid;
    private String phone;
    private String nickname;
    private int concerncount;
    private String thumb;
    private int friendcount;
    @JsonIgnore
    private int isfriend;

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHuanxinid(String huanxinid) {
        this.huanxinid = huanxinid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setConcerncount(int concerncount) {
        this.concerncount = concerncount;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setFriendcount(int friendcount) {
        this.friendcount = friendcount;
    }

    public String getId() {
        return id;
    }

    public String getHuanxinid() {
        return huanxinid;
    }

    public String getPhone() {
        return phone;
    }

    public String getNickname() {
        return nickname;
    }

    public int getConcerncount() {
        return concerncount;
    }

    public String getThumb() {
        return thumb;
    }

    public int getFriendcount() {
        return friendcount;
    }
}
