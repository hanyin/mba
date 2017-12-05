package com.hy.mvp.ui.bean;


import com.hy.mvp.greendao.bean.ContactsPortraitConverter;
import com.hy.mvp.greendao.bean.LinkFileConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AddrBook {
	@Id
	private Long _id;
	private String id;			//id
	private String ownerId; 	//通讯录所有人的Id
	private String contactsId;	//联系人ID
	private int status = 0;		//状态   0：未删除； 1： 已删除； 
	private long create_time;
	
	//关联信息
	private String contacts_name; //联系人姓名
	private String contacts_school;//联系人所在学校
	private String pingyin;
	@Convert(converter = ContactsPortraitConverter.class, columnType = String.class)
	private ContactsPortrait contacts_portrait; //联系人头像

	@Generated(hash = 599999266)
	public AddrBook(Long _id, String id, String ownerId, String contactsId,
			int status, long create_time, String contacts_name, String contacts_school,
			String pingyin, ContactsPortrait contacts_portrait) {
		this._id = _id;
		this.id = id;
		this.ownerId = ownerId;
		this.contactsId = contactsId;
		this.status = status;
		this.create_time = create_time;
		this.contacts_name = contacts_name;
		this.contacts_school = contacts_school;
		this.pingyin = pingyin;
		this.contacts_portrait = contacts_portrait;
	}

	@Generated(hash = 706415189)
	public AddrBook() {
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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getContactsId() {
		return contactsId;
	}

	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public String getContacts_name() {
		return contacts_name;
	}

	public void setContacts_name(String contacts_name) {
		this.contacts_name = contacts_name;
	}

	public String getContacts_school() {
		return contacts_school;
	}

	public void setContacts_school(String contacts_school) {
		this.contacts_school = contacts_school;
	}

	public ContactsPortrait getContacts_portrait() {
		return contacts_portrait;
	}

	public void setContacts_portrait(ContactsPortrait contacts_portrait) {
		this.contacts_portrait = contacts_portrait;
	}

	public String getPingyin() {
		return pingyin;
	}

	public void setPingyin(String pingyin) {
		this.pingyin = pingyin;
	}
}
