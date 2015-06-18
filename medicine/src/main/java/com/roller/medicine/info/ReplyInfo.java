package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

public class ReplyInfo extends ResponseMessage {
	
	public String id;
	public String postId;
	public String replyId;
	public String content;
	public String floor;
	public String isPassed;
	public String disabled;
	public String byReplyUserId;
	public String replyUserId;
	public String createTime;
	public String replyCount;
	public String praiseCount;
	public String nickname;
	public String headImage;
	public String atName;
	public boolean praise;

	@Override
	public String toString() {
		return "KnowledgeQuizContentReplyListItemInfo [id=" + id + ", postId="
				+ postId + ", replyId=" + replyId + ", content=" + content
				+ ", floor=" + floor + ", isPassed=" + isPassed + ", disabled="
				+ disabled + ", byReplyUserId=" + byReplyUserId
				+ ", replyUserId=" + replyUserId + ", createTime=" + createTime
				+ ", replyCount=" + replyCount + ", praiseCount=" + praiseCount
				+ ", nickname=" + nickname + ", headImage=" + headImage
				+ ", atName=" + atName + ", praise=" + praise + "]";
	}
	
}
