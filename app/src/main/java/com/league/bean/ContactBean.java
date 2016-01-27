package com.league.bean;

import android.text.TextUtils;

public class ContactBean {

	private String phone;
	private String thumb;
	private String nickname;
	private int isRegister;

	public ContactBean() {

	}

	public ContactBean(String phone, String thumb, String nickname) {
		this.phone = phone;
		this.thumb = thumb;
		this.nickname = nickname;
	}

	public int getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(int isRegister) {
		this.isRegister = isRegister;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o instanceof ContactBean){
			ContactBean contanctBean = (ContactBean) o;
			if (TextUtils.isEmpty(contanctBean.getPhone()))
				return false;
			return contanctBean.getPhone().equals(this.phone);
		}
		return false;
	}

}
