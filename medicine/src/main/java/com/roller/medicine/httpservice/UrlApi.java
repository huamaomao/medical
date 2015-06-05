package com.roller.medicine.httpservice;

/**
 * Created by Hua_ on 2015/4/16.
 */
public interface UrlApi {
    static enum RequestUrl{
        S("http://rolle.cn:8080"),T("http://192.168.1.88:8080");
        String url;
        RequestUrl(String url){
            this.url=url;
        }
    }
    public static final String SERVER_NAME= RequestUrl.S.url;
    /***验证码**/
    public static final String TEL_CODE="/crm/send_sp/sendVerifycode.json";
    /***验证手机号与验证码是否匹配**/
    public static final String CHECK_TEL_CODE="/crm/verifycode_sp/checkVerifycode.json";

    /***获取个人信息**/
    public static final String USER_INFO="/crm/user_sp/getUserByToken.json";
    /***修改***/
    public static final String  UPD_DOCTOR_INFO="/crm/user_sp/saveDoctor.json";
    /***注册***/
    public static final String  SAVE_USER_INFO="/crm/user_sp/saveUser.json";
    /***login***/
    public static final String  LOGIN="/crm/user_sp/login.json";
    public static final String  PASSWORD="/crm/user_sp/updatePassword.json";
    /***获取医生或患者列表**/
    public static final String FRIEND_LIST="/crm/user_sp/getfriendList.json";
    /***add friend**/
    public static final String FRIEND_ADD="/crm/user_sp/addFriendRelation.json";
    /***add friend**/
    public static final String FRIEND_ADD_ID="/crm/relation_sp/saveRelationByUserId.json";
    public static final String SAVE_PRAISE="/crm/praise_sp/savePraise.json";

}
