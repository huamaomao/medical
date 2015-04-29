package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.view.IView;
import com.rolle.doctor.viewmodel.GotyeModel;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author hua
 * @Description: 注册
 */
public class MessageListPresenter extends Presenter {

    private IMessageView view;
    private GotyeModel gotyeModel;
    private UserModel model;
    public MessageListPresenter(IMessageView iView) {
        this.view = iView;
        model=new UserModel(view.getContext());
        gotyeModel=new GotyeModel();
    }

   public void doMessage(){

    }


    public static interface IMessageView extends IView{

    }

}
