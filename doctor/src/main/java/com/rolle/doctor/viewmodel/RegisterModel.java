package com.rolle.doctor.viewmodel;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.util.RequestApi;

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
    public void  requestSendSms(String tel,String type, final SimpleResponseListener<ResponseMessage> listener){
        execute(RequestApi.requestTelCode(tel, type), listener);
    }

    public   interface OnValidationListener{
        void errorTelNull();
    }

}
