package com.gotye.api;

public enum GotyeMessageStatus {
	/**
	 * 默认状态
	 */
	GotyeMessageStatusCreated, 
	/**
	 * 未读状态
	 */
    GotyeMessageStatusUnread,
    /**
     * 已读状态
     */
    GotyeMessageStatusRead,
    /**
     * 发送中
     */
    GotyeMessageStatusSending,
    /**
     * 已发送成功
     */
    GotyeMessageStatusSent,
    /**
     * 发送失败
     */
    GotyeMessageStatusSendingFailed
}
