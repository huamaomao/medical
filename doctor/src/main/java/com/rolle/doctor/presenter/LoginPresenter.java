package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
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
import com.rolle.doctor.ui.MainActivity;
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
       if (true){
           ViewUtil.openActivity(MainActivity.class,null,view.getContext(), ActivityModel.ACTIVITY_MODEL_2);
           return;
       }
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
                          token.status=1;
                          model.setToken(token);
                          doGetUserInfo();
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

       },new UserModel.OnValidationListener(){
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
                            model.db.save(user.user);
                            Log.d(model.db.queryById(user.user.id, User.class));
                            ViewUtil.openActivity(MainActivity.class,null,view.getContext(), ActivityModel.ACTIVITY_MODEL_2);
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


    public static interface ILogin extends IView{
         String getTel();
        String getPwd();
        void  setTel(String tel);
    }

}
