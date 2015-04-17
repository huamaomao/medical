package com.rolle.doctor.viewmodel;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
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

    private OnValidationListener onValidationListener;

    public void  requestModel(String tel, final OnModelListener<ResponseMessage> listener){
        if (!CommonUtil.isMobileNO(tel)){
            onValidationListener.errorTelNull();
            return;
        }
        execute(RequestApi.requestTelCode(tel),listener);
    }

    public void setOnValidationListener(OnValidationListener onValidationListener) {
        this.onValidationListener = onValidationListener;
    }

    public static  interface OnValidationListener{
        void errorTelNull();
    }

}
