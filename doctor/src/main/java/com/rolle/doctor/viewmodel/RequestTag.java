package com.rolle.doctor.viewmodel;

/**
 * @author Hua_
 * @Description: 请求标签
 * @date 2015/7/13 - 14:37
 */
public  interface RequestTag {
    static final String TAG="RequestTag";
    //家庭组
    static final int R_USER_UP=0x0001;
    //  好友。
    static final int R_USER_FRIEND=0x0002;
    //更新好友信息
    static final int R_UPD_USER=0x0003;

}
