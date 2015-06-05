package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

public class KnowledgeQuizItemImageListInfo  extends ResponseMessage {
	
	private String id;
	private String imageName;
	private String url;
	public String getId() {
		if(id == null)return "";
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImageName() {
		if(imageName == null)return "";
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getUrl() {
		if(url == null)return "";
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "KnowledgeQuizItemImageListInfo [id=" + id + ", imageName="
				+ imageName + ", url=" + url + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KnowledgeQuizItemImageListInfo other = (KnowledgeQuizItemImageListInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imageName == null) {
			if (other.imageName != null)
				return false;
		} else if (!imageName.equals(other.imageName))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}
