package com.rolle.doctor.viewmodel;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.MD5;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.content.MultipartBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.content.multi.FilePart;
import com.litesuits.http.request.content.multi.StringPart;
import com.litesuits.http.request.param.HttpMethod;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.util.RequestApi;
import com.rolle.doctor.util.UrlApi;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public void  requestModel(String tel,String type, final HttpModelHandler<String> listener,OnValidationListener onValidationListener){
       if (!CommonUtil.isMobileNO(tel)){
           if (CommonUtil.notNull(onValidationListener)){
               onValidationListener.errorTelNull();
           }
           return;
        }
        execute(RequestApi.requestTelCode(tel,type),listener);
    }




    /***
     *@Description 发送短信
     * @param tel
     * @param listener
     */
    public void  requestSendSms(String tel,String type, final ModelListener<ResponseMessage> listener){
        execute(RequestApi.requestTelCode(tel, type), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, com.litesuits.http.response.Response res) {
                ResponseMessage token=res.getObject(ResponseMessage.class);
                if (CommonUtil.notNull(token)){
                    switch (token.statusCode){
                        case "200":
                            listener.model(res,token);
                            break;
                        case "300":
                            listener.errorModel(null,res);
                            break;
                    }
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, com.litesuits.http.response.Response res) {
                listener.errorModel(e,res);
                listener.view();
            }
        });
    }



    public static  interface OnValidationListener{
        void errorTelNull();
    }

    public static  interface OnUserValidationListener{
        void errorPwd();
        void errorCode();
    }

}
