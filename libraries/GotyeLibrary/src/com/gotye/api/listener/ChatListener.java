package com.gotye.api.listener;

import java.util.List;

import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeStatusCode;

/**
 *  聊天相关监听
 * 
 *
 */
public interface ChatListener extends GotyeListener {
	/**
	 * 发送消息后回调
	 * @param code 状态码 参见 {@link GotyeStatusCode}
	 * @param message 被发送的消息对象
	 */
	void onSendMessage(int code, GotyeMessage message);

	/**
	 * 接收消息回调
	 * @param message 接收到的消息
	 */
	void onReceiveMessage(GotyeMessage message);

	/**
	 * 下载消息回调
	 * @param code 状态码 参见 {@link GotyeStatusCode}
	 * @param message 下载的消息对象
	 */
	void onDownloadMediaInMessage(int code, GotyeMessage message);

	/**
	 * 举报回调
	 * @param code 状态码 参见 {@link GotyeStatusCode}
	 * @param message 被举报的消息
	 */
	void onReport(int code, GotyeMessage message);

	/**
	 * 开始录制语音消息回调
	 * @param code 状态码 参见 {@link GotyeStatusCode}
	 * @param isRealTime 是否实时语音
	 * @param targetType 发送对象类型
	 * @param target  发送对象
	 */
	void onStartTalk(int code, boolean isRealTime, int targetType,
			GotyeChatTarget target);

	/**
	 * 停止录制语音消息回调
	 * @param code  状态码 参见 {@link GotyeStatusCode}
	 * @param message
	 * @param isVoiceReal
	 */
	void onStopTalk(int code, GotyeMessage message, boolean isVoiceReal);

	/**
	 * 解码语音消息
	 * @param code 状态码 参见 {@link GotyeStatusCode}
	 * @param message 被解码的消息对象
	 */
	void onDecodeMessage(int code, GotyeMessage message);
	/**
	 * 获取历史消息回调
	 * @param code 状态码 参见 {@link GotyeStatusCode}
	 * @param list 历史消息列表
	 */
	void onGetMessageList(int code, List<GotyeMessage> list);
	
	void onOutputAudioData(byte[] datas);

}
