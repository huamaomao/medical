package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.view.IView;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author hua
 * @Description: 注册
 */
public class ChooseListPresenter extends Presenter {

    private IChooseView view;
    private UserModel model;
    public ChooseListPresenter(IChooseView iView) {
        this.view = iView;
        model=new UserModel(view.getContext());
    }

    public void doChoose(){

    }

    public static interface IChooseView extends IView{

    }

}
