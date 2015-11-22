package com.league.bean;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liug on 15/11/10.
 */
public class LiaoBaUserInfo {

    /**
     * id : 1
     * userid : 3
     * content : 第一条聊吧
     * pictures : http://c.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbed91fbb0837ef0f736aec31faf.jpg
     * likecount : 2
     * replycount : 10
     * created_at : 1447677098
     * title :
     * phone : 1
     * nickname : 测试1
     * thumb : http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2
     * isconcerned : 0
     * isliked : 1
     * replys : [{"id":"1","tbmessageid":"1","content":"第一条回复","fromid":"3","toid":"0","isread":"0","created_at":"1447679772","fromnickname":"测试1","fromphone":"1","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":null,"tophone":null,"tothumb":null},{"id":"2","tbmessageid":"1","content":"第二条回复","fromid":"3","toid":"4","isread":"0","created_at":"1447679778","fromnickname":"测试1","fromphone":"1","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":"2","tophone":"2","tothumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"},{"id":"3","tbmessageid":"1","content":"第三条回复","fromid":"4","toid":"3","isread":"0","created_at":"1447679783","fromnickname":"2","fromphone":"2","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":"测试1","tophone":"1","tothumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"},{"id":"4","tbmessageid":"1","content":"第四条回复","fromid":"4","toid":"3","isread":"0","created_at":"1447679786","fromnickname":"2","fromphone":"2","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":"测试1","tophone":"1","tothumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"},{"id":"5","tbmessageid":"1","content":"第五条回复","fromid":"3","toid":"4","isread":"0","created_at":"1447679789","fromnickname":"测试1","fromphone":"1","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":"2","tophone":"2","tothumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"},{"id":"6","tbmessageid":"1","content":"第六条回复","fromid":"3","toid":"4","isread":"0","created_at":"1447680729","fromnickname":"测试1","fromphone":"1","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":"2","tophone":"2","tothumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"},{"id":"7","tbmessageid":"1","content":"第六条回复","fromid":"3","toid":"4","isread":"0","created_at":"1447680772","fromnickname":"测试1","fromphone":"1","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":"2","tophone":"2","tothumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"},{"id":"12","tbmessageid":"1","content":"第六条回复","fromid":"3","toid":"4","isread":"0","created_at":"1447681047","fromnickname":"测试1","fromphone":"1","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":"2","tophone":"2","tothumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"},{"id":"13","tbmessageid":"1","content":"第六条回复","fromid":"3","toid":"4","isread":"0","created_at":"1447681094","fromnickname":"测试1","fromphone":"1","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":"2","tophone":"2","tothumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"},{"id":"14","tbmessageid":"1","content":"第10条回复","fromid":"3","toid":"4","isread":"0","created_at":"1447681153","fromnickname":"测试1","fromphone":"1","fromthumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2","tonickname":"2","tophone":"2","tothumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"}]
     * likes : [{"phone":"1","nickname":"测试1","thumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"},{"phone":"15088783890","nickname":"周佳威","thumb":"http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2"}]
     */

    private String id;
    private String userid;
    private String content;
    private String pictures;
    private int likecount;
    private int replycount;
    private long created_at;
    private String title;
    private String phone;
    private String nickname;
    private String thumb;
    private int isconcerned;
    private int isliked;
    /**
     * id : 1
     * tbmessageid : 1
     * content : 第一条回复
     * fromid : 3
     * toid : 0
     * isread : 0
     * created_at : 1447679772
     * fromnickname : 测试1
     * fromphone : 1
     * fromthumb : http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2
     * tonickname : null
     * tophone : null
     * tothumb : null
     */

    private List<ReplysEntity> replys;
    /**
     * phone : 1
     * nickname : 测试1
     * thumb : http://7xkbeq.com1.z0.glb.clouddn.com/Fo-v4V7aCVADWiMXJm9TW-kx37U2
     */

    private List<LikesEntity> likes;

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

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }

    public void setReplycount(int replycount) {
        this.replycount = replycount;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setIsconcerned(int isconcerned) {
        this.isconcerned = isconcerned;
    }

    public void setIsliked(int isliked) {
        this.isliked = isliked;
    }

    public void setReplys(List<ReplysEntity> replys) {
        this.replys = replys;
    }

    public void setLikes(List<LikesEntity> likes) {
        this.likes = likes;
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

    public int getLikecount() {
        return likecount;
    }

    public int getReplycount() {
        return replycount;
    }

    public long getCreated_at() {
        return created_at;
    }

    public String getTitle() {
        return title;
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

    public int getIsconcerned() {
        return isconcerned;
    }

    public int getIsliked() {
        return isliked;
    }

    public List<ReplysEntity> getReplys() {
        return replys;
    }

    public List<LikesEntity> getLikes() {
        return likes;
    }

    public List<String> getPictureList(){
        if (TextUtils.isEmpty(pictures))
            return null;
        return Arrays.asList(pictures.trim().split(" "));
    }
}
