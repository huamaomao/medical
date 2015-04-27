package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.MainActivity;
import com.rolle.doctor.ui.RegisterInfoActivity;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.UserModel;

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

    }


    public static interface IRegisterView extends IView{
        String getName();
        String  getCity();
        String  getVisit();
        String  getHospital();
        String getSection();
    }
}
