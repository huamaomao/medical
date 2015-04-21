package com.rolle.doctor.util;

/**
 * Created by Hua_ on 2015/4/16.
 */
public interface UrlApi{
    static enum RequestUrl{
        S("http://192.168.1.88:8080/crm/"),T("http://192.168.1.88:8080/crm/");
        String url;
        private RequestUrl(String url){
            this.url=url;
        }
    }
    public static final String SERVER_NAME=RequestUrl.T.url;
    /***验证码**/
    public static final String TEL_CODE="send_sp/sendVerifycode.json";
    /***验证手机号与验证码是否匹配**/
    public static final String CHECK_TEL_CODE="verifycode_sp/checkVerifycode.json";
    /***注册**/
    public static final String REGISTER="user_sp/saveUser.json";
    /***登陆**/
    public static final String LOGIN="user_sp/login.json";
    /***获取个人信息**/
    public static final String USER_INFO="user_sp/getUserByToken.json";
    /***消息列表**/
    public static final String MESSAGE_LIST="user_sp/getUserListByToken.json";
    /***获取医生或患者列表**/
    public static final String USER_FRIEND_LIST="user_sp/getfriendList.json";
    /***获取患者数目**/
    public static final String USER_PATIENT_SUM="user_sp/getfriendSum.json";
    /***我的邀请码**/
    public static final String USER_INVITE_CODE="user_sp/getInviteCodeByToken.json";









}
