package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class KnowledgeQuizItemInfo extends ResponseMessage {

	public List<Item> list;
   public int  pageNum;
 	public class Item{
		public String id;
		public String title;
		public String content;
		public String createTime;
		public String replyCount;
		public String praiseCount;
		public String praise;
		public String source;
		public String replyId;
		public String typeId;
		public String createUserId;
		public String boardId;
		public  List<Image> images;

		public class Image{
			public String url;
		}

	}

}
