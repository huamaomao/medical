package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

public class MyFocusFansCommentsItemInfo extends ResponseMessage {
	
	private String headImage;
	private String nickname;	
	private String typeId;
	private String sex;
	private String praise;
	public String id;
	private String title;
	private String content;
	private String createTime;
	private String replyCount;
	private String praiseCount;
	private String source;
	private String replyId;
	private String createUserId;
	private String boardId;
	private String replyContent;
	private List<KnowledgeQuizItemImageListInfo> images;
	
	
	
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getPraise() {
		return praise;
	}
	public void setPraise(String praise) {
		this.praise = praise;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
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
	public List<KnowledgeQuizItemImageListInfo> getImages() {
		return images;
	}
	public void setImages(List<KnowledgeQuizItemImageListInfo> images) {
		this.images = images;
	}
	public String getHeadImage() {
		if(headImage == null){
			return "";
		}
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getNickname() {
		if(nickname == null){
			return "";
		}
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTypeId() {
		if(typeId == null){
			return "";
		}
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getSex() {
		if(sex == null){
			return "0";
		}
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "MyFocusFansCommentsItemInfo [headImage=" + headImage
				+ ", nickname=" + nickname + ", typeId=" + typeId + ", sex="
				+ sex + ", praise=" + praise + ", id=" + id + ", title="
				+ title + ", content=" + content + ", createTime=" + createTime
				+ ", replyCount=" + replyCount + ", praiseCount=" + praiseCount
				+ ", source=" + source + ", replyId=" + replyId
				+ ", createUserId=" + createUserId + ", boardId=" + boardId
				+ ", images=" + images + "]";
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
				+ ((headImage == null) ? 0 : headImage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((images == null) ? 0 : images.hashCode());
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((praise == null) ? 0 : praise.hashCode());
		result = prime * result
				+ ((praiseCount == null) ? 0 : praiseCount.hashCode());
		result = prime * result
				+ ((replyCount == null) ? 0 : replyCount.hashCode());
		result = prime * result + ((replyId == null) ? 0 : replyId.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
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
		MyFocusFansCommentsItemInfo other = (MyFocusFansCommentsItemInfo) obj;
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
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
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