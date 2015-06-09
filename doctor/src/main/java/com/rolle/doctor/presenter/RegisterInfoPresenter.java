package com.rolle.doctor.presenter;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.BaseActivity;
import com.rolle.doctor.ui.MainActivity;
import com.rolle.doctor.viewmodel.ListModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hua
 * @Description: 注册  填写用户
 */
public class RegisterInfoPresenter extends Presenter {

    private IRegisterView view;
    private UserModel userModel;
    public RegisterInfoPresenter(IRegisterView iView) {
        this.view = iView;
        userModel=new UserModel(view.getContext());
    }

   public void doNext(){
        if (CommonUtil.isEmpty(view.getName())){
            view.msgShow("请输入真实姓名");
            return;
        }
       if (CommonUtil.isEmpty(view.getHospital())){
           view.msgShow("请输入工作的医院");
           return;
       }
       if (CommonUtil.isNull(view.getCity())){
           view.msgShow("请选择省份");
           return;
       }
       if (CommonUtil.isNull(view.getTitle())){
           view.msgShow("请选择科室");
           return;
       }
       User user=userModel.getLoginUser();
       user.nickname=view.getName();
       user.userName=view.getName();
       user.doctorTitle=view.getTitle();
       user.hospitalAddress=view.getHospital();
       user.regionId=view.getCity();
       user.token=userModel.getToken().token;
       userModel.db.save(user);
       view.showLoading();
       userModel.requestUpdateUser(user, new SimpleResponseListener<ResponseMessage>() {
           @Override
           public void requestSuccess(ResponseMessage info, Response response) {
               ViewUtil.startTopActivity(MainActivity.class,view.getContext());
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




    public static interface IRegisterView extends IView{
        String getName();
        String   getCity();
        String  getHospital();
        String getTitle();
    }
}
