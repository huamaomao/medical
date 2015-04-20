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
        url.append(UrlApi.TEL_CODE).append("?").append("mobile=").append(mobile);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("mobile",mobile));
        return new Request(url.toString()).setMethod(HttpMethod.Get).setHttpBody(new UrlEncodedFormBody(param));
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
        return new Request(url.toString()).setMethod(HttpMethod.Get).setHttpBody(new UrlEncodedFormBody(param));
    }


    /******
     *@Description  注册用户
     *@param : mobile
     *@param   verifycode
     *@return Request
     */
    public static Request requestRegister(String mobile,String verifycode,String nickName,String password,String typeId){

        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.REGISTER);
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("mobile",mobile));
        param.add(new NameValuePair("password",password));
        param.add(new NameValuePair("typeId",typeId));
        param.add(new NameValuePair("verifycode",verifycode));
        param.add(new NameValuePair("nickname",nickName));
        return new Request(url.toString()).setMethod(HttpMethod.Get).setHttpBody(new UrlEncodedFormBody(param));
    }




}
