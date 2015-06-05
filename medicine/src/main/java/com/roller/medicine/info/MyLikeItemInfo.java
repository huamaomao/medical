package com.roller.medicine.info;

public class MyLikeItemInfo {

	private String id;
	private String typeId;
	private String mainUserId;
	private String minorUserId;
	private String postId;
	private String replyId;
	private String createTime;
	private String headImage;
	private String nickname;
	private MyLikeReplyInfo reply;
	private MyLikePostInfo post;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getMainUserId() {
		return mainUserId;
	}
	public void setMainUserId(String mainUserId) {
		this.mainUserId = mainUserId;
	}
	public String getMinorUserId() {
		return minorUserId;
	}
	public void setMinorUserId(String minorUserId) {
		this.minorUserId = minorUserId;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public MyLikeReplyInfo getReply() {
		return reply;
	}
	public void setReply(MyLikeReplyInfo reply) {
		this.reply = reply;
	}
	public MyLikePostInfo getPost() {
		return post;
	}
	public void setPost(MyLikePostInfo post) {
		this.post = post;
	}
	@Override
	public String toString() {
		return "MyLikeItemInfo [id=" + id + ", typeId=" + typeId
				+ ", mainUserId=" + mainUserId + ", minorUserId=" + minorUserId
				+ ", postId=" + postId + ", replyId=" + replyId
				+ ", createTime=" + createTime + ", headImage=" + headImage
				+ ", nickname=" + nickname + ", reply=" + reply + ", post="
				+ post + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((headImage == null) ? 0 : headImage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((mainUserId == null) ? 0 : mainUserId.hashCode());
		result = prime * result
				+ ((minorUserId == null) ? 0 : minorUserId.hashCode());
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		result = prime * result + ((postId == null) ? 0 : postId.hashCode());
		result = prime * result + ((reply == null) ? 0 : reply.hashCode());
		result = prime * result + ((replyId == null) ? 0 : replyId.hashCode());
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
		MyLikeItemInfo other = (MyLikeItemInfo) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
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
		if (mainUserId == null) {
			if (other.mainUserId != null)
				return false;
		} else if (!mainUserId.equals(other.mainUserId))
			return false;
		if (minorUserId == null) {
			if (other.minorUserId != null)
				return false;
		} else if (!minorUserId.equals(other.minorUserId))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		if (reply == null) {
			if (other.reply != null)
				return false;
		} else if (!reply.equals(other.reply))
			return false;
		if (replyId == null) {
			if (other.replyId != null)
				return false;
		} else if (!replyId.equals(other.replyId))
			return false;
		if (typeId == null) {
			if (other.typeId != null)
				return false;
		} else if (!typeId.equals(other.typeId))
			return false;
		return true;
	}
	
	
	
	
	
	
}
