package com.league.bean;

/**
 * @author liugang
 * @date 2015年9月26日
 */
public class HobbyInfoBean {

    /**
     * id : 2
     * userid : 4
     * picture : ghgjdfgsfg
     * sex : 0
     * age : 23
     * hobbyid : 2
     * hobby : 2
     * content : 第三七年条交友信息
     * created_at : 1446818071
     * phone : 2
     * nickname : 2
     * thumb : 2
     */

    private String id;
    private String userid;
    private String picture;
    private int sex;
    private int age;
    private String hobbyid;
    private String hobby;
    private String content;
    private long created_at;
    private String phone;
    private String nickname;
    private String thumb;

    public HobbyInfoBean() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHobbyid(String hobbyid) {
        this.hobbyid = hobbyid;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
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

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getPicture() {
        return picture;
    }

    public int getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getHobbyid() {
        return hobbyid;
    }

    public String getHobby() {
        return hobby;
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
