package com.rolle.doctor.domain;

/****
 * 联系人
 */
public class ContactBean {

	private int contactId; //id
	private String nickname;//姓名
	private String mobile; // 电话号码
	private String pinyin; // 姓名拼音

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
}
