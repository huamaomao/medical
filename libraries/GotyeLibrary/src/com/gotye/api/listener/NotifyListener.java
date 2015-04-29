package com.gotye.api.listener;

import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeNotify;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;

/**
 * 推送相关监听
 *
 */
public interface NotifyListener extends GotyeListener {
	/**
	 * 收到或发送消息回调通知
	 * 
	 * @param message 消息对象
	 */
	void onReceivePushMessage(GotyeMessage message);

	/**
	 * 收到通知
	 * @param notify 通知对象
	 */
	void onReceiveNotify(GotyeNotify notify);

	/**
	 * 好友添加或删除通知
	 * 
	 * @param addOrRemove
	 *            true表示添加好友，false表示删除好友
	 * @param user
	 *            被添加或删除的好友
	 */
	void onFriendChanged(boolean addOrRemove, GotyeUser user);

	/**
	 * 通知变态变化通知，用于更新界面
	 */
	void onNotifyStateChanged();
}
