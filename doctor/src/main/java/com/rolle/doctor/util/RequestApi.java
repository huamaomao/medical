package com.rolle.doctor.util;


import com.android.common.util.Log;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.Request;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethod;
import com.rolle.doctor.domain.User;

import java.util.ArrayList;
import java.util.List;

/***
 * 请求接口
 */
public final class RequestApi {

    public static String getImageUrl(String path){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(path);
        Log.d(url.toString());
        return url.toString();
    }

    /************** ----------------注册-----登陆-------------- ****************************/

    /******
     *@Description  请求验证码
     * 83	注册短信
    84	找回密码短信
     *@param : mobile
     *@param : type
     *@return Request
     */
    public static Request requestTelCode(String mobile,String type){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.TEL_CODE);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("mobile",mobile));
        param.add(new NameValuePair("typeId",type));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }


    /******
     *@Description  注册用户
     *@param : mobile
     *@return Request
     */
    public static Request requestRegister(String mobile,String verifycode,String password){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.REGISTER);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("mobile",mobile));
        param.add(new NameValuePair("password", password));
        param.add(new NameValuePair("verifycode",verifycode));
        param.add(new NameValuePair("appType", AppConstants.USER_TYPE_DOCTOR));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }


    /******
     *  login
     * @param mobile
     * @param password
     * @return
     */
    public static Request requestLogin(String mobile,String password){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.LOGIN);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("mobile",mobile));
        param.add(new NameValuePair("password",password));
        param.add(new NameValuePair("appType", AppConstants.USER_TYPE_DOCTOR));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }

    /******
     *  获取个人信息
     * @param token
     * @return
     */
    public static Request requestUserInfo(String token){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.USER_INFO);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",token));
        return new Request(url.toString()).setMethod(HttpMethod.Get).setHttpBody(new UrlEncodedFormBody(param));
    }

    /******
     *  消息列表
     * @param token
     * @return
     */
    public static Request requestMessageList(String token){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.MESSAGE_LIST);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",token));
        return new Request(url.toString()).setMethod(HttpMethod.Get).setHttpBody(new UrlEncodedFormBody(param));
    }

    /******
     *  获取医生或患者列表
     * @param token
     * @param type
     * @return
     */
    public static Request requestFriendList(String token,String type){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.USER_FRIEND_LIST);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",token));
        param.add(new NameValuePair("typeId",type));
        return new Request(url.toString()).setMethod(HttpMethod.Get).setHttpBody(new UrlEncodedFormBody(param));
    }

    /**
     * 获取评论 投诉
     * @param token
     * @param type
     * @return
     */
    public static Request requestRecommendReviewComplaints(String token,String type){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.USER_RECOMMEND_REVIEW_COMPLAINTS_CODE);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",token));
        param.add(new NameValuePair("typeId",type));
        return new Request(url.toString()).setMethod(HttpMethod.Get).setHttpBody(new UrlEncodedFormBody(param));
    }
    /**
     * 赞
     * @param token
     * @return
     */
    public static Request requestPraise(String token){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.USER_PRAISE_CODE);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",token));
        return new Request(url.toString()).setMethod(HttpMethod.Get).setHttpBody(new UrlEncodedFormBody(param));
    }
    /******
     *  患者数目
     * @param token
     * @param type
     * @return
     */
    public static Request requestPatientNum(String token,String type){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.USER_PATIENT_SUM);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",token));
        param.add(new NameValuePair("typeId",type));
        return new Request(url.toString()).setMethod(HttpMethod.Get).setHttpBody(new UrlEncodedFormBody(param));
    }

    /******
     *   我的邀请码
     * @param token
     * @return
     */
    public static Request requestUserInviteCode(String token){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.USER_INVITE_CODE);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",token));
        return new Request(url.toString()).setMethod(HttpMethod.Get).setHttpBody(new UrlEncodedFormBody(param));
    }


    /******
     *   填写邀请码
     * @param token
     * @param inviteCode
     * @return
     */
    public static Request requestSaveInviteCode(String token,String inviteCode){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.USER_SAVE_CODE);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",token));
        param.add(new NameValuePair("inviteCode",inviteCode));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }


    /******
     *   获取省会 城市
     * @param parentId
     * @return
     */
    public static Request requestCity(String parentId){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.CITY);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("parentId",parentId));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }

    /******
     *   获取职称
     * @param parentId
     * @return
     */
    public static Request requestTitle(String parentId){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.TITLE);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("parentId",parentId));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }


    /******
     *   修改医生
     * @param user
     * @return
     */
    public static Request requestUpdUser(User user){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.REGISTER);
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("typeId",user.typeId));
        param.add(new NameValuePair("nickname",user.nickname));
        param.add(new NameValuePair("email",user.email));
        param.add(new NameValuePair("tel",user.tel));
        param.add(new NameValuePair("sex",user.sex));
        param.add(new NameValuePair("age",user.age));
        param.add(new NameValuePair("photoId",user.photoId));
        param.add(new NameValuePair("intro",user.intro));
        param.add(new NameValuePair("address",user.address));
        param.add(new NameValuePair("userName",user.userName));
        param.add(new NameValuePair("token",user.token));
        param.add(new NameValuePair("birthday",user.birthday));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }

    /******
     *   修改医生
     * @param user
     * @return
     */
    public static Request requestUpdDoctor(User user){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.UPD_DOCTOR_INFO);
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("typeId",user.typeId));
        param.add(new NameValuePair("workAddress",user.doctorDetail.workAddress));
        param.add(new NameValuePair("workRegionId",user.doctorDetail.workRegionId));
        param.add(new NameValuePair("hospitalName ",user.doctorDetail.hospitalName));
        param.add(new NameValuePair("jobId",user.doctorDetail.jobId));
        param.add(new NameValuePair("token",user.token));
        param.add(new NameValuePair("departmentsId",user.doctorDetail.departmentsId));
        param.add(new NameValuePair("speciality",user.doctorDetail.speciality));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }

    /******
     *   添加好友
     * @param token
     * @param userId
     * @param noteName
     * @return
     */
    public static Request requestAddFriend(String token,String userId,String noteName ){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.ADD_FRIEND);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",token));
        param.add(new NameValuePair("mobile",userId));
        param.add(new NameValuePair("noteName",noteName));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }

}
