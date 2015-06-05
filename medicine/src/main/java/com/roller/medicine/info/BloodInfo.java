package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

/**
 * @author Hua_
 * @Description: 血糖记录
 * @date 2015/4/28 - 9:42
 */
public class BloodInfo extends ResponseMessage {
    /***偏低次数*****/
    public String lowSum;
    /****正常次数****/
    public String normalSum;
    /***偏高次数*****/
    public String highSum;
    public List<Item> list;

    public static class Item{
        public Long createTime;
        public String day;
        /****凌晨*******/
        public String morningNum;
        /****早餐前*******/
        public String breakfastStart;
        /****早餐后*******/
        public String breakfastEnd;
        /****中餐前*******/
        public String chineseFoodStart;
        /****中餐后*******/
        public String chineseFoodEnd;
        /****晚餐前*******/
        public String dinnerStart;
        /****晚餐后*******/
        public String dinnerEnd;
        /****睡前*******/
        public String beforeGoingToBed;
    }
}
