package com.gotye.api;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 消息对象
 *
 */
public class GotyeMessage {
	 
	private long date;

	private long dbId;

	private long id;

	private GotyeMedia media, extra;

	private byte[] userData, extraData;
	private GotyeChatTarget receiver;

	private int receiver_type;

	private GotyeChatTarget sender;

	private int sender_type;

	private String text;

	private GotyeMessageType type;
	private GotyeMessageStatus status;

	public GotyeMessageStatus getStatus() {
		return status;
	}

	private GotyeMessage() {

	}

//	public Media getExtra() {
//		return extra;
//	}

	public GotyeMedia getMedia() {
		return media;
	}

	public void setMedia(GotyeMedia media) {
		this.media = media;
	}

	public void putExtraData(byte[] extraData) {
		this.extraData = extraData;
	}

	public void setStatus(GotyeMessageStatus status) {
		this.status = status;
	}

	public byte[] getUserData() {
		return userData;
	}

	public byte[] getExtraData() {
		return extraData;
	}

//	public void setExtra(Media extra) {
//		this.extra = extra;
//	}

	/**
	 * 获取额外数据
	 * @return
	 */
	 
 
	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	/**
	 * 获取消息本地数据库ID(重要)
	 * @return
	 */
	public long getDbId() {
		return dbId;
	}

	public void setDbId(long dbId) {
		this.dbId = dbId;
	}

	/**
	 * 获取消息服务器ID
	 * @return
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	 
	/**
	 * 获取消息接收者
	 * @return
	 */
	public GotyeChatTarget getReceiver() {
		return receiver;
	}

	/**
	 * 设置消息接收者
	 * @param receiver
	 */
	public void setReceiver(GotyeChatTarget receiver) {
		this.receiver = receiver;
	}

	/**
	 * 获取
	 * @return
	 */
	public int getReceiverType() {
		return receiver_type;
	}

	public void setReceiverType(int receiver_type) {
		this.receiver_type = receiver_type;
	}

	public GotyeChatTarget getSender() {
		return sender;
	}

	public void setSender(GotyeChatTarget sender) {
		this.sender = sender;
	}

	public int getSenderType() {
		return sender_type;
	}

	public void setSenderType(int sender_type) {
		this.sender_type = sender_type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public GotyeMessageType getType() {
		return type;
	}

	public void setType(GotyeMessageType type) {
		this.type = type;
	}

	public static GotyeMessage createMessage(GotyeChatTarget receiver) {
		return createMessage(GotyeAPI.getInstance().getLoginUser(),receiver);
	}
	public boolean hasMedia() {
	    return type == GotyeMessageType.GotyeMessageTypeAudio || type == GotyeMessageType.GotyeMessageTypeImage || type == GotyeMessageType.GotyeMessageTypeUserData;
	}
	
	public boolean hasExtraData(){
		if(extra!=null){
			return !TextUtils.isEmpty(extra.getPath());
		}
		return false;
		
	}
	public static GotyeMessage createMessage(GotyeChatTarget sender,
			GotyeChatTarget receiver) {
		GotyeMessage message = new GotyeMessage();
		message.sender = sender;
		message.receiver = receiver;
		message.setDate(System.currentTimeMillis() / 1000);
		return message;
	}
	//
	public static GotyeMessage createTextMessage(GotyeChatTarget receiver,
			String text) {
		return createTextMessage(GotyeAPI.getInstance().getLoginUser(),receiver,text);
	}

	public static GotyeMessage createTextMessage(GotyeChatTarget sender,
			GotyeChatTarget receiver, String text) {
		GotyeMessage message = new GotyeMessage();
		message.sender = sender;
		message.receiver = receiver;
		message.text = text;
		message.setDate(System.currentTimeMillis() / 1000);
		message.type = GotyeMessageType.GotyeMessageTypeText;
		return message;
	}

	public static GotyeMessage createImageMessage(GotyeChatTarget receiver,
			String imagePath) {
		return createImageMessage(GotyeAPI.getInstance().getLoginUser(),receiver,imagePath);
	}
	public static GotyeMessage createImageMessage(GotyeChatTarget sender,
			GotyeChatTarget receiver, String imagePath) {
		GotyeMessage message = new GotyeMessage();
		message.sender = sender;
		message.receiver = receiver;
		message.setDate(System.currentTimeMillis() / 1000);
		GotyeMedia media = new GotyeMedia();
		media.setPathEx(imagePath);
		media.setType(GotyeMediaType.GotyeMediaTypeImage);
		message.media = media;
		message.type = GotyeMessageType.GotyeMessageTypeImage;
		return message;
	}
	//

	public static GotyeMessage createUserDataMessage(GotyeChatTarget receiver,
			String dataPath) {
		return createUserDataMessage(GotyeAPI.getInstance().getLoginUser(),receiver,dataPath);
	}
	
	
	public static GotyeMessage createUserDataMessage(GotyeChatTarget sender,
			GotyeChatTarget receiver, String dataPath) {
		GotyeMessage message = new GotyeMessage();
		message.sender = sender;
		message.receiver = receiver;
		GotyeMedia media = new GotyeMedia();
		media.setPath(dataPath);
		message.setDate(System.currentTimeMillis() / 1000);
		media.setType(GotyeMediaType.GotyeMediaTypeUserData);
		message.media = media;
		message.type = GotyeMessageType.GotyeMessageTypeUserData;
		return message;
	}

	public static GotyeMessage createUserDataMessage(GotyeChatTarget receiver,
			byte[] data, int len) {
		return createUserDataMessage(GotyeAPI.getInstance().getLoginUser(),receiver,data,len);

	}

	public static GotyeMessage createUserDataMessage(GotyeChatTarget sender,
			GotyeChatTarget receiver, byte[] data, int len) {
		if(data==null){
            throw new NullPointerException("data is null");
		}
		if(data.length<len){
			throw new ArrayIndexOutOfBoundsException("len > data length");
		}
		GotyeMessage message = new GotyeMessage();
		message.sender = sender;
		message.receiver = receiver;
		message.userData=data;
		message.type = GotyeMessageType.GotyeMessageTypeUserData;
		return message;
	}
	public static GotyeMessage jsonToMessage(String jsonMessage) {
		if (jsonMessage == null || jsonMessage.length() == 0) {
			return null;
		}
		try {
			return jsonToMessage(new JSONObject(jsonMessage));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static GotyeMessage jsonToMessage(JSONObject obj) {
		GotyeMessage msg = new GotyeMessage();
			msg.date = obj.optLong("date");
			msg.dbId = obj.optLong("dbID");
			msg.id = obj.optLong("id");
			int type = obj.optInt("type");
			try{
			GotyeMessageType gt = GotyeMessageType.values()[type];
			msg.type = gt;
			}catch(Exception e){
			//	e.printStackTrace();
			  return null;
			}
			
			msg.receiver_type = obj.optInt("receiver_type");
			if (msg.receiver_type == 0) {
				GotyeUser user = new GotyeUser(obj.optString("receiver"));
				msg.receiver = user;
			} else if (msg.receiver_type == 1) {
				GotyeRoom room = new GotyeRoom();
				room.Id = Long.parseLong(obj.optString("receiver"));
				msg.receiver = room;
			} else if (msg.receiver_type == 2) {
				GotyeGroup group = new GotyeGroup();
				group.Id = Long.parseLong(obj.optString("receiver"));
				msg.receiver = group;
			}

			msg.sender_type = obj.optInt("sender_type");

			if (msg.sender_type == 0) {
				GotyeUser user = new GotyeUser(obj.optString("sender"));
				msg.sender = user;
			} else if (msg.sender_type == 1) {
				GotyeRoom room = new GotyeRoom();
				room.Id = Long.parseLong(obj.optString("sender"));
				msg.sender = room;
			} else if (msg.sender_type == 2) {
				GotyeGroup group = new GotyeGroup();
				group.Id = Long.parseLong(obj.optString("sender"));
				msg.sender = group;
			}
			msg.media = GotyeMedia.jsonToMedia(obj.optJSONObject("media"));
			if(msg.media!=null){
				if(msg.type==GotyeMessageType.GotyeMessageTypeUserData&&msg.media.getType()==GotyeMediaType.GotyeMediaTypeUserData){
					msg.userData=FileUtil.getBytes(msg.media.getPath());
				}
			}
			
			msg.extra=GotyeMedia.jsonToMedia(obj.optJSONObject("extra"));
			if(msg.extra!=null){
				msg.extraData=FileUtil.getBytes(msg.extra.getPath());
			}
			int status=obj.optInt("status");
			try{
			msg.status =GotyeMessageStatus.values()[status];
			}catch(Exception e){
				return null;
			}
			msg.text = obj.optString("text");
		return msg;
	}

	@Override
	public String toString() {
		return "GotyeMsgList [date=" + date + ", id=" + id + ", media=" + media
				+ ", receiver=" + receiver + ", receiver_type=" + receiver_type
				+ ", sender=" + sender + ", sender_is_type=" + sender_type
				+ ", text=" + text + ", type=" + type + "]";
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		GotyeMessage message = (GotyeMessage) object;
		if (message.getDbId() == this.dbId) {
			return true;
		} else {
			return false;
		}
	}

}
