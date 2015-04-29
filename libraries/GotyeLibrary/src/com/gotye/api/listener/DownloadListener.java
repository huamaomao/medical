package com.gotye.api.listener;

import com.gotye.api.GotyeMedia;
import com.gotye.api.GotyeStatusCode;
/**
 *资源下载监听
 */
public interface DownloadListener  extends GotyeListener{
	/**
	 * 下载图片回调
	 * @param code 状态码 参见 {@link GotyeStatusCode}
	 * @param media 下载的媒体消息
	 */
	  void onDownloadMedia(int code,GotyeMedia media);
}
