package com.gotye.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.gotye.api.listener.GotyeListener;
import com.gotye.api.listener.NotifyListener;

/**
 * Gotye IM 入口
 * 
 * @author gotye
 *
 */
public final class GotyeAPI {
	// 保存的应用上下文环境
	private Context context;
	private KeepAlive mKeepAlive;
	private static GotyeAPI mInstance = null;
	static GotyeDelegate mListener = new GotyeListenerImp();
	// 所有注册的监听器
	private ArrayList<GotyeListener> listeners = new ArrayList<GotyeListener>();

	// 私有化构造方法，同时启动timer
	private GotyeAPI() {
		myHandler.sendEmptyMessage(0);
	}

	private static Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 驱动回调事件
			getInstance().mainloop();
			myHandler.sendMessageDelayed(myHandler.obtainMessage(0),50);
		}
	};

	// 获取实例
	public static GotyeAPI getInstance() {
		if (null == mInstance) {
			mInstance = new GotyeAPI();
		}
		return mInstance;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 *            当前应用环境
	 * @param appKey
	 *            亲加官网申请的appKey
	 * @return 参见 {@link GotyeStatusCode}
	 */
	public int init(Context context, String appKey) {
		this.context = context.getApplicationContext();
		mKeepAlive = new KeepAlive(context, myHandler);
		mKeepAlive.startKeepAlive();
		int ret = init(appKey, context.getPackageName());
		bindCallbacks();

		return ret;
	}

	/**
	 * 在退出的时候调用该方法，停止发送心跳消息
	 */
	public void onDestory() {
		if (mKeepAlive != null) {
			mKeepAlive.stopKeepAlive();
		}
	}

	ArrayList<GotyeListener> getListeners() {
		// TODO Auto-generated method stub
		ArrayList<GotyeListener> copy = new ArrayList<GotyeListener>(
				listeners.size());
		copy.addAll(listeners);
		return copy;
	}

	/**
	 * 添加注册监听器
	 * 
	 * @param listener
	 */
	public void addListener(GotyeListener listener) {
		if (listener != null && !listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * 删除
	 * 
	 * @param listener
	 */
	public void removeListener(GotyeListener listener) {
		if (listener != null && listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	private native int init(String appKey, String packageName);

	private native void bindCallbacks();

	/**
	 * 退出GotyeAPI
	 */
	public native void exit();

	// log 输出
	public native void enableLog(boolean info, boolean error, boolean logfile);

	/**
	 * 获取客服人员
	 * 
	 * @param groupId
	 *            组ID
	 * @param tag
	 *            Tag
	 * @return 操作结果，参见 {@link GotyeStatusCode}
	 */
	public native int reqCustomerService(int groupId, String tag);

	// -------------------------tools---------------------------
	/**
	 * 清理缓存
	 * 
	 * @return 操作结果，参见 {@link GotyeStatusCode}
	 */
	public native int setNetConfig(String ip, int port);

	/**
	 * 清理缓存
	 * 
	 * @return
	 */
	public native int clearCache();

	public native void keepalive();

	private native int mainloop();

	/**
	 * @return -1 offline, API will reconnect when network becomes valid,<br/>
	 *         0 not login or logout already,</br> 1 online
	 */
	public native int isOnLine();

	private native String getLoginuser();

	private native String getTargetDetail(String target, int type,
			boolean forceRequest);

	private native int modifyUserinfo(String nickname, int gender, String info,
			String path);// add param info

	private native int inRoom(long roomId);

	private native void markMessagesAsread(String target, int type,
			boolean isread);

	private native void markSingleMessageAsRead(long msgDbid, boolean isRead);

	private native void deleteMessage(long msgDbid);

	private native void clearMessages(String target, int type);

	private native int getUnreadMsgcount(String target, int type);

	private native int getTotalUnreadMsgcount();

	private native int getUnreadMsgcountByType(int type);

	private native String getSessionlist();

	private native void activeSession(String target, int type);

	private native void deactiveSession(String target, int type);

	private native void deleteSession(String target, int type,
			boolean removeMessages);

	private native void markSessionTop(String target, int type, boolean top);

	private native String getLastMessage(String target, int type);

	private native String getNotifylist();

	private native int markNotifyIsread(long dbID, boolean isRead);

	/**
	 * 清理掉所有未读通知状态
	 */
	public native void clearNotifyUnreadStatus();

	private native void deleteNotify(long dbID);

	// 启动或关闭文本敏感词过滤
	private native void enableTextFilter(int type, int enabled);

	/**
	 * 设置每次读取历史消息个数
	 * 
	 * @param increment
	 */
	public void setMessageReadIncrement(int increment) {
		setMsgReadincrement(increment);
	}

	private native void setMsgReadincrement(int increment);

	/**
	 * 设置每次请求历史记录个数
	 * 
	 * @param increment
	 */
	public void setMessageRequestIncrement(int increment) {
		setMsgRequestincrement(increment);
	}

	private native void setMsgRequestincrement(int increment);

	/**
	 * 开始接收离校消息
	 */
	public void beginReceiveOfflineMessage() {
		beginRcvOfflineMessge();
	}

	private native void beginRcvOfflineMessge();

	private native int getUnreadNotifycount();

	// ------------------------login----------------------------
	/**
	 * 登录
	 * 
	 * @param username
	 *            登录账号
	 * @param password
	 *            登录密码（若没有密码传null）
	 * @return 参见 {@link GotyeStatusCode}
	 */
	public native int login(String username, String password);

	public int login(String username) {
		return login(username, null);
	}

	/**
	 * 退出登录
	 * 
	 * @return 参见 {@link GotyeStatusCode}
	 */
	public native int logout();

	// ------------------------user----------------------------
	// private native String requestUserInfo(String username); 删除

	private native void resetUsersearch();

	private native String getLocalUserSearchlist();

	private native String getLocalUserCurpageSearchlist();

	private native int requestSearchUserlist(int page, String username,
			String nickname, int gender);

	private native String getLocalFriendlist();

	/**
	 * 获取好友列表
	 * 
	 * @return
	 */
	private native int requestFriendlist();

	private native String getLocalBlockedlist();

	/**
	 * 获取黑名单列表
	 * 
	 * @return
	 */
	private native int requestBlockedlist();

	private native int requestAddfriend(String who);

	private native int requestAddblocked(String who);

	private native int removeFriend(String who);

	private native int removebolcked(String who);

	// ----------------------room----------------------------

	/**
	 * 获取聊天室列表
	 * 
	 * @param pageIndex
	 *            页码，从0开始
	 * @return 参见 {@link GotyeStatusCode}
	 */
	private native int requestRoomList(int pageIndex);

	private native String getLocalRoomlist();

	private native int enterRoom(long roomId);

	private native int leaveRoom(long roomId);

	private native boolean supportRealtime(long roomId);

	private native int requestRoomMemberlist(long roomId, int pageIndex);

	private native void clearLocalRoomlist();

	// ---------------------group---------------------------
	private native int createGroup(String groupname, int ownerType,
			boolean needAuth, String groupInfo, String iconPath); // add param

	private native int joinGroup(long groupId);

	private native int leaveGroup(long groupId);

	private native int dismissGroup(long groupId);

	private native int kickoutUser(long groupId, String username);

	private native int requestGrouplist();

	private native int requestModifyGroupinfo(long groupId, String name,
			String info, int type, int need_auth, String imagePath);

	private native int requestGroupMemberlist(long groupId, int pageIndex);

	private native int inviteUserTogroup(String username, long groupId,
			String inviteMessage);

	private native int requestJoinGroup(long groupId, String reqMessage);

	private native int replyJoinGroup(String username, long group_id,
			String replyMessage, boolean agree);

	/**
	 * 搜索群
	 * 
	 * @param groupName
	 *            要查找的群名，模糊查询
	 * @param pageIndex
	 *            页码，从0开始
	 * @return 参见 {@link GotyeStatusCode}
	 */
	public int reqSearchGroup(String groupName, int pageIndex) {
		return requestSearchGroup(groupName, pageIndex);
	}

	private native int requestSearchGroup(String groupName, int pageIndex);

	/**
	 * 重置群搜索
	 */
	public native void resetGroupSearch();

	private native String getLocalGroupSearchlist();

	private native String getLocalGroupCurpageSearchlist();

	private native int changeGroupowner(long groupId, String username);

	private native String getLocalGrouplist();

	// --------------------chat-----------------------------
	private native int sendMessage(long msgdbid, byte[] extra, int len);// @

	private native int report(int type, String content, long msgdbid);

	private native int downloadMessage(long msgdbid);

	private native String getLocalMessage(String target, int type, boolean more);

	private native String getSessioninfo(String target, int type);//

	private native String sendText(String target, int type, String text,
			byte[] extra, int len);// @

	private native String sendImage(String target, int type, String imagePath,
			byte[] extra, int len);// @

	private native String sendUserData(String target, int type, byte[] data,
			int len, byte[] extra, int elen);// @

	private native String sendFile(String target, int isRoom, String path,
			byte[] extra, int elen);// @
	
	public native void enableShortRecording(boolean enabled);

	private native int startTalk(String target, int type, int mode,
			int realtime, int maxDuration);

	public native int getTalkingPower();

	private native int decodeMessage(long msgDbid);// @

	/**
	 * 停止录音
	 * 
	 * @return 参见 {@link GotyeStatusCode}
	 */
	public native int stopTalk();

	// -------------------play-----------------------------
	private native int playMessage(long msgdbid);

	/**
	 * 停止播放
	 * 
	 * @return 参见 {@link GotyeStatusCode}
	 */
	public native int stopPlay();

	// --------------------download-------------------------

	/**
	 * 下载头像
	 * 
	 * @param media
	 * @return
	 */
	public int downloadMedia(GotyeMedia media) {
		if (media == null) {
			throw new NullPointerException("media can not be null");
		}
		return downloadMedia(media.getUrl());
	}

	private native int downloadMedia(String url);

	public native int downloadAudio(String url);
	/**
	 * 判断是否已经进入该聊天室
	 * 
	 * @param room
	 * @return
	 */
	public boolean isInRoom(GotyeRoom room) {
		long result = inRoom(room.getRoomID());
		return result != 0;
	}

	// -------------------------------------------------------------------

	/**
	 * 获取当前登陆用户
	 * 
	 * @return
	 */
	public GotyeUser getLoginUser() {
		return GotyeUser.jsonToUser(getLoginuser());
	}

	// -------------------------------------------------------------------

	/**
	 * 获取会话列表
	 * 
	 * @return
	 */
	public List<GotyeChatTarget> getSessionList() {
		List<GotyeChatTarget> sessionList = null;
		try {
			String aa = getSessionlist();
			JSONArray array = new JSONArray(aa);
			int len = array.length();
			if (len > 0) {
				sessionList = new ArrayList<GotyeChatTarget>();
				for (int i = 0; i < len; i++) {
					sessionList
							.add(Utils.jsonToSession(array.getJSONObject(i)));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sessionList;
	}

	/**
	 * 获取对应Target的最后一条消息
	 * 
	 * @param target
	 * @return
	 */
	public GotyeMessage getLastMessage(GotyeChatTarget target) {
		if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			return GotyeMessage.jsonToMessage(getLastMessage(target.name,
					target.type.ordinal()));
		} else {
			return GotyeMessage.jsonToMessage(getLastMessage(
					String.valueOf(target.Id), target.type.ordinal()));
		}
	}

	/**
	 * 获取对应Target的历史消息记录
	 * 
	 * @param target
	 * @param more
	 *            是否去服务器请求更多
	 * @return
	 */
	public List<GotyeMessage> getMessageList(GotyeChatTarget target,
			boolean more) {
		String messagesJson;
		if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			messagesJson = getLocalMessage(target.name, target.type.ordinal(),
					more);
		} else {
			messagesJson = getLocalMessage(String.valueOf(target.Id),
					target.type.ordinal(), more);
		}

		if (messagesJson == null || messagesJson.length() == 0) {
			return null;
		}
		try {
			JSONArray array = new JSONArray(messagesJson);
			int len = array.length();
			if (len > 0) {
				List<GotyeMessage> messages = new ArrayList<GotyeMessage>();
				for (int i = 0; i < len; i++) {
					messages.add(GotyeMessage.jsonToMessage(array
							.getJSONObject(i)));
				}
				return messages;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取对应Target的未读消息个数
	 * 
	 * @param target
	 * @return
	 */
	public int getUnreadMessageCount(GotyeChatTarget target) {
		if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			return getUnreadMsgcount(target.name, target.type.ordinal());
		} else {
			return getUnreadMsgcount(String.valueOf(target.Id),
					target.type.ordinal());
		}
	}

	/**
	 * 获取总未读消息数量
	 * 
	 * @return
	 */
	public int getTotalUnreadMessageCount() {
		return getTotalUnreadMsgcount();
	}

	/**
	 * 标记对应Target的所有消息为已读
	 */
	public void markMessagesAsRead(GotyeChatTarget target, boolean isRead) {
		if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			markMessagesAsread(target.name, target.type.ordinal(), isRead);
		} else {
			markMessagesAsread(String.valueOf(target.Id),
					target.type.ordinal(), isRead);
		}
		// session 变动
		sessionStateChanged();
	}

	/**
	 * 标记对应消息为已读
	 * 
	 * @param message
	 */
	public void markOneMessageAsRead(GotyeMessage message, boolean isRead) {
		markSingleMessageAsRead(message.getDbId(), isRead);
		// session 变动
		sessionStateChanged();
	}

	/**
	 * 删除消息
	 * 
	 * @param message
	 */
	public void deleteMessage(GotyeMessage message) {
		deleteMessage(message.getDbId());
		// session 变动
		sessionStateChanged();
	}

	/**
	 * 清除对应Target所有消息
	 * 
	 * @param target
	 */
	public void clearMessages(GotyeChatTarget target) {
		if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			clearMessages(target.name, target.type.ordinal());
		} else {
			clearMessages(String.valueOf(target.Id), target.type.ordinal());
		}
		// session 变动
		sessionStateChanged();
	}

	/**
	 * 获取对应消息类型的所有未读个数
	 * 
	 * @param type
	 * @return
	 */
	public int getUnreadMsgcountByType(GotyeChatTargetType type) {
		return getUnreadMsgcountByType(type.ordinal());
	}

	/**
	 * 激活对应的会话session（这样收到的对应该Target的消息自动标记为已读）
	 * 
	 * @param target
	 */
	public void activeSession(GotyeChatTarget target) {
		if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			activeSession(target.name, target.type.ordinal());
		} else {
			activeSession(String.valueOf(target.Id), target.type.ordinal());
		}
	}

	/**
	 * 设置指定通知为已读
	 * 
	 * @param nofity
	 */
	public void markNotifyIsRead(GotyeNotify nofity) {
		markNotifyIsread(nofity.getDbID(), true);
		// session 变动
		sessionStateChanged();
	}

	/**
	 * 删除通知
	 * 
	 * @param nofity
	 */
	public void deleteNotify(GotyeNotify nofity) {
		deleteNotify(nofity.getDbID());
	}

	/**
	 * 获取未读通知数量
	 * 
	 * @return
	 */
	public int getUnreadNotifyCount() {
		return getUnreadNotifycount();
	}

	/**
	 * 取消激活会话
	 * 
	 * @param target
	 */
	public void deactiveSession(GotyeChatTarget target) {
		if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			deactiveSession(target.name, target.type.ordinal());
		} else {
			deactiveSession(String.valueOf(target.Id), target.type.ordinal());
		}
	}

	/**
	 * 设置会话置顶
	 * 
	 * @param target
	 * @param markTop
	 */
	public void markSessionIsTop(GotyeChatTarget target, boolean markTop) {
		if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			markSessionTop(target.name, target.type.ordinal(), markTop);
		} else {
			markSessionTop(String.valueOf(target.Id), target.type.ordinal(),
					markTop);
		}
		// session 变动
		sessionStateChanged();
	}

	/**
	 * 获取通知列表
	 * 
	 * @return
	 */
	public List<GotyeNotify> getNotifyList() {
		String notifyJsons = getNotifylist();
		List<GotyeNotify> notifiyList = null;
		try {
			JSONArray notifies = new JSONArray(notifyJsons);
			int len = notifies.length();
			if (len > 0) {
				notifiyList = new ArrayList<GotyeNotify>();
				for (int i = 0; i < len; i++) {
					notifiyList.add(GotyeNotify.jsonToNotify(notifies
							.getJSONObject(i)));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notifiyList;
	}

	/**
	 * 获取本地缓存好友列表
	 * 
	 * @return
	 */
	public List<GotyeUser> getLocalFriendList() {
		String json = getLocalFriendlist();
		List<GotyeUser> users = null;
		try {
			JSONArray array = new JSONArray(json);
			int len = array.length();
			if (len > 0) {
				users = new ArrayList<GotyeUser>();
				for (int i = 0; i < len; i++) {
					users.add(GotyeUser.jsonToUser(array.getJSONObject(i)));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 请求获取好友列表
	 * 
	 * @return
	 */
	public int reqFriendList() {
		return requestFriendlist();
	}

	/**
	 * 本地缓存黑名单列表
	 * 
	 * @return
	 */
	public List<GotyeUser> getLocalBlockedList() {
		String json = getLocalBlockedlist();
		List<GotyeUser> users = null;
		try {
			JSONArray array = new JSONArray(json);
			int len = array.length();
			if (len > 0) {
				users = new ArrayList<GotyeUser>();
				for (int i = 0; i < len; i++) {
					users.add(GotyeUser.jsonToUser(array.getJSONObject(i)));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 本地缓存群搜索列表
	 * 
	 * @return
	 */
	public List<GotyeGroup> getLocalGroupSearchList() {
		String jsonGroups = getLocalGroupSearchlist();
		if (jsonGroups != null && jsonGroups.length() > 0) {
			try {
				JSONArray groups = new JSONArray(jsonGroups);
				int len = groups.length();
				if (len > 0) {
					List<GotyeGroup> groupList = new ArrayList<GotyeGroup>();
					for (int i = 0; i < len; i++) {
						groupList.add(GotyeGroup.createGroupJson(groups
								.getJSONObject(i)));
					}
					return groupList;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 获取本地缓存群搜索当前页列表
	 * 
	 * @return
	 */
	public List<GotyeGroup> getLocalGroupSearchCurPageList() {
		String jsonGroups = getLocalGroupCurpageSearchlist();
		if (jsonGroups != null && jsonGroups.length() > 0) {
			try {
				JSONArray groups = new JSONArray(jsonGroups);
				int len = groups.length();
				if (len > 0) {
					List<GotyeGroup> groupList = new ArrayList<GotyeGroup>();
					for (int i = 0; i < len; i++) {
						groupList.add(GotyeGroup.createGroupJson(groups
								.getJSONObject(i)));
					}
					return groupList;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 获取本地缓存群列表
	 * 
	 * @return
	 */
	public List<GotyeGroup> getLocalGroupList() {
		String jsonGroups = getLocalGrouplist();
		if (jsonGroups != null && jsonGroups.length() > 0) {
			try {
				JSONArray groups = new JSONArray(jsonGroups);
				int len = groups.length();
				if (len > 0) {
					List<GotyeGroup> groupList = new ArrayList<GotyeGroup>();
					for (int i = 0; i < len; i++) {
						groupList.add(GotyeGroup.createGroupJson(groups
								.getJSONObject(i)));
					}
					return groupList;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 * @return
	 */
	public int sendMessage(GotyeMessage message) {
		byte[] extraData = message.getExtraData();
		int len = extraData == null ? 0 : extraData.length;
		if (message.getDbId() > 0) {
			return sendMessage(message.getDbId(), extraData, len);
		} else {
			String jsonMessage = null;
			GotyeChatTargetType receiverType = message.getReceiver().type;
			if (message.getType() == GotyeMessageType.GotyeMessageTypeText) {
				if (receiverType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
					jsonMessage = sendText(message.getReceiver().name,
							message.getReceiver().type.ordinal(),
							message.getText(), extraData, len);
				} else {
					String tar = String.valueOf(message.getReceiver().Id);
					jsonMessage = sendText(tar,
							message.getReceiver().type.ordinal(),
							message.getText(), extraData, len);
				}
			} else if (message.getType() == GotyeMessageType.GotyeMessageTypeImage) {
				if (receiverType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
					jsonMessage = sendImage(message.getReceiver().name,
							message.getReceiver().type.ordinal(), message
									.getMedia().getPathEx(), extraData, len);
				} else {
					String tar = String.valueOf(message.getReceiver().Id);
					jsonMessage = sendImage(tar,
							message.getReceiver().type.ordinal(), message
									.getMedia().getPathEx(), extraData, len);
				}
			} else if (message.getType() == GotyeMessageType.GotyeMessageTypeUserData) {

				if (receiverType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
					if (message.getMedia() != null) {
						jsonMessage = sendFile(message.getReceiver().name,
								message.getReceiver().type.ordinal(), message
										.getMedia().getPath(), extraData, len);
					} else {
						byte[] userData = message.getUserData();
						if (userData == null) {
							throw new NullPointerException("userData is null");
						}
						jsonMessage = sendUserData(message.getReceiver().name,
								message.getReceiver().type.ordinal(), userData,
								userData.length, extraData, len);
					}
				} else {
					String tar = String.valueOf(message.getReceiver().Id);
					if (message.getMedia() != null) {
						jsonMessage = sendFile(tar,
								message.getReceiver().type.ordinal(), message
										.getMedia().getPath(), extraData, len);
					} else {
						byte[] userData = message.getUserData();
						if (userData == null) {
							throw new NullPointerException("userData is null");
						}
						jsonMessage = sendUserData(tar,
								message.getReceiver().type.ordinal(), userData,
								userData.length, extraData, len);
					}
				}
			}
			try {
				JSONObject obj = new JSONObject(jsonMessage);
				int code = obj.getInt("code");
				GotyeMessage temp = GotyeMessage.jsonToMessage(obj
						.getJSONObject("message"));
				message.setDbId(temp.getDbId());
				message.setMedia(temp.getMedia());
				message.setStatus(temp.getStatus());
				message.setDate(temp.getDate());
				return code;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
		}
	}

	/**
	 * 修改用户信息
	 * 
	 * @param nickname
	 * @param gender
	 * @param info
	 * @param path
	 * @return
	 */
	public int reqModifyUserInfo(GotyeUser forModify, String path) {
		int result = modifyUserinfo(forModify.getNickname(), forModify
				.getGender().ordinal(), forModify.getInfo(), path);
		return result;
	}

	/**
	 * 重置用户搜索
	 */
	public void resetUserSearch() {
		resetUsersearch();
	}

	/**
	 * 获取本地缓存用户搜索列表
	 * 
	 * @return
	 */
	public List<GotyeUser> getLocalUserSearchList() {
		String userListStr = getLocalUserSearchlist();
		List<GotyeUser> users = null;
		try {
			JSONArray array = new JSONArray(userListStr);
			int len = array.length();
			if (len > 0) {
				users = new ArrayList<GotyeUser>();
				for (int i = 0; i < len; i++) {
					users.add(GotyeUser.jsonToUser(array.getJSONObject(i)));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 获取本地缓存用户搜索当前页列表
	 * 
	 * @return
	 */
	public List<GotyeUser> getLocalUserSearchCurPageList() {
		String userListStr = getLocalUserCurpageSearchlist();
		List<GotyeUser> users = null;
		try {
			JSONArray array = new JSONArray(userListStr);
			int len = array.length();
			if (len > 0) {
				users = new ArrayList<GotyeUser>();
				for (int i = 0; i < len; i++) {
					users.add(GotyeUser.jsonToUser(array.getJSONObject(i)));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 条件搜索用户
	 * 
	 * @param page
	 *            页码
	 * @param username
	 *            用户账户
	 * @param nickname
	 *            用户昵称
	 * @param gender
	 *            性别（如果无性别要求请传-1）
	 * @return
	 */
	public int reqSearchUserList(int page, String username, String nickname,
			GotyeUserGender gender) {
		return requestSearchUserlist(page, username, nickname, gender.ordinal());
	}

	public int reqSearchUserList() {
		return requestSearchUserlist(0, null, null, -1);
	}

	/**
	 * 举报
	 * 
	 * @param type
	 * @param content
	 * @param message
	 * @return
	 */
	public int report(int type, String content, GotyeMessage message) {
		return report(type, content, message.getDbId());
	}

	/**
	 * 播放消息
	 * 
	 * @param message
	 * @return
	 */
	public int playMessage(GotyeMessage message) {
		return playMessage(message.getDbId());
	}

	// ---------------------------------------user

	/**
	 * 获取用户详情
	 * 
	 * @param name
	 * @param forceRequest
	 *            该参数为true时返回当前缓存数据，并从服务器获取最新数据通过回调返回，若为false，当存在缓存时只返回缓存数据，
	 *            若不存在缓存则去服务器请求并通过回调返回
	 * @return 本地缓存数据，若本地没有，返回null
	 */
	public GotyeUser getUserDetail(String name, boolean forceRequest) {
		String result = getTargetDetail(name,
				GotyeChatTargetType.GotyeChatTargetTypeUser.ordinal(),
				forceRequest);
		return GotyeUser.jsonToUser(result);
	}

	
	/**
	 * 获取用户详情
	 * 
	 * @param target 基本数据类型
	 * @param forceRequest
	 *            该参数为true时返回当前缓存数据，并从服务器获取最新数据通过回调返回，若为false，当存在缓存时只返回缓存数据，
	 *            若不存在缓存则去服务器请求并通过回调返回
	 * @return 本地缓存数据，若本地没有，返回null
	 */
	public GotyeUser getUserDetail(GotyeChatTarget target, boolean forceRequest) {
		if(target.type==GotyeChatTargetType.GotyeChatTargetTypeUser){
			String result = getTargetDetail(target.name,
					GotyeChatTargetType.GotyeChatTargetTypeUser.ordinal(),
					forceRequest);
			return GotyeUser.jsonToUser(result);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取黑名单列表
	 * 
	 * @return 请求状态
	 */
	public int reqBlockedList() {
		return requestBlockedlist();
	}

	/**
	 * 请求添加好友
	 * 
	 * @param friend
	 *            被添加好友对象
	 * @return 请求状态
	 */
	public int requestAddFriend(GotyeUser friend) {
		return requestAddfriend(friend.getName());
	}

	/**
	 * 请求加好友
	 * 
	 * @param friendName
	 *            好友名字
	 * @return 请求状态
	 */
	public int requestAddFriend(String friendName) {
		return requestAddfriend(friendName);
	}

	/**
	 * 请求添加为黑名单
	 * 
	 * @param friend
	 * @return 请求状态
	 */
	public int reqAddFriend(GotyeUser friend) {
		return requestAddfriend(friend.getName());
	}

	public int reqAddBlocked(GotyeUser friend) {
		return requestAddblocked(friend.getName());
	}

	/**
	 * 请求删除好友
	 * 
	 * @param friend
	 *            被删除的好友对象
	 * @return 请求状态
	 */
	public int reqRemoveFriend(GotyeUser friend) {
		return removeFriend(friend.getName());
	}

	/**
	 * 请求删除黑名单
	 * 
	 * @param friend
	 *            被从黑名单删除的用户对象
	 * @return 请求状态
	 */
	public int reqRemoveBlocked(GotyeUser friend) {
		return removebolcked(friend.getName());
	}

	// ---------------------------------------end user

	// ----------------------------------------room

	/**
	 * 请求获取聊天室成员列表
	 * 
	 * @param room
	 *            当前房间
	 * @param pageNumber
	 * @return 请求状态
	 */
	public int reqRoomMemberList(GotyeRoom room, int pageNumber) {
		return requestRoomMemberlist(room.getRoomID(), pageNumber);
	}

	/**
	 * 清除本地房间信息缓存
	 */
	public void clearLocalRoomList() {
		clearLocalRoomlist();
	}

	/**
	 * 创建群
	 * 
	 * @param group
	 *            要创建的群对象
	 * @param groupIconPath
	 *            要创建的群头像本地文件路径
	 * @return 请求状态
	 */

	public int createGroup(GotyeGroup group, String groupIconPath) {
		return createGroup(group.getGroupName(),
				group.getOwnerType().ordinal(), group.isNeedAuthentication(),
				group.getInfo(), groupIconPath);
	}

	/**
	 * 获取本地缓存聊天室列表
	 * 
	 * @return 本地缓存聊天室列表
	 */
	public List<GotyeRoom> getLocalRoomList() {
		String roomJsonStr = getLocalRoomlist();
		if (roomJsonStr != null && roomJsonStr.length() > 0) {
			try {
				JSONArray rooms = new JSONArray(roomJsonStr);
				int len = rooms.length();
				if (len > 0) {
					List<GotyeRoom> roomList = new ArrayList<GotyeRoom>();
					for (int i = 0; i < len; i++) {
						roomList.add(GotyeRoom.createRoomJson(rooms
								.getJSONObject(i)));
					}
					return roomList;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 请求进入聊天室
	 * 
	 * @param room
	 *            当前聊天室对象
	 * @return 请求状态
	 */
	public int enterRoom(GotyeRoom room) {
		return enterRoom(room.getRoomID());
	}

	/**
	 * 请求离开聊天室
	 * 
	 * @param room
	 *            当前聊天室
	 * @return 请求状态
	 */
	public int leaveRoom(GotyeRoom room) {
		return leaveRoom(room.getRoomID());
	}

	/**
	 * 判断该聊天室是否支持实时语音
	 * 
	 * @param room
	 *            当前聊天室
	 * @return 返回该聊天室是否支持实时语音结果
	 */
	public boolean supportRealtime(GotyeRoom room) {
		return supportRealtime(room.getRoomID());
	}

	/**
	 * 获取聊天室列表
	 * 
	 * @param pageIndex 分页页码
	 * @return
	 */
	public int reqRoomList(int pageIndex) {
		return requestRoomList(pageIndex);
	}

	// ---------------------------------------room end

	// ---------------------------------------group

	/**
	 * 获取群详情
	 * 
	 * @param groupId
	 *            要获取群信息的对应群id
	 * @param forceRequest
	 *            该参数为true时返回当前缓存数据，并从服务器获取最新数据通过回调返回，若为false，当存在缓存时只返回缓存数据，
	 *            若不存在缓存则去服务器请求并通过回调返回
	 * @return 返回本地缓存群信息对象
	 */
	public GotyeGroup getGroupDetail(long groupId, boolean forceRequest) {
		String result = getTargetDetail(String.valueOf(groupId),
				GotyeChatTargetType.GotyeChatTargetTypeGroup.ordinal(),
				forceRequest);
		return GotyeGroup.createGroupJson(result);
	}

	/**
	 * 获取群详情
	 * 
	 * @param target
	 *            基础类型
	 * @param forceRequest
	 *            该参数为true时返回当前缓存数据，并从服务器获取最新数据通过回调返回，若为false，当存在缓存时只返回缓存数据，
	 *            若不存在缓存则去服务器请求并通过回调返回
	 * @return 返回本地缓存群信息对象
	 */
	public GotyeGroup getGroupDetail(GotyeChatTarget target, boolean forceRequest) {
		if(target.type==GotyeChatTargetType.GotyeChatTargetTypeGroup){
			String result = getTargetDetail(String.valueOf(target.Id),
					GotyeChatTargetType.GotyeChatTargetTypeGroup.ordinal(),
					forceRequest);
			return GotyeGroup.createGroupJson(result);
		}else{
			return null;
		}
		
	}
	
	
	
	
	/**
	 * 获取聊天室详情
	 * 
	 * @param roomId
	 *            对应聊天室Id
	 * @return 返回本地缓存聊天室信息对象
	 */
	public GotyeRoom getRoomDetail(long roomId) {
		String result = getTargetDetail(String.valueOf(roomId),
				GotyeChatTargetType.GotyeChatTargetTypeRoom.ordinal(),
				false);
		return GotyeRoom.createRoomJson(result);
	}
	
	/**
	 * 获取聊天室详情
	 * 
	 * @param target
	 *            基础类型
	 * @return 返回本地缓存聊天室信息对象
	 */
	public GotyeRoom getRoomDetail(GotyeChatTarget target) {
		if(target.type==GotyeChatTargetType.GotyeChatTargetTypeRoom){
			String result = getTargetDetail(String.valueOf(target.Id),
					GotyeChatTargetType.GotyeChatTargetTypeRoom.ordinal(),
					false);
			return GotyeRoom.createRoomJson(result);
		}else{
			return null;
		}
		
		
	}
	
	
	
	
	/**
	 * 请求修改群信息
	 * 
	 * @param group
	 *            要修改的群对象
	 * @param imagePath
	 *            新的群图标本地文件路径
	 * @return 请求状态
	 */
	public int reqModifyGroupInfo(GotyeGroup group, String imagePath) {
		return requestModifyGroupinfo(group.getGroupID(), group.getGroupName(),
				group.getInfo(), group.getOwnerType().ordinal(),
				group.isNeedAuthentication() ? 1 : 0, imagePath);
	}

	/**
	 * 请求离开群
	 * 
	 * @param group
	 *            当前群对象
	 * @return 请求状态
	 */
	public int leaveGroup(GotyeGroup group) {
		return leaveGroup(group.getGroupID());
	}

	/**
	 * 请求解散群
	 * 
	 * @param group
	 *            当前群对象
	 * @return 请求状态
	 */
	public int dismissGroup(GotyeGroup group) {
		return dismissGroup(group.getGroupID());
	}

	/**
	 * 请求踢人
	 * 
	 * @param group
	 *            当前群对象
	 * @param user
	 *            被踢出的群成员对象
	 * @return 请求状态
	 */
	public int kickoutGroupMember(GotyeGroup group, GotyeUser member) {
		return kickoutUser(group.getGroupID(), member.getName());
	}

	/**
	 * 从服务器获取当前登陆用户群列表
	 * 
	 * @return 请求状态
	 */
	public int reqGroupList() {
		return requestGrouplist();
	}

	/**
	 * 请求获取群成员列表
	 * 
	 * @param group
	 *            当前群对象
	 * @param pageIndex
	 *            页码（0开始）
	 * @return 请求状态
	 */
	public int reqGroupMemberList(GotyeGroup group, int pageIndex) {
		return requestGroupMemberlist(group.getGroupID(), pageIndex);
	}

	/**
	 * 要求好友入群
	 * 
	 * @param user
	 *            被邀请的用户
	 * @param group
	 *            当前群对象
	 * @param message
	 *            邀请信息
	 * @return 请求状态
	 */
	public int inviteUserToGroup(GotyeUser user, GotyeGroup group,
			String message) {
		return inviteUserTogroup(user.getName(), group.getGroupID(), message);
	}

	/**
	 * 加入群
	 * 
	 * @param group
	 *            当前群对象
	 * @return 请求状态
	 */
	public int joinGroup(GotyeGroup group) {
		return joinGroup(group.getGroupID());
	}

	/**
	 * 加入群
	 * 
	 * @param group
	 *            当前群对象
	 * @param reqMessage
	 *            请求附加信息
	 * @return 请求状态
	 */
	public int reqJoinGroup(GotyeGroup group, String reqMessage) {
		return requestJoinGroup(group.Id, reqMessage);
	}

	/**
	 * 处理入群申请
	 * 
	 * @param group
	 *            当前群
	 * @param agreeMsg
	 *            同意或拒绝信息
	 * @param isAgree
	 *            是否同意加入
	 * @return 请求状态
	 */
	public int replyJoinGroup(GotyeNotify notify, String agreeMsg,
			boolean isAgree) {
		return replyJoinGroup(notify.getSender().getName(), notify.getFrom()
				.getId(), agreeMsg, isAgree);
	}

	/**
	 * 请求变更群主
	 * 
	 * @param group
	 *            当前群对象
	 * @param user
	 *            请求变更的新群主
	 * @return 请求状态
	 */
	public int changeGroupowner(GotyeGroup group, GotyeUser user) {
		return changeGroupowner(group.getGroupID(), user.getName());
	}

	/**
	 * 开始录制语音消息
	 * 
	 * @param user 接收者
	 * @param mode
	 *            变声模式
	 * @param maxDuration
	 *            录制的最长时间(ms)
	 * @return 请求状态
	 */
	public int startTalk(GotyeUser user, WhineMode mode, int maxDuration) {
		return startTalk(user.name, user.type.ordinal(), mode.ordinal(), 0,
				maxDuration);
	}

	/**
	 * 开始录制语音消息
	 * 
	 * @param room 为对应聊天室
	 * @param mode
	 *            变声模式
	 * @param realtime
	 *            若为true时表示实时语音
	 * @param maxDuration
	 *            录制的最长时间(ms)
	 * @return 请求状态
	 */
	public int startTalk(GotyeRoom room, WhineMode mode, boolean realtime,
			int maxDuration) {
		String id = String.valueOf(room.Id);
		return startTalk(id, room.type.ordinal(), mode.ordinal(),
				realtime ? 1 : 0, maxDuration);
	}

	/**
	 * 开始录制语音消息
	 * 
	 * @param group 为对应群
	 * @param mode
	 *            变声模式
	 * @param maxDuration
	 *            录制的最长时间(ms)
	 * @return 请求状态
	 */
	public int startTalk(GotyeGroup group, WhineMode mode, int maxDuration) {
		String id = String.valueOf(group.Id);
		return startTalk(id, group.type.ordinal(), mode.ordinal(), 0,
				maxDuration);
	}
	/**
	 * 开始录制语音消息
	 * 
	 * @param target 对应接收者
	 * @param mode
	 *            变声模式
	 * @param realtime
	 *            若为true时表示实时语音
	 * @param maxDuration
	 *            录制的最长时间(ms)
	 * @return 请求状态
	 */
	public int startTalk(GotyeChatTarget target, WhineMode mode,boolean realtime, int maxDuration) {
		
		if(target.type==GotyeChatTargetType.GotyeChatTargetTypeUser){
			return startTalk(target.name, target.type.ordinal(), mode.ordinal(), realtime ? 1 : 0,
					maxDuration);
		}else{
			String id = String.valueOf(target.Id);
			return startTalk(id, target.type.ordinal(), mode.ordinal(), realtime ? 1 : 0,
					maxDuration);
		}
		
	}
	// ---------------------------------------end talk

	/**
	 * 删除会话
	 * 
	 * @param target
	 *            会话对象
	 */
	public void deleteSession(GotyeChatTarget target, boolean removeMessages) {

		if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			deleteSession(target.name, target.type.ordinal(), removeMessages);
		} else {
			deleteSession(String.valueOf(target.Id), target.type.ordinal(),
					removeMessages);
		}
		// session 变动
		sessionStateChanged();
		// GotyeNotifyManager.getInstance().onNotifyStateChanged();
	}

	/**
	 * 下载消息
	 * 
	 * @param message
	 *            被下载的消息对象
	 * @return 请求状态
	 */
	public int downloadMediaInMessage(GotyeMessage message) {
		if (message.getDbId() <= 0) {
			return GotyeStatusCode.CodeUnkonwnError;
		}
		return downloadMessage(message.getDbId());
	}

	// 会话列表变更通知
	private void sessionStateChanged() {
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof NotifyListener) {
				((NotifyListener) listener).onNotifyStateChanged();
			}
		}
	}

	/**
	 * 反编译音频消息（只能处理语音类型消息）
	 * 
	 * @param meessage
	 *            当前语音类型消息对象
	 * @return 请求状态
	 */
	public int decodeMessage(GotyeMessage message) {
		return decodeMessage(message.getDbId());
	}

	/**
	 * 启动或关闭敏感词过滤
	 * 
	 * @param type
	 *            聊天类型
	 * @param enabled
	 *            是否开启
	 */
	public void enableTextFilter(GotyeChatTargetType type, boolean enabled) {
		enableTextFilter(type.ordinal(), enabled ? 1 : 0);
	}

	static {
		//System.loadLibrary("gotye");
		System.loadLibrary("gotyeapi");
	}

	static void dispatchEvent(int eventCode, String json) {
		DispathEvent.dispatchEvent(eventCode, json);
	}

	static void dispatchAudioData(byte[] datas) {
		if (mListener != null) {
			mListener.onOutputAudioData(datas);
		}
	}

}
