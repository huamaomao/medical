package com.rolle.doctor.event;

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


    //user 有更新
   public static  final int EV_USER_INFO=0X0001;
    //token 过时
   public static  final int EV_TOKEN_OUT=0X00f1;
    //  好友  ...
   public static  final int EV_USER_FRIEND=0X0002;



}
