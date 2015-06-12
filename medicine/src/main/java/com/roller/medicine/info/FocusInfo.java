package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/6/11 - 15:35
 */
public class FocusInfo extends ResponseMessage {
    public List<Item> list;
    public class Item{
        public String id;
        public String nickname;
        public String headImage;
        public String typeId;
        public String sex;
        //76 add   77  is add
        public String statusId;
    }
}
