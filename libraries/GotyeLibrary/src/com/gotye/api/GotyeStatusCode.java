package com.gotye.api;

/**
 * 操作及回调状态码
 *
 */
public class GotyeStatusCode {
	
	/**
	 * 异步调用成功，请等待回调
	 */
	public static final int CodeWaitingCallback = -1; // 异步调用成功，请等待回调
	/**
	 * 操作成功
	 */
	public static final int CodeOK = 0; // 操作成功
	
	/**
	 * 系统忙，正在处理中
	 */
	public static final int CodeSystemBusy = 1; // 系统忙，正在处理中
	
	
	/**
	 * 还未登陆
	 */
	public static final int CodeNotLoginYet = 2; // 还未登陆
	/**
	 * 创建文件失败
	 */
	public static final int CodeCreateFileFailed = 3; // 创建文件失败
	
	/**
	 * 目标是自己(已废除)
	 */
	public static final int CodeTargetIsSelf = 4; // 目标是自己(已废除)
	
	/**
	 * 重登陆成功
	 */
	public static final int CodeReloginOK = 5; // 重登陆成功
	
	/**
	 * 离线登陆模式成功
	 */
	public static final int CodeOfflineLoginOK = 6; // 离线登陆模式成功
	
	/**
	 * 超时
	 */
	public static final int CodeTimeout = 300; // 超时
	
	/**
	 * 验证失败
	 */
	public static final int CodeVerifyFailed = 400; // 验证失败
	
	/**
	 * 没有权限
	 */
	public static final int CodeNoPermission = 401; // 没有权限
	
	/**
	 * 重复操作
	 */
	public static final int CodeRepeatOper = 402; // 重复操作
	/**
	 * 群组未找到
	 */
	public static final int CodeGroupNotFound = 403; // 群组未找到
	
	/**
	 * 用户未找到
	 */
	public static final int CodeUserNotFound = 404; // 用户未找到
	
	/**
	 * 登录失败(此命名有误，应为操作失败)
	 */
	public static final int CodeLoginFailed = 500; // 登录失败(此命名有误，应为操作失败)
	
	/**
	 * 强制登出(被其他设备同一账号踢下线).
	 */
	public static final int CodeForceLogout = 600; // 强制登出(被其他设备同一账号踢下线).
	/**
	 * 网络连接断开
	 */
	public static final int CodeNetworkDisConnected = 700; // 网络连接断开
	
	/**
	 * 聊天室不存在
	 */
	public static final int CodeRoomNotExist = 33; // 聊天室不存在
	/**
	 * 聊天室人数已满
	 */
	public static final int CodeRoomIsFull = 34; // 聊天室人数已满
	
	/**
	 * 不在聊天室内
	 */
	public static final int CodeNotInRoom = 35; // 不在聊天室内
	
	/**
	 * 操作禁止
	 */
	public static final int CodeForbidden = 36; // 操作禁止
	
	/**
	 * 已经在聊天室中
	 */
	public static final int CodeAlreadyInRoom = 39; // 已经在聊天室中
	
	/**
	 * 用户不存在
	 */
	public static final int CodeUserNotExist = 804; // 用户不存在
	/**
	 * 请求Mic失败
	 */
	public static final int CodeRequestMicFailed = 806; // 请求Mic失败
	
	/**
	 * 录音时间超出
	 */
	public static final int CodeVoiceTimeOver = 807; // 录音时间超出
	
	/**
	 * 录音设备正在使用
	 */
	public static final int CodeRecorderBusy = 808; // 录音设备正在使用
	
	/**
	 * 录音时间过短 <1000ms
	 */
	public static final int CodeVoiceTooShort = 809; // 录音时间过短 <1000ms
																 
	/**
	 * 有不合法参数
	 */
	public static final int CodeInvalidArgument = 1000; // 有不合法参数
	
	/**
	 * 服务器处理失败(可能含有非法参数)
	 */
	public static final int CodeServerProcessError = 1001; // 服务器处理失败(可能含有非法参数)
	/**
	 *  操作数据库失败.
	 */
	public static final int CodeDBError = 1002; // 操作数据库失败.
	/**
	 * 未知错误
	 */
	public static final int CodeUnkonwnError = 1100; // 未知错误
}
