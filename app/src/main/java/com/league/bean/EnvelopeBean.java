package com.league.bean;

/**
 * Created by liug on 15/11/28.
 */
public class EnvelopeBean {

    /**
     * type : 2
     * flag : 1
     * count : 7
     */

    private int type;
    private int flag;
    private int count;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public int getFlag() {
        return flag;
    }

    public int getCount() {
        return count;
    }
}
