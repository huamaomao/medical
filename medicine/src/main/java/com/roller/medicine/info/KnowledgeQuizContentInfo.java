package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

public class KnowledgeQuizContentInfo extends ResponseMessage {

	private String isPraise;
	public String id;
	private String title;
	private String content;
	private String createTime;
	private String replyCount;
	private String praiseCount;
	private String source;
	private String replyId;
	private String typeId;
	private String createUserId;
	private String boardId;
	private List<KnowledgeQuizItemInfo> list;
	private List<KnowledgeQuizContentReplyListItemInfo> replyList;
	private List<KnowledgeQuizItemImageListInfo> images;
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(String praiseCount) {
		this.praiseCount = praiseCount;
	}
	public String getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(String isPraise) {
		this.isPraise = isPraise;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<KnowledgeQuizItemInfo> getList() {
		return list;
	}
	public void setList(List<KnowledgeQuizItemInfo> list) {
		this.list = list;
	}
	public List<KnowledgeQuizContentReplyListItemInfo> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<KnowledgeQuizContentReplyListItemInfo> replyList) {
		this.replyList = replyList;
	}
	public List<KnowledgeQuizItemImageListInfo> getImages() {
		return images;
	}
	public void setImages(List<KnowledgeQuizItemImageListInfo> images) {
		this.images = images;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	@Override
	public String toString() {
		return "KnowledgeQuizContentInfo [isPraise=" + isPraise + ", id=" + id
				+ ", title=" + title + ", content=" + content + ", createTime="
				+ createTime + ", replyCount=" + replyCount + ", praiseCount="
				+ praiseCount + ", source=" + source + ", replyId=" + replyId
				+ ", typeId=" + typeId + ", createUserId=" + createUserId
				+ ", boardId=" + boardId + ", list=" + list + ", replyList="
				+ replyList + ", images=" + images + "]";
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
		result = prime * result
				+ ((isPraise == null) ? 0 : isPraise.hashCode());
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result
				+ ((praiseCount == null) ? 0 : praiseCount.hashCode());
		result = prime * result
				+ ((replyCount == null) ? 0 : replyCount.hashCode());
		result = prime * result + ((replyId == null) ? 0 : replyId.hashCode());
		result = prime * result
				+ ((replyList == null) ? 0 : replyList.hashCode());
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
		KnowledgeQuizContentInfo other = (KnowledgeQuizContentInfo) obj;
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
		if (isPraise == null) {
			if (other.isPraise != null)
				return false;
		} else if (!isPraise.equals(other.isPraise))
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
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
		if (replyList == null) {
			if (other.replyList != null)
				return false;
		} else if (!replyList.equals(other.replyList))
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
