
package com.roller.medicine.utils;

import android.os.Environment;

import com.baoyz.widget.PullRefreshLayout;

public abstract class AppConstants {

    public static final String ROOT;
    public static final String PATH;
    public static final String VERSION="version";

    /***add**/
    public static final String USER_STATUS_ADD="0";
    /***接受**/
    public static final String USER_STATUS_INTRODUCE="1";

    /************参数传递************/
    public static final String DATA_TEL="tel";
    public static final String DATA_DATE="date";
    public static final String DATA_CODE="code";
    public static final String DATA="code";
    public static final String DATA_BOARD_ID="boardId";

    public static final int TYPE_COMMENT=1;

    public static final int CODE_PIC=1;
    public static final int CODE_CAPTURE=2;
    /**********/
    public static final int TYPE_MEDICINE=5;
    /*****短信重新发送时间60s*****/
    public static  final int SMS_SEBD_TIME=60000;

    /*****性别***/
    public static final String SEX_BOY="0";
    public static final String SEX_GIRL="1";

    public static  final String TYPE="type";
    public static  final String LIST="list";
    public static final String  POSITION="position";
    public static final String  ITEM="item";

    public static final String QINJIA_KEY="7c2c51ba-2be8-4e94-bcb0-38a6ddb65a51";
    public static final String ACTIVITY_ACTION="acitivty_exit";
    /***下路刷新样式***/
    public static final int PULL_STYLE= PullRefreshLayout.MODE_TOP;


    /******用户类型****/
    public static final String USER_TYPE_DOCTOR="3";
    public static final String USER_TYPE_DIETITAN="4";
    public static final String USER_TYPE_PATIENT="5";

    public static final String USER_ADD="76";
    public static final String USER_NO_ADD="77";

    public static final int CODE=200;

    /****73：点赞医生
     74：点赞帖子
     78：点赞回复****/
    public static final String  PRAISE_USER="73";
    public static final String  PRAISE_COMMENT="74";
    public static final String  PRAISE_REEPLY="78";

    public static final String APPID_WEIXIN="wxfe217123b08e4316";

    static {
        ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
        PATH = ROOT + "/com.roller.medicine/";
    }

}
