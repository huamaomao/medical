package com.rolle.doctor.presenter;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.ActivityModel;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.MD5;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.RegisterChooseActivity;
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

    public void doTwoRegister() {
        if (CommonUtil.isEmpty(view.getCode())||view.getCode().length()<4) {
            view.msgShow("验证码必须是4位数字");
            return;
        }

        if (!CommonUtil.checkPassword(view.getPwd())) {
            view.msgShow("密码格式错误,密码需6-15位");
            return;
        }
        String password = MD5.compute(view.getPwd());
        view.showLoading();
        userModel.requestRegister(view.getTel(), view.getCode(), password, new SimpleResponseListener<User>() {
            @Override
            public void requestSuccess(User info, Response response) {
                ViewUtil.openActivity(RegisterChooseActivity.class, null, view.getContext(), ActivityModel.ACTIVITY_MODEL_2);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                new AppHttpExceptionHandler().via(view.getContext()).handleException(e,info);
            }

            @Override
            public void requestView() {
                view.hideLoading();
            }
        });

    }


   public void doSendSms(){
       view.timeSendStart();
       model.requestModel(view.getTel(),"83", new HttpModelHandler<String>() {
           @Override
           protected void onSuccess(String data, Response res) {

           }
           @Override
           protected void onFailure(HttpException e, Response res) {
               ViewUtil.requestHandle(e,res,view.getContext());
           }
       }, new RegisterModel.OnValidationListener() {
           @Override
           public void errorTelNull() {
               view.msgShow("手机号错误...");
           }
       });
    }

    public  interface IRegisterView extends IView{
        String getTel();
        String getCode();
        String getPwd();
        void timeSendStart();
    }
}
