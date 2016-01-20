package com.league.bean;

/**
 * Created by liug on 16/1/20.
 */
public class CountDetailBean {

    /**
     * count : 300
     * id : 199
     * created_at : 1453102466.413
     * type : 0
     * phone : 13705760757
     * nickname : 张雷
     * thumb : http://7xoc8r.com2.z0.glb.qiniucdn.com/items/1453198080137.png
     */

    private String count;
    private String id;
    private String created_at;
    private String type;
    private String phone;
    private String nickname;
    private String thumb;

    public void setCount(String count) {
        this.count = count;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCount() {
        return count;
    }

    public String getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getType() {
        return type;
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
