package com.rolle.doctor.util;


import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.Request;
import com.litesuits.http.request.content.HttpBody;
import com.litesuits.http.request.content.StringBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethod;

import java.util.ArrayList;
import java.util.List;

/***
 * 请求接口
 */
public final class RequestApi {

    /************** ----------------注册-----登陆-------------- ****************************/

    /******
     *@Description  请求验证码
     *@param : mobile
     *@return Request
     */
    public static Request requestTelCode(String mobile){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.TEL_CODE);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("mobile",mobile));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }

    /******
     *@Description  验证手机短信码
     *@param : mobile
     *@param   verifycode
     *@return Request
     */
    public static Request requestCheckTelCode(String mobile,String verifycode){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.CHECK_TEL_CODE);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("mobile",mobile));
        param.add(new NameValuePair("verifycode",verifycode));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }


    /******
     *@Description  注册用户
     *@param : mobile
     *@return Request
     */
    public static Request requestRegister(String mobile,String nickName,String password,String typeId){

        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.REGISTER);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("mobile",mobile));
        param.add(new NameValuePair("password",password));
        param.add(new NameValuePair("typeId",typeId));
        param.add(new NameValuePair("nickname",nickName));
        return new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
    }


    /******
     *  验证手机短信码
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



}
