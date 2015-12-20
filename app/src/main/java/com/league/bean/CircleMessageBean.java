package com.league.bean;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pfy on 2015/12/20.
 */
public class CircleMessageBean {


    /**
     * id : 11
     * userid : 4
     * content : 关系无比复杂2
     * pictures : http://www.ahgame.com/uploads/allimg/130124/3-130124091JX19.jpg
     * created_at : 111144867611811
     * phone : 2
     * thumb : http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2
     * nickname : 2
     * iszaned : 0
     * replys : [{"id":"1","messageid":"3","content":"回复1","fromid":"4","toid":"3","isread":"0","created_at":1111111446479961,"fromnickname":"2","fromphone":"2","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":"测试1","tophone":"1","tothumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"}]
     * zans : [{"phone":"2","nickname":"2"}]
     */

    private String id;
    private String userid;
    private String content;
    private String pictures;
    private long created_at;
    private String phone;
    private String thumb;
    private String nickname;
    private int iscollected;
    private int iszaned;
    private int ismy;
    private List<ReplysEntity> replys;
    private List<ZansEntity> zans;

    public List<String> getPictureList(){
        if (TextUtils.isEmpty(pictures))
            return null;
        return Arrays.asList(pictures.trim().split(" "));
    }

    public int getIsmy() {
        return ismy;
    }

    public void setIsmy(int ismy) {
        this.ismy = ismy;
    }

    public int getIscollected() {
        return iscollected;
    }

    public void setIscollected(int iscollected) {
        this.iscollected = iscollected;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setIszaned(int iszaned) {
        this.iszaned = iszaned;
    }

    public void setReplys(List<ReplysEntity> replys) {
        this.replys = replys;
    }

    public void setZans(List<ZansEntity> zans) {
        this.zans = zans;
    }

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getContent() {
        return content;
    }

    public String getPictures() {
        return pictures;
    }

    public long getCreated_at() {
        return created_at;
    }

    public String getPhone() {
        return phone;
    }

    public String getThumb() {
        return thumb;
    }

    public String getNickname() {
        return nickname;
    }

    public int getIszaned() {
        return iszaned;
    }

    public List<ReplysEntity> getReplys() {
        return replys;
    }

    public List<ZansEntity> getZans() {
        return zans;
    }

    public static class ReplysEntity {
        /**
         * id : 1
         * messageid : 3
         * content : 回复1
         * fromid : 4
         * toid : 3
         * isread : 0
         * created_at : 1111111446479961
         * fromnickname : 2
         * fromphone : 2
         * fromthumb : http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2
         * tonickname : 测试1
         * tophone : 1
         * tothumb : http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2
         */

        private String id;
        private String messageid;
        private String content;
        private String fromid;
        private String toid;
        private String isread;
        private long created_at;
        private String fromnickname;
        private String fromphone;
        private String fromthumb;
        private String tonickname;
        private String tophone;
        private String tothumb;

        public void setId(String id) {
            this.id = id;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setFromid(String fromid) {
            this.fromid = fromid;
        }

        public void setToid(String toid) {
            this.toid = toid;
        }

        public void setIsread(String isread) {
            this.isread = isread;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public void setFromnickname(String fromnickname) {
            this.fromnickname = fromnickname;
        }

        public void setFromphone(String fromphone) {
            this.fromphone = fromphone;
        }

        public void setFromthumb(String fromthumb) {
            this.fromthumb = fromthumb;
        }

        public void setTonickname(String tonickname) {
            this.tonickname = tonickname;
        }

        public void setTophone(String tophone) {
            this.tophone = tophone;
        }

        public void setTothumb(String tothumb) {
            this.tothumb = tothumb;
        }

        public String getId() {
            return id;
        }

        public String getMessageid() {
            return messageid;
        }

        public String getContent() {
            return content;
        }

        public String getFromid() {
            return fromid;
        }

        public String getToid() {
            return toid;
        }

        public String getIsread() {
            return isread;
        }

        public long getCreated_at() {
            return created_at;
        }

        public String getFromnickname() {
            return fromnickname;
        }

        public String getFromphone() {
            return fromphone;
        }

        public String getFromthumb() {
            return fromthumb;
        }

        public String getTonickname() {
            return tonickname;
        }

        public String getTophone() {
            return tophone;
        }

        public String getTothumb() {
            return tothumb;
        }
    }

    public static class ZansEntity {
        /**
         * phone : 2
         * nickname : 2
         */

        private String phone;
        private String nickname;

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
