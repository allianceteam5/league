package com.league.bean;

/**
 * Created by liug on 15/11/20.
 */
public class SucessBean {

    /**
     * flag : 1
     * msg : http://res.126.net/p/dbqb/one/98/348/eb8186618b8dd8f383ed542152eee6b4.png
     */

    private String flag;
    private String msg;
    private String huanxinid;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHuanxinid() {
        return huanxinid;
    }

    public void setHuanxinid(String huanxinid) {
        this.huanxinid = huanxinid;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFlag() {
        return flag;
    }

    public String getMsg() {
        return msg;
    }
}
