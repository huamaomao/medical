package com.rolle.doctor.presenter;

import android.os.Bundle;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.Constants;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.domain.UserResponse;
import com.rolle.doctor.ui.MainActivity;
import com.rolle.doctor.ui.RegisterChooseActivity;
import com.rolle.doctor.ui.RegisterThreeActivity;
import com.rolle.doctor.ui.RegisterTwoActivity;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.RegisterModel;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author hua
 * @Description: 注册  第二步
 */
public class RegisterTwoPresenter extends Presenter {

    private IRegisterView view;
    private RegisterModel model;
    private UserModel userModel;
    public RegisterTwoPresenter(IRegisterView iView) {
        this.view = iView;
        model=new RegisterModel();
        userModel=new UserModel(iView.getContext());
    }

    public void doTwoRegister(){
        model.requestRegister(view.getTel(), view.getCode(), view.getPwd(), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                Token token = res.getObject(Token.class);
                if (CommonUtil.notNull(token)) {
                    switch (token.statusCode) {
                        case "200":
                            token.tel=view.getTel();
                            userModel.setToken(token);
                            doGetUserInfo();
                            ViewUtil.openActivity(RegisterChooseActivity.class, null, view.getContext(), ActivityModel.ACTIVITY_MODEL_2);
                            break;
                        case "300":
                            view.msgShow(token.message);
                            break;
                    }
                }
                view.hideLoading();

            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                if (Util.errorHandle(e, view)) {
                    view.msgShow("注册失败");
                }
                view.hideLoading();
            }
        }, new RegisterModel.OnUserValidationListener() {
            @Override
            public void errorPwd() {
                view.hideLoading();
                view.msgShow("密码格式错误,密码需6-15位");
            }

            @Override
            public void errorCode() {
                view.msgShow("验证码错误,6位数");
                view.hideLoading();
            }
        });

    }


    /*****
     * 获取个人信息
     */
    public void doGetUserInfo(){
        userModel.requestModel(userModel.getToken().token,new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                UserResponse user=res.getObject(UserResponse.class);
                if (CommonUtil.notNull(user)){
                    switch (user.statusCode){
                        case "200":
                            Log.d(user.user);
                            // 更新 token id
                            userModel.getToken().userId=user.user.id;
                            userModel.setToken(userModel.getToken());
                            userModel.db.save(user.user);
                            Log.d(userModel.db.queryById(user.user.id, User.class));
                            ViewUtil.openActivity(RegisterChooseActivity.class,null,view.getContext(), ActivityModel.ACTIVITY_MODEL_2);
                            break;
                        case "300":
                            break;
                    }
                }
            }

            @Override
            protected void onFailure(HttpException e, Response res) {

            }
        });
    }


   public void doSendSms(){
       view.timeSendStart();
       model.requestModel(view.getTel(), new HttpModelHandler<String>() {
           @Override
           protected void onSuccess(String data, Response res) {

           }

           @Override
           protected void onFailure(HttpException e, Response res) {
               if (e instanceof HttpNetException) {
                   HttpNetException netException = (HttpNetException) e;
               } else if (e instanceof HttpServerException) {
                   HttpServerException serverException = (HttpServerException) e;
               }
           }
       }, new RegisterModel.OnValidationListener() {
           @Override
           public void errorTelNull() {
               view.msgShow("验证码错误");
           }
       });
    }

    public static interface IRegisterView extends IView{
        String getTel();
        String getCode();
        String getPwd();
        void timeSendStart();
    }
}
