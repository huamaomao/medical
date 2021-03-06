package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/6/11 - 14:35
 */
public class CommentInfo extends ResponseMessage {
    public List<Item> list;
    public static class Item{
        public String id;
        public String title;
        public String postId;
        public String content;
        public String replyUserId;
        public String createTime;
        public String nickname;
        public String headImage;
        public String replyContent;
        public String replyId;
        public String praiseCount;
        public String replyCount;
        public String postContent;
        public String praise;
        public String source;
        public String imageUrl;
        public List<ImageInfo> images;
    }

}
