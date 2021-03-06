package com.league.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by liug on 15/12/12.
 */
public class UserInfoBean {

    /**
     * id : 3
     * phone : 1
     * pwd : 202cb962ac59075b964b07152d234b70
     * authKey :
     * fatherid : 1
     * directalliancecount : 0
     * allalliancecount : 0
     * corns : 103423
     * money : 22983
     * envelope : 10
     * cornsforgrab : 1000010
     * alliancerewards : 0
     * nickname : 测啊
     * thumb : http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2
     * gender : 0
     * area : 安徽省安庆市枞阳县
     * job : 顾客
     * hobby : 投稿
     * signature : 哈哈毛线啊
     * created_at : 0
     * updated_at : 0
     * channel :
     * platform : null
     * friendcount : 0
     * concerncount : 2
     * isdraw : 0
     * status : 0
     */
    private String paypwd;
    private String background;
    private String invitecode;
    private String phone;
    @JsonProperty("id")
    private String hxId;
    private String pwd;
    private String authKey;
    private String fatherid;
    private int directalliancecount;
    private int allalliancecount;
    private int corns;
    private int money;
    private int envelope;
    private int cornsforgrab;
    private int alliancerewards;
    private String nickname;
    private String thumb;
    private int gender;
    private String area;
    private String job;
    private String hobby;
    private String signature;
    private int created_at;
    private int updated_at;
    private String channel;
    private String platform;
    private int friendcount;
    private int concerncount;
    private int status;
    private int isdraw;
    private int flag;
    private String huanxinid;

    public String getPaypwd() {
        return paypwd;
    }

    public void setPaypwd(String paypwd) {
        this.paypwd = paypwd;
    }

    public String getHuanxinid() {
        return huanxinid;
    }

    public void setHuanxinid(String huanxinid) {
        this.huanxinid = huanxinid;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public void setFatherid(String fatherid) {
        this.fatherid = fatherid;
    }

    public void setDirectalliancecount(int directalliancecount) {
        this.directalliancecount = directalliancecount;
    }

    public void setAllalliancecount(int allalliancecount) {
        this.allalliancecount = allalliancecount;
    }

    public void setCorns(int corns) {
        this.corns = corns;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setEnvelope(int envelope) {
        this.envelope = envelope;
    }

    public void setCornsforgrab(int cornsforgrab) {
        this.cornsforgrab = cornsforgrab;
    }

    public void setAlliancerewards(int alliancerewards) {
        this.alliancerewards = alliancerewards;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setFriendcount(int friendcount) {
        this.friendcount = friendcount;
    }

    public void setConcerncount(int concerncount) {
        this.concerncount = concerncount;
    }

    public void setIsdraw(int isdraw) {
        this.isdraw = isdraw;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getPhone() {
        return phone;
    }

    public String getPwd() {
        return pwd;
    }

    public String getAuthKey() {
        return authKey;
    }

    public String getFatherid() {
        return fatherid;
    }

    public int getDirectalliancecount() {
        return directalliancecount;
    }

    public int getAllalliancecount() {
        return allalliancecount;
    }

    public int getCorns() {
        return corns;
    }

    public int getMoney() {
        return money;
    }

    public int getEnvelope() {
        return envelope;
    }

    public int getCornsforgrab() {
        return cornsforgrab;
    }

    public int getAlliancerewards() {
        return alliancerewards;
    }

    public String getNickname() {
        return nickname;
    }

    public String getThumb() {
        return thumb;
    }

    public int getGender() {
        return gender;
    }

    public String getArea() {
        return area;
    }

    public String getJob() {
        return job;
    }

    public String getHobby() {
        return hobby;
    }

    public String getSignature() {
        return signature;
    }

    public int getCreated_at() {
        return created_at;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public String getChannel() {
        return channel;
    }

    public String getPlatform() {
        return platform;
    }

    public int getFriendcount() {
        return friendcount;
    }

    public int getConcerncount() {
        return concerncount;
    }

    public int getIsdraw() {
        return isdraw;
    }

    public int getStatus() {
        return status;
    }

    public void setHxId(String hxId) {
        this.hxId = hxId;
    }

    public String getHxId() {
        return hxId;
    }
}
