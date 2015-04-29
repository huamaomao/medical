package com.gotye.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户对象
 *
 */
public class GotyeUser extends GotyeChatTarget {

	public static final int CSSTATUS_OFFLINE = 0;
	public static final int CSSTATUS_ONLINE = 1;
	public static final int CSSTATUS_BUSY = 2;
	public static final int CSSTATUS_PREOFFLINE = 3;

	/**
	 * 登陆状态
	 */
	public static final int LOGIN_STATE_ONLINE  = 1;
	/**
	 * 未登录状态
	 */
	public static final int LOGIN_STATE_UNLOGIN = 0;
	/**
	 * 离线状态
	 */
	public static final int LOGIN_STATE_OFFLINE = -1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GotyeUserGender gender;
	private String nickname;
	private boolean isBlocked;
	private boolean isFriend;
	public GotyeUser(String name) {
		this.name = name;
		this.type = GotyeChatTargetType.GotyeChatTargetTypeUser;
	}
	public GotyeUser() {
		this.type = GotyeChatTargetType.GotyeChatTargetTypeUser;
	}

	public GotyeUserGender getGender() {
		return gender;
	}

	public void setGender(GotyeUserGender gender) {
		this.gender = gender;
	}

	 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null) {
			return false;
		}
		GotyeUser user = (GotyeUser) o;

		if (name.equals(user.getName())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "GotyeUser [gender=" + gender + ", icon=" + icon + ", name="
				+ name + ", nickname=" + nickname + "]";
	}

	public static GotyeUser jsonToUser(String jsonUser) {
		try {
			JSONObject object = new JSONObject(jsonUser);
			return jsonToUser(object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static GotyeUser jsonToUser(JSONObject object) {
		GotyeUser user = new GotyeUser();
		user.setGender(GotyeUserGender.values()[object.optInt("gender")]);
		user.hasGotDetail=object.optBoolean("hasGotDetail");
		GotyeMedia icon = new GotyeMedia();
		JSONObject obj = object.optJSONObject("icon");
		if (obj != null) {
			String mPath = obj.optString("path");
			String mPath_ex = obj.optString("path_ex");
			String mUrl = obj.optString("url");
			icon.setPath(mPath);
			icon.setPathEx(mPath_ex);
			icon.setUrl(mUrl);
			user.setIcon(icon);
		}
		user.setBlocked(object.optBoolean("isBlocked"));
		user.setFriend(object.optBoolean("isFriend"));
		user.setInfo(object.optString("info"));
		user.setName(object.optString("name"));
		user.setNickname(object.optString("nickname"));
		return user;
	}

}
