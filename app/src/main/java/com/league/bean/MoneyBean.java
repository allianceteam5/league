package com.league.bean;

/**
 * Created by liug on 15/12/20.
 */
public class MoneyBean {


    /**
     * cardcount : 0
     * money : 16604.0
     * corns : 103413
     * cornsforgrab : 1000011
     */

    private String cardcount;
    private float money;
    private int corns;
    private int cornsforgrab;

    public void setCardcount(String cardcount) {
        this.cardcount = cardcount;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void setCorns(int corns) {
        this.corns = corns;
    }

    public void setCornsforgrab(int cornsforgrab) {
        this.cornsforgrab = cornsforgrab;
    }

    public String getCardcount() {
        return cardcount;
    }

    public float getMoney() {
        return money;
    }

    public int getCorns() {
        return corns;
    }

    public int getCornsforgrab() {
        return cornsforgrab;
    }
}
