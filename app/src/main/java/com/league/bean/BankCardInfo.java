package com.league.bean;

/**
 * Created by liug on 15/11/12.
 */
public class BankCardInfo {
    private String cardID;
    private String name;
    private String userID;
    private String userNumber;
    private String kaihudi;

    private String bankName;
    private String bankType;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getKaihudi() {
        return kaihudi;
    }

    public void setKaihudi(String kaihudi) {
        this.kaihudi = kaihudi;
    }
}
