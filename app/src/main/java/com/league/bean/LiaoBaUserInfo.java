package com.league.bean;

/**
 * Created by liug on 15/11/10.
 */
public class LiaoBaUserInfo {
    private String thumb;
    private String name;
    private String time;
    private int hot_new_flag;
    private String title;
    private String content;
    private String[] content_picture;
    private int flag_concern;
    private int dianzannum;
    private int commentnum;
    private int funsnum;
    private int likenum;

    public int getFunsnum() {
        return funsnum;
    }

    public void setFunsnum(int funsnum) {
        this.funsnum = funsnum;
    }

    public int getLikenum() {
        return likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHot_new_flag() {
        return hot_new_flag;
    }

    public void setHot_new_flag(int hot_new_flag) {
        this.hot_new_flag = hot_new_flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getContent_picture() {
        return content_picture;
    }

    public void setContent_picture(String[] content_picture) {
        this.content_picture = content_picture;
    }

    public int getFlag_concern() {
        return flag_concern;
    }

    public void setFlag_concern(int flag_concern) {
        this.flag_concern = flag_concern;
    }

    public int getDianzannum() {
        return dianzannum;
    }

    public void setDianzannum(int dianzannum) {
        this.dianzannum = dianzannum;
    }

    public int getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }
}
