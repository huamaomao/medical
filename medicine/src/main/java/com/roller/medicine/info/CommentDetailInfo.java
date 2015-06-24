package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

public class CommentDetailInfo extends ResponseMessage {

	public boolean isPraise;
	public String id;
	public String title;
	public String content;
	public String createTime;
	public String replyCount;
	public String praiseCount;
	public String source;
	public String replyId;
	public String typeId;
	public String createUserId;
	public String boardId;

	public List<ReplyInfo> replyList;
	public List<CommentInfo.Item> list;

	
	
}
