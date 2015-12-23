package com.league.bean;

/**
 * Created by liug on 15/12/23.
 */
public class MoreNumberBean {

    /**
     * flag : 1
     * numbers : 10000001 10000002 10000003 10000004 10000005 10000006 10000007 10000008 10000009 10000010
     * msg : success
     */

    private int flag;
    private String numbers;
    private String msg;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getFlag() {
        return flag;
    }

    public String getNumbers() {
        return numbers;
    }

    public String getMsg() {
        return msg;
    }
}
