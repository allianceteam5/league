package com.league.bean;

/**
 * Created by liug on 15/11/21.
 */
public class ReplysEntity {
    private String id;
    private String tbmessageid;
    private String content;
    private String fromid;
    private String toid;
    private String isread;
    private String created_at;
    private String fromnickname;
    private String fromphone;
    private String fromthumb;
    private Object tonickname;
    private Object tophone;
    private Object tothumb;

    public void setId(String id) {
        this.id = id;
    }

    public void setTbmessageid(String tbmessageid) {
        this.tbmessageid = tbmessageid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public void setToid(String toid) {
        this.toid = toid;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setFromnickname(String fromnickname) {
        this.fromnickname = fromnickname;
    }

    public void setFromphone(String fromphone) {
        this.fromphone = fromphone;
    }

    public void setFromthumb(String fromthumb) {
        this.fromthumb = fromthumb;
    }

    public void setTonickname(Object tonickname) {
        this.tonickname = tonickname;
    }

    public void setTophone(Object tophone) {
        this.tophone = tophone;
    }

    public void setTothumb(Object tothumb) {
        this.tothumb = tothumb;
    }

    public String getId() {
        return id;
    }

    public String getTbmessageid() {
        return tbmessageid;
    }

    public String getContent() {
        return content;
    }

    public String getFromid() {
        return fromid;
    }

    public String getToid() {
        return toid;
    }

    public String getIsread() {
        return isread;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getFromnickname() {
        return fromnickname;
    }

    public String getFromphone() {
        return fromphone;
    }

    public String getFromthumb() {
        return fromthumb;
    }

    public Object getTonickname() {
        return tonickname;
    }

    public Object getTophone() {
        return tophone;
    }

    public Object getTothumb() {
        return tothumb;
    }
}
