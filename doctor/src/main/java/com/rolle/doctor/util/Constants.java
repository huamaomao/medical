package com.rolle.doctor.util;

import com.baoyz.widget.PullRefreshLayout;

/**
 * @author Hua_
 * @Description: 常量
 * @date 2015/4/21 - 16:59
 */
public interface Constants {
    /*****短信重新发送时间60s*****/
    public static  final int SMS_SEBD_TIME=60000;

    /******用户类型****/
    public static final String USER_TYPE_DOCTOR="3";
    public static final String USER_TYPE_DIETITAN="4";
    public static final String USER_TYPE_PATIENT="5";


    public static  final String TYPE="type";
    public static  final String LIST="list";
    public static final String  POSITION="position";



    public static final String DB_NAME="doctor.db";

    public static final String QINJIA_KEY="5182ca36-9179-46f0-a520-3b474a8c8f91";

    /***下路刷新样式***/
    public static final int PULL_STYLE=PullRefreshLayout.STYLE_WATER_DROP;


    public static final String ACTIVITY_ACTION="acitivty_exit";

}
