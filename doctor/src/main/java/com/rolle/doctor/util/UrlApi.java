package com.rolle.doctor.util;

/**
 * Created by Hua_ on 2015/4/16.
 */
public interface UrlApi{
    static enum RequestUrl{
        S("http://rolle.cn:8080/crm/"),T("http://192.168.1.88:8080/crm/");
        String url;
        private RequestUrl(String url){
            this.url=url;
        }
    }
    public static final String SERVER_NAME=RequestUrl.T.url;
    /***验证码**/
    public static final String TEL_CODE="send_sp/sendVerifycode.json";
    /***验证手机号与验证码是否匹配**/
    //public static final String CHECK_TEL_CODE="verifycode_sp/checkVerifycode.json";
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
    /***填写邀请码**/
    public static final String USER_SAVE_CODE="invite_sp/saveInvite.json";
    /***城市***/
    public static final String CITY="region_sp/getRegionByParentId.json";
    /***职称***/
    public static final String TITLE="cd_sp/getCDByParentId.json";
    /***修改医生***/
    public static final String  UPD_DOCTOR_INFO="user_sp/saveDoctor.json";
    /***添加好友***/
    public static final String  ADD_FRIEND="user_sp/addFriendRelation.json";


    /***推荐投诉**/
    public static final String USER_RECOMMEND_REVIEW_COMPLAINTS_CODE="message_sp/getMessageListByMap.json";
    /***赞**/
    public static final String USER_PRAISE_CODE="praise_sp/getPraiseListByMap.json";
    /******血糖记录*********/
    public static final String BLOOD_LIST="glycemic_sp/getGlycemicDataByMap.json";


}
