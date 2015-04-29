package com.gotye.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 群对象
 *
 */
public class GotyeGroup extends GotyeChatTarget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int capacity;
	private String ownerAccount;
	private GotyeGroupType ownerType;
	private boolean needAuthentication;

	  
	 
	public boolean isNeedAuthentication() {
		return needAuthentication;
	}

	public GotyeGroup(long groupId) {
		this.Id = groupId;
		this.type = GotyeChatTargetType.GotyeChatTargetTypeGroup;
	}

	public GotyeGroup() {
		this.type = GotyeChatTargetType.GotyeChatTargetTypeGroup;
	}

	public void setNeedAuthentication(boolean needAuthentication) {
		this.needAuthentication = needAuthentication;
	}

	public long getCapacity() {
		return capacity;
	}

	public long getGroupID() {
		return Id;
	}

	public void setGroupID(long groupID) {
		this.Id = groupID;
	}
 
	public String getGroupName() {
		return name;
	}

	public void setGroupName(String groupName) {
		this.name = groupName;
	}

	public String getOwnerAccount() {
		return ownerAccount;
	}

	public void setOwnerAccount(String ownerAccount) {
		this.ownerAccount = ownerAccount;
	}

	public GotyeGroupType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(GotyeGroupType ownerType) {
		this.ownerType = ownerType;
	}

	@Override
	public String toString() {
		return "GotyeGroup [capacity=" + capacity + ", groupInfo=" + info
				+ ", ownerAccount=" + ownerAccount + ", ownerType=" + ownerType
				+ ", needAuthentication=" + needAuthentication
				+ ", hasGotDetail=" + hasGotDetail + ", icon=" + icon + "]";
	}

	public static GotyeGroup createGroupJson(JSONObject object) {
		GotyeGroup group = new GotyeGroup();

		int mCapacity = object.optInt("capacity");
		long mGroupID = object.optLong("groupID");
		String mGroupInfo = object.optString("groupInfo");
		String mGroupName = object.optString("groupName");
		boolean mHasGotDetail = object.optBoolean("hasGotDetail");
		GotyeMedia icon = new GotyeMedia();
		JSONObject obj = object.optJSONObject("icon");
		if (obj != null) {
			String mPath = obj.optString("path");
			String mPath_ex = obj.optString("path_ex");
			String mUrl = obj.optString("url");
			icon.setPath(mPath);
			icon.setPathEx(mPath_ex);
			icon.setUrl(mUrl);
		}
		boolean mAuthentication = object.optBoolean("need_authentication");
		String mOAcount = object.optString("ownerAccount");
		int mOwnAType = object.optInt("ownerType");
		group.capacity=mCapacity;
		group.setGroupID(mGroupID);
		group.info=mGroupInfo;
		group.setGroupName(mGroupName);
		group.hasGotDetail=mHasGotDetail;
		group.setIcon(icon);
		group.setNeedAuthentication(mAuthentication);
		group.setOwnerAccount(mOAcount);
		group.setOwnerType(GotyeGroupType.values()[mOwnAType]);
		group.type = GotyeChatTargetType.GotyeChatTargetTypeGroup;
		return group;
	}

	public static GotyeGroup createGroupJson(String jsonStr) {
		try {
			JSONObject obj = new JSONObject(jsonStr);
			return createGroupJson(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
