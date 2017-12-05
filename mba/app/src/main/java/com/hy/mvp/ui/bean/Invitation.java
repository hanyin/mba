package com.hy.mvp.ui.bean;


import com.hy.mvp.greendao.bean.ContactsPortraitConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Invitation {
	@Id
	private Long _id;
	private String id;
	private String inviterId;
	private String inviteeId;
	private String message;
	private int status = 1;     	//邀请状态  0：拒绝  1：验证中 2：通过验证
	private long invite_time;
	
	//关联信息
	private int wayType; //邀请方式类型。 1：请求对方加我为好友  2：对方请求我加入好友
	private String friend_name; //联系人姓名
	private String friend_school;//联系人所在学校
	@Convert(converter = ContactsPortraitConverter.class, columnType = String.class)
	private ContactsPortrait friend_portrait; //联系人头像

	@Generated(hash = 1230010178)
	public Invitation(Long _id, String id, String inviterId, String inviteeId,
			String message, int status, long invite_time, int wayType,
			String friend_name, String friend_school, ContactsPortrait friend_portrait) {
		this._id = _id;
		this.id = id;
		this.inviterId = inviterId;
		this.inviteeId = inviteeId;
		this.message = message;
		this.status = status;
		this.invite_time = invite_time;
		this.wayType = wayType;
		this.friend_name = friend_name;
		this.friend_school = friend_school;
		this.friend_portrait = friend_portrait;
	}

	@Generated(hash = 589668392)
	public Invitation() {
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInviterId() {
		return inviterId;
	}
	public void setInviterId(String inviterId) {
		this.inviterId = inviterId;
	}	
	public String getInviteeId() {
		return inviteeId;
	}
	public void setInviteeId(String inviteeId) {
		this.inviteeId = inviteeId;
	}	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getInvite_time() {
		return invite_time;
	}
	public void setInvite_time(long invite_time) {
		this.invite_time = invite_time;
	}
	
	//关联信息 
	public String getFriend_name() {
		return friend_name;
	}
	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}
	public String getFriend_school() {
		return friend_school;
	}
	public void setFriend_school(String friend_school) {

		this.friend_school = friend_school;
	}

	public ContactsPortrait getFriend_portrait() {
		return friend_portrait;
	}

	public void setFriend_portrait(ContactsPortrait friend_portrait) {
		this.friend_portrait = friend_portrait;
	}

	public int getWayType() {
		return wayType;
	}
	public void setWayType(int wayType) {
		this.wayType = wayType;
	}
	

	
	
	


}
