package com.league.bean;

/**
 * Created by liug on 15/12/6.
 */
public class ShippingAddressBean {

    /**
     * id : 3
     * userid : 3
     * address : 浙江省杭州市浙江爱你广大学
     * name : 博爱
     * aphone : 2
     * postcode :
     * isdefault : 0
     * created_at :
     */

    private Long id;
    private Long userid;
    private String address;
    private String name;
    private String aphone;
    private String postcode;
    private int isdefault;
    private String created_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAphone() {
        return aphone;
    }

    public void setAphone(String aphone) {
        this.aphone = aphone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
