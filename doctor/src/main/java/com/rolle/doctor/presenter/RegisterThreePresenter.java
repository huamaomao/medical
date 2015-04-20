package com.rolle.doctor.presenter;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.view.IView;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.rolle.doctor.viewmodel.RegisterModel;
/**
 * @author hua
 * @Description: 注册  填写用户
 */
public class RegisterThreePresenter extends Presenter {

    private IRegisterView view;
    private RegisterModel model;
    public RegisterThreePresenter(IRegisterView iView) {
        this.view = iView;
        model=new RegisterModel();
    }

   public void doRegister(){
       view.showLoading();
       model.requestModel(view.getTel(),view.getCode(),view.getNickName(),view.getPwd(),view.getType(),new ViewModel.OnModelListener<ResponseMessage>() {
           @Override
           public void onSuccess(ResponseMessage message) {

           }

           @Override
           public void onError(HttpException e, ResponseMessage message) {

           }

           @Override
           public void onFinally() {
               view.hideLoading();
           }
       },new RegisterModel.OnUserValidationListener() {
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
       });
    }


    public static interface IRegisterView extends IView{
        String getTel();
        String getPwd();
        String getType();
        String getNickName();
        String getCode();
    }

    public static interface OnPresenterListener{

    }

}
