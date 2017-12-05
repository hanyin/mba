package com.hy.mvp.ui.bean;

import java.io.Serializable;

public class Friend implements Serializable {
	
	private String leaguerId;			//会员id
	private String realname; 	//会员姓名
	private int sex = 1;			//性别 1:男；2：女
	private String nickname;	
	private String telnum;		//电话号码
	private String school;		//学校
	private String protrait_url;
	
	
 
	public String getLeaguerId() {
		return leaguerId;
	}
	public void setLeaguerId(String leaguerId) {
		this.leaguerId = leaguerId;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTelnum() {
		return telnum;
	}
	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getProtrait_url() {
		return protrait_url;
	}
	public void setProtrait_url(String protrait_url) {
		this.protrait_url = protrait_url;
	}
	
	

}
