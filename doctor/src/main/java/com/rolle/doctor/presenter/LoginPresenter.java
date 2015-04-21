package com.rolle.doctor.presenter;

import android.os.Bundle;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.Constants;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.ui.MainActivity;
import com.rolle.doctor.ui.RegisterTwoActivity;
import com.rolle.doctor.viewmodel.LoginModel;
import com.rolle.doctor.viewmodel.RegisterModel;

/**
 * @author hua
 * @Description: 注册
 */
public class LoginPresenter extends Presenter {

    private ILogin view;
    private LoginModel model;
    public LoginPresenter(ILogin iView) {
        this.view = iView;
        model=new LoginModel();
    }

   public void doLogin(){
       view.showLoading();
       model.requestModel(view.getTel(),view.getPwd(),new HttpModelHandler<String>(){
           @Override
           protected void onSuccess(String data, Response res) {
               Token token=res.getObject(Token.class);
               if (CommonUtil.notNull(token)){
                   switch (token.statusCode){
                       case "200":
                           ViewUtil.openActivity(MainActivity.class,null,view.getContext(), ActivityModel.ACTIVITY_MODEL_2);
                           break;
                       case "300":
                            view.msgShow("登陆失败账号或密码错误.....");
                           break;
                   }
               }
               view.hideLoading();
           }

           @Override
           protected void onFailure(HttpException e, Response res) {
               if (e instanceof HttpNetException) {
                   HttpNetException netException = (HttpNetException) e;
               } else if (e instanceof HttpServerException) {
                   HttpServerException serverException = (HttpServerException) e;
               }
               view.msgShow("登陆失败");
               view.hideLoading();
           }

       },new LoginModel.OnValidationListener() {
           @Override
           public void errorPwd() {
               view.msgShow("密码格式错误，6至15位");
           }

           @Override
           public void errorTel() {
               view.msgShow("手机格式错误..");
           }
       });
    }


    public static interface ILogin extends IView{
         String getTel();
         String getPwd();
    }

}
