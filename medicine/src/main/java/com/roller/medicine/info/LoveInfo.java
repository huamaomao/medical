package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hua_
 * @Description: 喜欢
 * @date 2015/6/11 - 15:17
 */
public class LoveInfo extends ResponseMessage{
    public List<Item> list;
    public  class Item{
        public String id;
        public String typeId;
        public String createTime;
        public PostInfo post;
    }

}
