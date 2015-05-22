package com.rolle.doctor.presenter;

import android.content.Intent;
import com.android.common.presenter.Presenter;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
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
import com.rolle.doctor.service.GotyeService;
import com.rolle.doctor.ui.MainActivity;
import com.rolle.doctor.ui.RegisterChooseActivity;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author hua
 * @Description: 注册
 */
public class LoginPresenter extends Presenter {
    private ILogin view;
    private UserModel model;
    static String TAG="Login";
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
       if (!CommonUtil.isMobileNO(view.getTel())){
           view.msgShow("手机格式错误..");
            return;
        }
        if (CommonUtil.isEmpty(view.getPwd())||view.getPwd().length()<6||view.getPwd().length()>15){
            view.msgShow("密码格式错误，6至15位");
            return;
        }
       view.showLoading();
       model.requestLogin(view.getTel(), view.getPwd(), new ViewModel.ModelListener<User>() {
           @Override
           public void model(Response response, User user) {
               if (user.typeId == null) {
                   ViewUtil.openActivity(RegisterChooseActivity.class, null, view.getContext(), ActivityModel.ACTIVITY_MODEL_2,true);
               } else {
                   doLoginService();
                   ViewUtil.openActivity(MainActivity.class, null, view.getContext(), ActivityModel.ACTIVITY_MODEL_2,true);
               }
           }

           @Override
           public void errorModel(HttpException e, Response response) {
               if (e instanceof HttpNetException) {
                   HttpNetException netException = (HttpNetException) e;
                   view.msgShow("无网络访问.....");
               } else if (e instanceof HttpServerException) {
                   HttpServerException serverException = (HttpServerException) e;
                   view.msgShow("无法访问服务器.....");
               }else{
                   view.msgShow("登陆失败.....");
               }
           }

           @Override
           public void view() {
                view.hideLoading();
           }
       });

    }

    public void doIsLogin(){
       Token token= model.getToken();
       if (CommonUtil.notNull(token)&&token.isLogin()){
           doLoginService();
           ViewUtil.openActivity(MainActivity.class, null, view.getContext(), ActivityModel.ACTIVITY_MODEL_2,true);
       }
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
