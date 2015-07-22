package com.rolle.doctor.util;

import com.baoyz.widget.PullRefreshLayout;

/**
 * @author Hua_
 * @Description: 常量
 * @date 2015/4/21 - 16:59
 */
public interface AppConstants {
    /*****短信重新发送时间60s*****/
    public static  final int SMS_SEBD_TIME=60000;

    /******用户类型****/
    public static final String USER_TYPE_DOCTOR="3";
    public static final String USER_TYPE_DIETITAN="4";
    public static final String USER_TYPE_PATIENT="5";

    public static final String KEY_UEMNG="55aefc2b67e58ebef4002b4e";

    public static final String APPID_WEIXIN="wx61795232350e45ae";
    public static final String APPID_WEIXIN_KEY="2f3e420d96d904d27a83b931f473d79c";
    public static final String APPID_QQ="1104566371";
    public static final String APPID_QQ_KEY="4y1xEtUfwPhCEjHq";

    public static  final String TYPE="type";
    public static  final String LIST="list";
    public static final String  POSITION="position";
    public static final String  ITEM="item";



    public static final String DB_NAME="doctor.db";


    /***下路刷新样式***/
    public static final int PULL_STYLE=PullRefreshLayout.MODE_TOP;


    public static final String ACTIVITY_ACTION="acitivty_exit";

    public static final int NO = -1;


    /*****性别***/
    public static final String SEX_BOY="0";
    public static final String SEX_GIRL="1";



    /***血糖值****/
    public static final double  MAX_BLOOD=12;
    public static final double  MIN_BLOOD=3.8;
    public static final String QINJIA_KEY="7c2c51ba-2be8-4e94-bcb0-38a6ddb65a51";

}
