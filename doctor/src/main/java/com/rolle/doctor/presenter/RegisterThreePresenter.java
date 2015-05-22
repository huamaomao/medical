package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.view.IView;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.viewmodel.RegisterModel;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author hua
 * @Description: 注册  填写用户
 */
public class RegisterThreePresenter extends Presenter {

    private IRegisterView view;
    private RegisterModel model;
    private UserModel userModel;
    public RegisterThreePresenter(IRegisterView iView) {
        this.view = iView;
        model=new RegisterModel();
        userModel=new UserModel(view.getContext());
    }

   public void doRegister(){
       view.showLoading();
      /* model.requestModel(view.getTel(),view.getNickName(),view.getPwd(),view.getType(),new HttpModelHandler<String>() {
                   @Override
                   protected void onSuccess(String data, Response res) {
                       Token token=res.getObject(Token.class);
                       Log.d(token);
                       if (CommonUtil.notNull(token)){
                           switch (token.statusCode){
                               case "200":
                                   userModel.setToken(token);
                                   ViewUtil.openActivity(MainActivity.class, null, view.getContext(), ActivityModel.ACTIVITY_MODEL_2,true);
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
                       if (Util.errorHandle(e,view)){
                           view.msgShow("注册失败");
                       }
                       view.hideLoading();
                   }
        },
        new RegisterModel.OnUserValidationListener() {
           @Override
           public void errorPwd() {
               view.msgShow("密码格式错误,密码需6-15位");
               view.hideLoading();
           }

           @Override
           public void errorNickName() {
               view.msgShow("昵称错误,包含中文A-Za-z0-9_");
               view.hideLoading();
           }
       });*/
    }


    public static interface IRegisterView extends IView{
        String getTel();
        String getPwd();
        String getType();
        String getNickName();
    }
}
