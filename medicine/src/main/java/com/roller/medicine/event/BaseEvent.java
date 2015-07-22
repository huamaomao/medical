package com.roller.medicine.event;

/**
 * @author Hua_
 * @Description:
 * @date 2015/7/8 - 10:33
 */
public  class BaseEvent {
     public int type;
    public BaseEvent(int type) {
        this.type = type;
    }
    public BaseEvent() {
    }


    //家庭组 有更新
   public static  final int EV_FAMILY=0X0001;

}
