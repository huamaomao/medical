package com.gotye.api;

import java.io.Serializable;

/**
 * 聊天对象父类
 * @author gotye
 *
 */
public class GotyeChatTarget implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected GotyeChatTargetType type;
	

	/**
	 * 聊天室ID或群ID
	 */
	protected long Id;
	
	/**
	 * 用户名、聊天室名或群名
	 */
	protected String name;
	
	protected String info;
	
	
	protected boolean hasGotDetail;
	
	protected GotyeMedia icon;

	public long getId() {
		return Id;
	}

	public String getName() {
		return name;
	}
    
	public GotyeChatTargetType getType() {
		return type;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public GotyeMedia getIcon() {
		return icon;
	}

	public void setIcon(GotyeMedia icon) {
		this.icon = icon;
	}

	public boolean isHasGotDetail() {
		return hasGotDetail;
	}
	
	
	
}
