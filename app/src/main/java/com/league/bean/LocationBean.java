package com.league.bean;

/**
 * Created by pfy on 2016/1/10.
 */
public class LocationBean {
    private double latitude;
    private double longitude;
    private String address;
    private String city;

    public LocationBean(){}

    public LocationBean(double latitude, double longitude, String city){
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
