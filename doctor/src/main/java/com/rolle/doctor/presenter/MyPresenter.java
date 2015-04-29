package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.view.IView;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author hua
 * @Description: 注册
 */
public class MyPresenter extends Presenter {

    private IMyView view;
    private UserModel model;
    public MyPresenter(IMyView iView) {
        this.view = iView;
        model=new UserModel(view.getContext());
    }

    public void doInit(){

    }

    public static interface IMyView extends IView{
       void setName(String name);

    }

}
