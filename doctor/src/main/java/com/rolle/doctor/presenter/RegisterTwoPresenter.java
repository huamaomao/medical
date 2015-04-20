package com.rolle.doctor.presenter;

import android.os.Bundle;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.ActivityModel;
import com.android.common.util.Constants;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.rolle.doctor.ui.RegisterThreeActivity;
import com.rolle.doctor.ui.RegisterTwoActivity;
import com.rolle.doctor.viewmodel.RegisterModel;

/**
 * @author hua
 * @Description: 注册  第二步
 */
public class RegisterTwoPresenter extends Presenter {

    private IRegisterView view;
    private RegisterModel model;
    public RegisterTwoPresenter(IRegisterView iView) {
        this.view = iView;
        model=new RegisterModel();
    }

    public void doTwoRegister(){
        model.requestModel(view.getTel(),view.getCode(),new ViewModel.OnModelListener<ResponseMessage>() {
            @Override
            public void onSuccess(ResponseMessage message) {
                Bundle bundle=new Bundle();
                bundle.putString(Constants.DATA_TEL,view.getTel());
                bundle.putString(Constants.DATA_CODE,view.getCode());
                ViewUtil.openActivity(RegisterThreeActivity.class, bundle, view.getContext(), ActivityModel.ACTIVITY_MODEL_2);
            }

            @Override
            public void onError(HttpException e, ResponseMessage message) {

            }

            @Override
            public void onFinally() {

            }
        },new RegisterModel.OnCheckValidationListener() {
            @Override
            public void errorCodeNull() {
                view.hideLoading();
                view.msgShow("验证码格式错误");
            }

            @Override
             public void errorTelNull() {
                view.hideLoading();
                view.msgShow("手机验证码为空");
            }
        });
    }


   public void doSendSms(){
       view.timeSendStart();
       model.requestModel(view.getTel(),new ViewModel.OnModelListener<ResponseMessage>(){
           @Override
           public void onSuccess(ResponseMessage message) {

           }

           @Override
           public void onError(HttpException e,ResponseMessage message) {
               if (e instanceof HttpNetException) {
                   HttpNetException netException = (HttpNetException) e;
               } else if (e instanceof HttpServerException) {
                   HttpServerException serverException = (HttpServerException) e;
               }
           }

           @Override
           public void onFinally() {
               view.hideLoading();
           }
       },new RegisterModel.OnValidationListener() {
           @Override
           public void errorTelNull() {
               view.msgShow("验证码错误");
           }
       });
    }

    public static interface OnPresenterListener{

    }

    public static interface IRegisterView extends IView{
        String getTel();
        String getCode();
         void timeSendStart();
    }
}
