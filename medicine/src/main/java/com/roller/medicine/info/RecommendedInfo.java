package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

/**
 * @author Hua_
 * @Description: 评论
 * @date 2015/5/12 - 13:35
 */
public class RecommendedInfo extends ResponseMessage {

    public List<Item> list;

    public static class Item{
        public String nickname;
        public String headImage;
        public String mainUserId;
        public String minorUserId;
        public String createTime;
        public String typeId;
        public String content;

    }
}
