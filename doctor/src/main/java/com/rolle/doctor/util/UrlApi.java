package com.rolle.doctor.util;

/**
 * Created by Hua_ on 2015/4/16.
 */
public interface UrlApi{
    enum RequestUrl{
        S("http://rolle.cn:8080"),T("http://192.168.1.88"),D("http://192.168.1.88:8080");
        String url;
        RequestUrl(String url){
            this.url=url;
        }
    }
    public static final String SERVER_NAME=RequestUrl.D.url;

    /***验证码**/
    public static final String TEL_CODE="/crm/send_sp/sendVerifycode.json";

    /***注册**/
    public static final String REGISTER="/crm/user_sp/saveUser.json";
    /***登陆**/
    public static final String LOGIN="/crm/user_sp/login.json";
    /***获取个人信息**/
    public static final String USER_INFO="/crm/user_sp/getUserByToken.json";
    /***消息列表**/
    public static final String MESSAGE_LIST="/crm/user_sp/getUserListByToken.json";
    /***获取医生或患者列表**/
    public static final String USER_FRIEND_LIST="/crm/user_sp/getfriendList.json";
    /***获取患者数目**/
    public static final String USER_PATIENT_SUM="/crm/user_sp/getfriendSum.json";
    /***我的邀请码**/
    public static final String USER_INVITE_CODE="/crm/user_sp/getInviteCodeByToken.json";
    /***填写邀请码**/
    public static final String USER_SAVE_CODE="/crm/invite_sp/saveInvite.json";
    /***城市***/
    public static final String CITY="/crm/region_sp/getRegionByParentId.json";
    /***职称***/
    public static final String TITLE="/crm/cd_sp/getCDByParentId.json";
    /***修改医生****/
    public static final String  UPD_DOCTOR_INFO="/crm/user_sp/saveDoctor.json";
    /***添加好友***/
    public static final String  ADD_FRIEND="/crm/user_sp/addFriendRelation.json";
    public static final String FRIEND_ADD_ID="/crm/relation_sp/saveRelationByUserId.json";

    /***推荐投诉**/
    public static final String USER_RECOMMEND_REVIEW_COMPLAINTS_CODE="/crm/message_sp/getMessageListByMap.json";
    /***赞**/
    public static final String USER_PRAISE_CODE="/crm/praise_sp/getPraiseListByMap.json";
    /******血糖记录*********/
    public static final String BLOOD_LIST="/crm/glycemic_sp/getGlycemicDataByMap.json";

}
