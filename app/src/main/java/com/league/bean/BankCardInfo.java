package com.league.bean;

/**
 * Created by liug on 15/11/12.
 */
public class BankCardInfo {

    /**
     * id : 3
     * userid : 3
     * cardnumber : 12345678654678654333455
     * name : 周
     * idcard : 3333333333333
     * lphone : 12345678
     * location : 杭州
     */

    private int id;
    private int userid;
    private String cardnumber;
    private String name;
    private String idcard;
    private String lphone;
    private String location;
    private String bankname;
    private String bankcode;

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setLphone(String lphone) {
        this.lphone = lphone;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public String getName() {
        return name;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getLphone() {
        return lphone;
    }

    public String getLocation() {
        return location;
    }
}
