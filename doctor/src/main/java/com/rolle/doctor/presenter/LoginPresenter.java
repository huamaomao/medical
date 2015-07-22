package com.rolle.doctor.presenter;

import android.app.Activity;
import android.content.Intent;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.ActivityModel;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
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
       
       model.requestLogin(view.getTel(), view.getPwd(), new SimpleResponseListener<User>() {
           @Override
           public void requestSuccess(User info, Response response) {
               if (info.typeId == null) {
                   doLoginService();
                   ViewUtil.openActivity(RegisterChooseActivity.class, null, view.getContext(), ActivityModel.ACTIVITY_MODEL_2, true);
               } else {
                   doLoginService();
                   ViewUtil.openActivity(MainActivity.class, null, view.getContext(), ActivityModel.ACTIVITY_MODEL_2,true);
               }
           }

           @Override
           public void requestError(HttpException e, ResponseMessage info) {
                new AppHttpExceptionHandler().via((Activity)view).handleException(e, info);
                view.hideLoading();
           }

       });
    }


    public void doIsLogin(){
       Token token= model.getToken();
        User user=model.getLoginUser();
       if (CommonUtil.notNull(token)&&CommonUtil.notNull(user)){
           view.setTel(token.tel);
           if (token.isLogin()){
               ViewUtil.openActivity(MainActivity.class, null, view.getContext(), ActivityModel.ACTIVITY_MODEL_2,true);
               if (CommonUtil.isEmpty(user.doctorDetail.hospitalName)){
                   doLoginService();
                   ViewUtil.openActivity(RegisterChooseActivity.class, null, view.getContext(), ActivityModel.ACTIVITY_MODEL_2);
                   return;
               }
           }
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
