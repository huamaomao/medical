package com.roller.medicine.info;

import java.util.List;

public class MyLikePostInfo {

	private String id;
	private String boardId;
	private String title;
	private String content;
	private String isTop;
	private String displayOrder;
	private String isPassed;
	private String disabled;
	private String createUserId;
	private String createTime;
	private String source;
	private String nickname;
	private String headImage;
	private String replyContent;
	private String replyId;
	private String replyCount;
	private String praiseCount;
	private String praise;
	private List<KnowledgeQuizItemImageListInfo> images;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsTop() {
		return isTop;
	}
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	public String getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getIsPassed() {
		return isPassed;
	}
	public void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public String getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}
	public String getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(String praiseCount) {
		this.praiseCount = praiseCount;
	}
	public String getPraise() {
		return praise;
	}
	public void setPraise(String praise) {
		this.praise = praise;
	}
	public List<KnowledgeQuizItemImageListInfo> getImages() {
		return images;
	}
	public void setImages(List<KnowledgeQuizItemImageListInfo> images) {
		this.images = images;
	}
	@Override
	public String toString() {
		return "MyLikePostInfo [id=" + id + ", boardId=" + boardId + ", title="
				+ title + ", content=" + content + ", isTop=" + isTop
				+ ", displayOrder=" + displayOrder + ", isPassed=" + isPassed
				+ ", disabled=" + disabled + ", createUserId=" + createUserId
				+ ", createTime=" + createTime + ", source=" + source
				+ ", nickname=" + nickname + ", headImage=" + headImage
				+ ", replyContent=" + replyContent + ", replyId=" + replyId
				+ ", replyCount=" + replyCount + ", praiseCount=" + praiseCount
				+ ", praise=" + praise + ", images=" + images + "]";
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
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result
				+ ((displayOrder == null) ? 0 : displayOrder.hashCode());
		result = prime * result
				+ ((headImage == null) ? 0 : headImage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((images == null) ? 0 : images.hashCode());
		result = prime * result
				+ ((isPassed == null) ? 0 : isPassed.hashCode());
		result = prime * result + ((isTop == null) ? 0 : isTop.hashCode());
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((praise == null) ? 0 : praise.hashCode());
		result = prime * result
				+ ((praiseCount == null) ? 0 : praiseCount.hashCode());
		result = prime * result
				+ ((replyContent == null) ? 0 : replyContent.hashCode());
		result = prime * result
				+ ((replyCount == null) ? 0 : replyCount.hashCode());
		result = prime * result + ((replyId == null) ? 0 : replyId.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		MyLikePostInfo other = (MyLikePostInfo) obj;
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
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (displayOrder == null) {
			if (other.displayOrder != null)
				return false;
		} else if (!displayOrder.equals(other.displayOrder))
			return false;
		if (headImage == null) {
			if (other.headImage != null)
				return false;
		} else if (!headImage.equals(other.headImage))
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
		if (isPassed == null) {
			if (other.isPassed != null)
				return false;
		} else if (!isPassed.equals(other.isPassed))
			return false;
		if (isTop == null) {
			if (other.isTop != null)
				return false;
		} else if (!isTop.equals(other.isTop))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
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
		if (replyContent == null) {
			if (other.replyContent != null)
				return false;
		} else if (!replyContent.equals(other.replyContent))
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
		return true;
	}
	
	
}
