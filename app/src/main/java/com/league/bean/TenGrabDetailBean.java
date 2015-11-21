package com.league.bean;

import java.util.List;

/**
 * Created by liug on 15/11/18.
 */
public class TenGrabDetailBean {
    List<GrabRecordBean> grabRecordBean;
    List<MyRecordGrabBean> myRecordGrabBean;
    TenYuanGrabBean tenYuanGrabBean;

    public List<GrabRecordBean> getGrabRecordBean() {
        return grabRecordBean;
    }

    public void setGrabRecordBean(List<GrabRecordBean> grabRecordBean) {
        this.grabRecordBean = grabRecordBean;
    }

    public List<MyRecordGrabBean> getMyRecordGrabBean() {
        return myRecordGrabBean;
    }

    public void setMyRecordGrabBean(List<MyRecordGrabBean> myRecordGrabBean) {
        this.myRecordGrabBean = myRecordGrabBean;
    }

    public TenYuanGrabBean getTenYuanGrabBean() {
        return tenYuanGrabBean;
    }

    public void setTenYuanGrabBean(TenYuanGrabBean tenYuanGrabBean) {
        this.tenYuanGrabBean = tenYuanGrabBean;
    }
}
