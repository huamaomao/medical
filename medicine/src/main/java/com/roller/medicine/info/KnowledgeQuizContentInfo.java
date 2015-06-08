package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

public class KnowledgeQuizContentInfo extends ResponseMessage {

	public String isPraise;
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
	public List<KnowledgeQuizItemInfo> list;
	public List<KnowledgeQuizContentReplyListItemInfo> replyList;
	public List<KnowledgeQuizItemImageListInfo> images;

	
	
}
