package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class KnowledgeQuizItemInfo extends ResponseMessage {

	private String id;
	private String title;
	private String content;
	private String createTime;
	private String replyCount;
	private String praiseCount;
	private String praise;
	private String source;
	private String replyId;
	private String typeId;
	private String createUserId;
	private String boardId;
	private List<KnowledgeQuizItemImageListInfo> images;
	
	
	
	public String getBoardId() {
		if(boardId == null)return "";
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getSource() {
		if(source == null)return "";
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getReplyId() {
		if(replyId == null)return "";
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public String getTypeId() {
		if(typeId == null)return "";
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getCreateUserId() {
		if(createUserId == null)return "";
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getId() {
		if(id == null)return "";
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		if(title == null)return "";
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		if(content == null)return "";
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		if(createTime == null)return "";
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReplyCount() {
		if(replyCount == null)return "";
		return replyCount;
	}
	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}
	public String getPraiseCount() {
		if(praiseCount == null)return "";
		return praiseCount;
	}
	public void setPraiseCount(String praiseCount) {
		this.praiseCount = praiseCount;
	}
	public String getPraise() {
		if(praise == null)return "";
		return praise;
	}
	public void setPraise(String praise) {
		this.praise = praise;
	}
	public List<KnowledgeQuizItemImageListInfo> getImages() {
		if(images == null)return new LinkedList<KnowledgeQuizItemImageListInfo>();
		return images;
	}
	public void setImages(List<KnowledgeQuizItemImageListInfo> images) {
		this.images = images;
	}
	@Override
	public String toString() {
		return "KnowledgeQuizItemInfo [id=" + id + ", title=" + title
				+ ", content=" + content + ", createTime=" + createTime
				+ ", replyCount=" + replyCount + ", praiseCount=" + praiseCount
				+ ", praise=" + praise + ", source=" + source + ", replyId="
				+ replyId + ", typeId=" + typeId + ", createUserId="
				+ createUserId + ", boardId=" + boardId + ", images=" + images
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardId == null) ? 0 : boardId.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((createUserId == null) ? 0 : createUserId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((images == null) ? 0 : images.hashCode());
		result = prime * result + ((praise == null) ? 0 : praise.hashCode());
		result = prime * result
				+ ((praiseCount == null) ? 0 : praiseCount.hashCode());
		result = prime * result
				+ ((replyCount == null) ? 0 : replyCount.hashCode());
		result = prime * result + ((replyId == null) ? 0 : replyId.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
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
		KnowledgeQuizItemInfo other = (KnowledgeQuizItemInfo) obj;
		if (boardId == null) {
			if (other.boardId != null)
				return false;
		} else if (!boardId.equals(other.boardId))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (createUserId == null) {
			if (other.createUserId != null)
				return false;
		} else if (!createUserId.equals(other.createUserId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (images == null) {
			if (other.images != null)
				return false;
		} else if (!images.equals(other.images))
			return false;
		if (praise == null) {
			if (other.praise != null)
				return false;
		} else if (!praise.equals(other.praise))
			return false;
		if (praiseCount == null) {
			if (other.praiseCount != null)
				return false;
		} else if (!praiseCount.equals(other.praiseCount))
			return false;
		if (replyCount == null) {
			if (other.replyCount != null)
				return false;
		} else if (!replyCount.equals(other.replyCount))
			return false;
		if (replyId == null) {
			if (other.replyId != null)
				return false;
		} else if (!replyId.equals(other.replyId))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (typeId == null) {
			if (other.typeId != null)
				return false;
		} else if (!typeId.equals(other.typeId))
			return false;
		return true;
	}
	
	
	
	
}
