package com.league.bean;

/**
 * Created by liug on 15/11/22.
 */
public class PopularityBean {

    /**
     * phone : 1
     * nickname : 测试1
     * thumb : http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2
     * signature : 对方犯规
     * concerncount : 1
     * isconcerned : 0
     */

    private String phone;
    private String nickname;
    private String thumb;
    private String signature;
    private int concerncount;
    private int isconcerned;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setConcerncount(int concerncount) {
        this.concerncount = concerncount;
    }

    public void setIsconcerned(int isconcerned) {
        this.isconcerned = isconcerned;
    }

    public String getPhone() {
        return phone;
    }

    public String getNickname() {
        return nickname;
    }

    public String getThumb() {
        return thumb;
    }

    public String getSignature() {
        return signature;
    }

    public int getConcerncount() {
        return concerncount;
    }

    public int getIsconcerned() {
        return isconcerned;
    }
}
