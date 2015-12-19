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
     * created_at : 144867611844444
     * phone : 2
     * thumb : http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2
     * nickname : 2
     * iszaned : 0
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
    private int iszaned;
    private List<ZansEntity> zans;

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

    public List<ZansEntity> getZans() {
        return zans;
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

    public List<String> getPictureList(){
        if (TextUtils.isEmpty(pictures))
            return null;
        return Arrays.asList(pictures.trim().split(" "));
    }
}
