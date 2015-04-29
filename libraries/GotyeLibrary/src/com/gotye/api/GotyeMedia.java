package com.gotye.api;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 多媒体对象
 *
 */
public class GotyeMedia implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GotyeMediaType type;
	
	private GotyeMediaStatus status;
	
	private String url;
	
	private String path;
	
	private String pathEx;
	
	private int duration;
	
	public GotyeMedia(){
		this.type=GotyeMediaType.GotyeMediaTypeImage;
	}
	public GotyeMedia(GotyeMediaType type){
		this.type=type;
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathEx() {
		return pathEx;
	}

	public void setPathEx(String path_ex) {
		this.pathEx = path_ex;
	}

	public GotyeMediaType getType() {
		return type;
	}

	public void setType(GotyeMediaType type) {
		this.type = type;
	}

	public GotyeMediaStatus getStatus() {
		return status;
	}

	public void setStatus(GotyeMediaStatus status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Media [duration=" + duration + ", path=" + path + ", path_ex="
				+ pathEx + ", type=" + type + "]";
	}

	public static GotyeMedia jsonToMedia(JSONObject obj) {
		if (obj == null) {
			return null;
		}
		GotyeMedia media = new GotyeMedia();
		media.duration = obj.optInt("duration");
		media.pathEx = obj.optString("path_ex");

		media.status =GotyeMediaStatus.values()[obj.optInt("status")]; // 0,1
												// downloading,2,down,3,shibai
		media.url = obj.optString("url");

		media.path = obj.optString("path");
		int type = obj.optInt("type");
		media.type = GotyeMediaType.values()[type];
		return media;
	}

}
