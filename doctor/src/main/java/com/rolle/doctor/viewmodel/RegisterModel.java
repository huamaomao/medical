package com.rolle.doctor.viewmodel;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.MD5;
import com.android.common.viewmodel.ViewModel;
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
    public void  requestModel(String tel, final OnModelListener<ResponseMessage> listener,OnValidationListener onValidationListener){
       if (!CommonUtil.isMobileNO(tel)){
           if (CommonUtil.notNull(onValidationListener)){
               onValidationListener.errorTelNull();
           }
           return;
        }
        execute(RequestApi.requestTelCode(tel),listener);
    }

    /***
     *@Description 验证短信
     * @param tel
     * @param code
     * @param listener
     */
    public void  requestModel(String tel,String code, final OnModelListener<ResponseMessage> listener,OnCheckValidationListener onCheckValidationListener){
        if (!CommonUtil.isMobileNO(tel)){
            if (CommonUtil.notNull(onCheckValidationListener)){
                onCheckValidationListener.errorTelNull();
            }
            return;
        }else if(CommonUtil.isEmpty(code)){
            if (CommonUtil.notNull(onCheckValidationListener)){
                onCheckValidationListener.errorCodeNull();
            }
            return;
        }
        execute(RequestApi.requestCheckTelCode(tel, code),listener);
    }

    /*****
     * @Description  注册填写用户名
     * @param tel
     * @param verifycode
     * @param nickName
     * @param password
     * @param typeId
     * @param listener
     * @param onValidationListener
     */
    public void  requestModel(String tel,String verifycode,String nickName,String password,String typeId,final OnModelListener<ResponseMessage> listener,OnUserValidationListener onValidationListener){
        if (!CommonUtil.checkName(nickName)){
            onValidationListener.errorNickName();
            return;
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
        execute(RequestApi.requestRegister(tel,verifycode,nickName,password,typeId),listener);
    }


    public static  interface OnValidationListener{
        void errorTelNull();
    }

    public static  interface OnCheckValidationListener extends OnValidationListener{
        void errorCodeNull();
    }
    public static  interface OnUserValidationListener{
        void errorPwd();
        void errorNickName();
    }

}
