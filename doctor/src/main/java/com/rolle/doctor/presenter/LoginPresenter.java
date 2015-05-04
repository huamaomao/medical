package com.rolle.doctor.presenter;

import android.content.Intent;
import com.android.common.presenter.Presenter;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.MD5;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.domain.UserResponse;
import com.rolle.doctor.service.GotyeService;
import com.rolle.doctor.ui.MainActivity;
import com.rolle.doctor.ui.RegisterChooseActivity;
import com.rolle.doctor.viewmodel.GotyeModel;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author hua
 * @Description: 注册
 */
public class LoginPresenter extends Presenter {
    private ILogin view;
    private UserModel model;
    public LoginPresenter(ILogin iView) {
        this.view = iView;
        model=new UserModel(view.getContext());
    }

    public void initUser(){
       Token token =model.getToken();
        if (token!=null){
            view.setTel(token.tel);
        }
    }

   public void doLogin(){
       view.showLoading();
       model.requestModel(view.getTel(),view.getPwd(),new HttpModelHandler<String>(){
           @Override
           protected void onSuccess(String data, Response res) {
               Token token=res.getObject(Token.class);
               Log.d(token);
               if (CommonUtil.notNull(token)){
                   switch (token.statusCode){
                       case "200":
                          token.tel=view.getTel();
                          model.setToken(token);
                           doGetUserInfo();
                           doLoginService();
                          view.hideLoading();
                          break;
                       case "300":
                            view.msgShow("登陆失败账号或密码错误.....");
                            view.hideLoading();
                           break;
                   }
               }

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

       },new UserModel.OnValidationListener(){
           @Override
           public void errorPwd() {
               view.msgShow("密码格式错误，6至15位");
               view.hideLoading();
           }

           @Override
           public void errorTel() {
               view.msgShow("手机格式错误..");
               view.hideLoading();
           }
       });
    }

    public void doIsLogin(){
       Token token= model.getToken();
       if (CommonUtil.notEmpty(token.token)){
           doGetUserInfo();
       }
    }

    /*****
     * 获取个人信息
     */
    public void doGetUserInfo(){
        model.requestModel(model.getToken().token,new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                UserResponse user=res.getObject(UserResponse.class);
                if (CommonUtil.notNull(user)){
                    switch (user.statusCode){
                        case "200":
                            Log.d(user.user);
                            // 更新 token id
                            model.getToken().userId=user.user.id;
                            model.getToken().pwd= MD5.compute(view.getPwd());
                            model.setToken(model.getToken());
                            model.db.save(user.user);
                            Log.d(model.getToken());
                           if (user.user.typeId==null){
                               view.getContext().finish();
                               ViewUtil.openActivity(RegisterChooseActivity.class,null,view.getContext(), ActivityModel.ACTIVITY_MODEL_2);
                           }else {
                               view.getContext().finish();
                               ViewUtil.openActivity(MainActivity.class,null,view.getContext(), ActivityModel.ACTIVITY_MODEL_2);
                           }

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

    public void doLoginService(){
        view.getContext().startService(new Intent(view.getContext(), GotyeService.class));
    }


    public static interface ILogin extends IView{
         String getTel();
        String getPwd();
        void  setTel(String tel);
    }

}
