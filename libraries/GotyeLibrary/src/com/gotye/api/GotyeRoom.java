package com.gotye.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 聊天室对象
 *
 */
public class GotyeRoom extends GotyeChatTarget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isTop;

	private int capacity;

	private int onlineNumber;


	public long getRoomID() {
		return Id;
	}

	public GotyeRoom() {
		this.type = GotyeChatTargetType.GotyeChatTargetTypeRoom;
	}

	public GotyeRoom(long roomID) {
		this.Id = roomID;
		this.type = GotyeChatTargetType.GotyeChatTargetTypeRoom;
	}
	public void setRoomID(long roomID) {
		this.Id = roomID;
	}

	public String getRoomName() {
		return name;
	}

	public void setRoomName(String roomName) {
		this.name = roomName;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}
 
	  
	public int getCapacity() {
		return capacity;
	}

	public int getOnlineNumber() {
		return onlineNumber;
	}

	@Override
	public String toString() {
		return "GotyeRoom [roomID=" + Id + ", roomName=" + name + ", isTop="
				+ isTop + ", userLimit=" + capacity + ", curUerCount="
				+ onlineNumber + ", icon=" + icon + "]";
	}

	public static GotyeRoom createRoomJson(JSONObject object) {

		GotyeRoom gotyeRoom = new GotyeRoom();
		GotyeMedia icon = new GotyeMedia();
		JSONObject obj = object.optJSONObject("icon");
		if (obj != null) {
			icon.setPath(obj.optString("path"));
			icon.setPathEx(obj.optString("path_ex"));
			icon.setUrl(obj.optString("url"));
		}
		gotyeRoom.onlineNumber = object.optInt("onlineNumber");
		gotyeRoom.setRoomID(object.optLong("id"));
		gotyeRoom.setRoomName(object.optString("name"));
		gotyeRoom.setTop(object.optBoolean("isTop"));
		gotyeRoom.capacity=object.optInt("capacity");
		gotyeRoom.setIcon(icon);
		gotyeRoom.type = GotyeChatTargetType.GotyeChatTargetTypeRoom;
		return gotyeRoom;
	}

	public static GotyeRoom createRoomJson(String jsonRoom) {
		try {
			JSONObject obj = new JSONObject(jsonRoom);
			return createRoomJson(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
