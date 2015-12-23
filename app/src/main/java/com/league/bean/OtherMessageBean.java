package com.league.bean;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pfy on 2015/12/22.
 */
public class OtherMessageBean {

    /**
     * id : 1
     * userid : 3
     * title : Hao
     * content : Hhh
     * pictures : http://7xoc8r.com2.z0.glb.qiniucdn.com/items/1450791929501.jpg
     * created_at : 1450791929
     * phone : 1
     * thumb : http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2
     * nickname : 账号3
     */

    private String id;
    private String userid;
    private String title;
    private String content;
    private String pictures;
    private long created_at;
    private String phone;
    private String thumb;
    private String nickname;

    public void setId(String id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getTitle() {
        return title;
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

    public List<String> getPictureList(){
        if (TextUtils.isEmpty(pictures))
            return null;
        return Arrays.asList(pictures.trim().split(" "));
    }
}
