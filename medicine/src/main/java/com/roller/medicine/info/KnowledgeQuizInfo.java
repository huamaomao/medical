package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

public class KnowledgeQuizInfo extends ResponseMessage {

	private int pageNum;
	
	private List<KnowledgeQuizItemInfo> list;

	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public List<KnowledgeQuizItemInfo> getList() {
		return list;
	}

	public void setList(List<KnowledgeQuizItemInfo> list) {
		this.list = list;
	}
	
	
	
}
