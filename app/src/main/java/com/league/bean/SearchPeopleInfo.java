package com.league.bean;
/**  
 *   
 * @author liugang  
 * @date 2015年9月26日   
 */
public class SearchPeopleInfo {

	String thumb;
	String userNickname;
	String sear_location;
	String lasttime;
	String comnumber;//消息数量
	String infoContent;
	String secContent;
	String[] imgList;
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	
	public String getSear_location() {
		return sear_location;
	}
	public void setSear_location(String sear_location) {
		this.sear_location = sear_location;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	public String getComnumber() {
		return comnumber;
	}
	public void setComnumber(String comnumber) {
		this.comnumber = comnumber;
	}
	public String getInfoContent() {
		return infoContent;
	}
	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}
	public String getSecContent() {
		return secContent;
	}
	public void setSecContent(String secContent) {
		this.secContent = secContent;
	}
	public String[] getImgList() {
		return imgList;
	}
	public void setImgList(String[] imgList) {
		this.imgList = imgList;
	}
	
}
