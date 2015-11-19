package com.league.bean;

/**
 * Created by pfy on 2015/11/19.
 */
public class CommentBean {

    /**
     * id : 1
     * userid : 3
     * recommendationid : 1
     * content : 地一条评论
     * created_at : 1446817599
     * phone : 1
     * nickname : 测试1
     * thumb : ty
     */

    private int id;
    private int userid;
    private int recommendationid;
    private String content;
    private long created_at;
    private String phone;
    private String nickname;
    private String thumb;

    public CommentBean() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setRecommendationid(int recommendationid) {
        this.recommendationid = recommendationid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
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

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public int getRecommendationid() {
        return recommendationid;
    }

    public String getContent() {
        return content;
    }

    public long getCreated_at() {
        return created_at;
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
}
