package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.view.IView;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author hua
 * @Description: 注册  填写用户
 */
public class RegisterTypePresenter extends Presenter {

    private IRegisterView view;
    private UserModel userModel;
    public RegisterTypePresenter(IRegisterView iView) {
        this.view = iView;
        userModel=new UserModel(view.getContext());
    }

   public void doRegister(){
       if (CommonUtil.isEmpty(view.getType())){
           view.msgShow("请选择");
           return;
       }
       User user=new User();
       user.mobile=view.getTel();
       user.id=1;
       user.type=Util.getUserType(view.getType());
       userModel.saveUser(user);
       Log.d(userModel.db.queryById(1,User.class));
    }


    public static interface IRegisterView extends IView{
        String getType();
        String getTel();
    }
}
