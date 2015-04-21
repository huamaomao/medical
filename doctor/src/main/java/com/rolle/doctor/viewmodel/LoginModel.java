package com.rolle.doctor.viewmodel;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.MD5;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.util.RequestApi;

/**
 * @author Hua
 * @Description:
 * @date 2015/4/17 - 12:23
 */
public class LoginModel extends ViewModel{
    /***
     *@Description 发送短信
     * @param tel
     * @param listener
     */
    public void  requestModel(String tel,String pwd,final HttpModelHandler<String> listener,OnValidationListener onValidationListener){
       if (!CommonUtil.isMobileNO(tel)){
           if (CommonUtil.notNull(onValidationListener)){
               onValidationListener.errorTel();
           }
           return;
        }
        if (CommonUtil.isEmpty(pwd)||pwd.length()<6||pwd.length()>15){
            onValidationListener.errorPwd();
            return;
        }
        execute(RequestApi.requestLogin(tel,MD5.compute(pwd)),listener);
    }



    public static  interface OnValidationListener{
        void errorTel();
        void errorPwd();
    }



}
