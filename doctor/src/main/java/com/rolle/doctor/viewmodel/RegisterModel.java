package com.rolle.doctor.viewmodel;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.MD5;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.util.RequestApi;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * @author Hua
 * @Description:
 * @date 2015/4/17 - 12:23
 */
public class RegisterModel extends ViewModel{


    /***
     *@Description 发送短信
     * @param tel
     * @param listener
     */
    public void  requestModel(String tel, final HttpModelHandler<String> listener,OnValidationListener onValidationListener){
       if (!CommonUtil.isMobileNO(tel)){
           if (CommonUtil.notNull(onValidationListener)){
               onValidationListener.errorTelNull();
           }
           return;
        }
        execute(RequestApi.requestTelCode(tel),listener);
    }



    /*****
     * @Description  注册填写用户名
     * @param tel
     * @param verifycode
     * @param password
     * @param listener
     * @param onValidationListener
     */
    public void  requestRegister(String tel,String verifycode,String password,final HttpModelHandler<String> listener,OnUserValidationListener onValidationListener){
        if (CommonUtil.isEmpty(verifycode)||verifycode.length()<6){
            onValidationListener.errorCode();
        }
        if (!CommonUtil.checkPassword(password)){
            onValidationListener.errorPwd();
            return;
        }
        password= MD5.compute(password);
        if (CommonUtil.isEmpty(password)){
            onValidationListener.errorPwd();
            return;
        }
        execute(RequestApi.requestRegister(tel,verifycode,password),listener);
    }


    public static  interface OnValidationListener{
        void errorTelNull();
    }

    public static  interface OnUserValidationListener{
        void errorPwd();
        void errorCode();
    }

}
