package com.league.bean;

import java.util.List;

/**
 * Created by liug on 15/11/18.
 */
public class OneGrabDetailBean {
    List<GrabRecordBean> grabRecordBean;
    List<MyRecordGrabBean> myRecordGrabBean;
    OneYuanBean oneYuanBean;

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

    public OneYuanBean getOneYuanBean() {
        return oneYuanBean;
    }

    public void setOneYuanBean(OneYuanBean oneYuanBean) {
        this.oneYuanBean = oneYuanBean;
    }
}
