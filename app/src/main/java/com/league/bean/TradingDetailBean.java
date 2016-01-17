package com.league.bean;

/**
 * Created by liug on 16/1/14.
 */
public class TradingDetailBean {

    /**
     * id : 11
     * userid : 4
     * count : 10
     * type : 2
     * description : 自己人联盟微信支付充值
     * cardid : 0
     * created_at : 1452999017
     * ishandled : 0
     * handled_at : 0
     */

    private String id;
    private String userid;
    private String count;
    private String type;
    private String description;
    private String cardid;
    private String created_at;
    private String ishandled;
    private String handled_at;

    public void setId(String id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setIshandled(String ishandled) {
        this.ishandled = ishandled;
    }

    public void setHandled_at(String handled_at) {
        this.handled_at = handled_at;
    }

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getCount() {
        return count;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCardid() {
        return cardid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getIshandled() {
        return ishandled;
    }

    public String getHandled_at() {
        return handled_at;
    }
}
