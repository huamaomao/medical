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
    public static final String  ITEM="item";



    public static final String DB_NAME="doctor.db";

    public static final String QINJIA_KEY="7c2c51ba-2be8-4e94-bcb0-38a6ddb65a51";

    /***下路刷新样式***/
    public static final int PULL_STYLE=PullRefreshLayout.STYLE_WATER_DROP;


    public static final String ACTIVITY_ACTION="acitivty_exit";

    public static final int NO = -1;


    public static final int CODE_PIC=1;
    public static final int CODE_PONTO=2;


    /***血糖值****/
    public static final double  MAX_BLOOD=12;
    public static final double  MIN_BLOOD=3.8;

}
