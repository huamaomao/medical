package com.roller.medicine.info;

public class MyLikeReplyInfo {

	private String id;
	private String postId;
	private String replyId;
	private String content;
	private String floor;
	private String isPassed;
	private String disabled;
	private String byReplyUserId;
	private String replyUserId;
	private String createTime;
	private String replyCount;
	private String praiseCount;
	private String nickname;
	private String atName;
	private String praise;
	private String headImage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
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
	public String getByReplyUserId() {
		return byReplyUserId;
	}
	public void setByReplyUserId(String byReplyUserId) {
		this.byReplyUserId = byReplyUserId;
	}
	public String getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAtName() {
		return atName;
	}
	public void setAtName(String atName) {
		this.atName = atName;
	}
	public String getPraise() {
		return praise;
	}
	public void setPraise(String praise) {
		this.praise = praise;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	@Override
	public String toString() {
		return "MyLikeReplyInfo [id=" + id + ", postId=" + postId
				+ ", replyId=" + replyId + ", content=" + content + ", floor="
				+ floor + ", isPassed=" + isPassed + ", disabled=" + disabled
				+ ", byReplyUserId=" + byReplyUserId + ", replyUserId="
				+ replyUserId + ", createTime=" + createTime + ", replyCount="
				+ replyCount + ", praiseCount=" + praiseCount + ", nickname="
				+ nickname + ", atName=" + atName + ", praise=" + praise
				+ ", headImage=" + headImage + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atName == null) ? 0 : atName.hashCode());
		result = prime * result
				+ ((byReplyUserId == null) ? 0 : byReplyUserId.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((floor == null) ? 0 : floor.hashCode());
		result = prime * result
				+ ((headImage == null) ? 0 : headImage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isPassed == null) ? 0 : isPassed.hashCode());
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((postId == null) ? 0 : postId.hashCode());
		result = prime * result + ((praise == null) ? 0 : praise.hashCode());
		result = prime * result
				+ ((praiseCount == null) ? 0 : praiseCount.hashCode());
		result = prime * result
				+ ((replyCount == null) ? 0 : replyCount.hashCode());
		result = prime * result + ((replyId == null) ? 0 : replyId.hashCode());
		result = prime * result
				+ ((replyUserId == null) ? 0 : replyUserId.hashCode());
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
		MyLikeReplyInfo other = (MyLikeReplyInfo) obj;
		if (atName == null) {
			if (other.atName != null)
				return false;
		} else if (!atName.equals(other.atName))
			return false;
		if (byReplyUserId == null) {
			if (other.byReplyUserId != null)
				return false;
		} else if (!byReplyUserId.equals(other.byReplyUserId))
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
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (floor == null) {
			if (other.floor != null)
				return false;
		} else if (!floor.equals(other.floor))
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
		if (isPassed == null) {
			if (other.isPassed != null)
				return false;
		} else if (!isPassed.equals(other.isPassed))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
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
		if (replyUserId == null) {
			if (other.replyUserId != null)
				return false;
		} else if (!replyUserId.equals(other.replyUserId))
			return false;
		return true;
	}
	
	
}
