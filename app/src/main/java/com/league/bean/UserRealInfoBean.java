package com.league.bean;

/**
 * Created by liug on 16/1/12.
 */
public class UserRealInfoBean {

    /**
     * id : 6
     * userid : 3
     * realname : 三个后
     * idcard : 22222222222222222222222
     * picture : 地方犯规好几个
     * created_at : 1450604114
     */

    private int id;
    private int userid;
    private String realname;
    private String idcard;
    private String picture;
    private int created_at;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public String getRealname() {
        return realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getPicture() {
        return picture;
    }

    public int getCreated_at() {
        return created_at;
    }
}
