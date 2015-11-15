package com.league.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by liug on 15/10/24.
 */
public class TenYuanGrabBean {
    /**
     * id : 2
     * picture : 122
     * title : 夺金500金币
     * version : 2
     * needed : 122
     * remain : 62
     * created_at : 1446903346
     * date : 124
     * end_at : 0
     * islotteried : 0
     * winneruserid : 0
     * winnerrecordid : 0
     * winnernumber : 0
     * foruser : 0
     * kind : 0
     */

    private String id;
    private String picture;
    private String title;
    private String version;
    private String needed;
    private String remain;
    private String created_at;
    private String date;
    private String end_at;
    private String islotteried;
    private String winneruserid;
    private String winnerrecordid;
    private String winnernumber;
    private String foruser;
    private String kind;

    /**
     * picture1 : http://img2.imgtn.bdimg.com/it/u=597462513,3132102173&fm=21&gp=0.jpg
     * picture2 : http://img2.imgtn.bdimg.com/it/u=597462513,3132102173&fm=21&gp=0.jpg
     * picture3 : http://pic55.nipic.com/file/20141208/8337244_105659585000_2.jpg
     * picture4 : http://pic55.nipic.com/file/20141208/8337244_105659585000_2.jpg
     * picture5 : http://img4.imgtn.bdimg.com/it/u=3142945243,3383730469&fm=21&gp=0.jpg
     * picture6 : http://img4.imgtn.bdimg.com/it/u=3142945243,3383730469&fm=21&gp=0.jpg
     */
    @JsonIgnore
    private String picture1;
    @JsonIgnore
    private String picture2;
    @JsonIgnore
    private String picture3;
    @JsonIgnore
    private String picture4;
    @JsonIgnore
    private String picture5;
    @JsonIgnore
    private String picture6;

    public void setId(String id) {
        this.id = id;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setNeeded(String needed) {
        this.needed = needed;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public void setIslotteried(String islotteried) {
        this.islotteried = islotteried;
    }

    public void setWinneruserid(String winneruserid) {
        this.winneruserid = winneruserid;
    }

    public void setWinnerrecordid(String winnerrecordid) {
        this.winnerrecordid = winnerrecordid;
    }

    public void setWinnernumber(String winnernumber) {
        this.winnernumber = winnernumber;
    }

    public void setForuser(String foruser) {
        this.foruser = foruser;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }

    public String getVersion() {
        return version;
    }

    public String getNeeded() {
        return needed;
    }

    public String getRemain() {
        return remain;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDate() {
        return date;
    }

    public String getEnd_at() {
        return end_at;
    }

    public String getIslotteried() {
        return islotteried;
    }

    public String getWinneruserid() {
        return winneruserid;
    }

    public String getWinnerrecordid() {
        return winnerrecordid;
    }

    public String getWinnernumber() {
        return winnernumber;
    }

    public String getForuser() {
        return foruser;
    }

    public String getKind() {
        return kind;
    }

    public TenYuanGrabBean() {

    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public void setPicture4(String picture4) {
        this.picture4 = picture4;
    }

    public void setPicture5(String picture5) {
        this.picture5 = picture5;
    }

    public void setPicture6(String picture6) {
        this.picture6 = picture6;
    }

    public String getPicture1() {
        return picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public String getPicture4() {
        return picture4;
    }

    public String getPicture5() {
        return picture5;
    }

    public String getPicture6() {
        return picture6;
    }
}
