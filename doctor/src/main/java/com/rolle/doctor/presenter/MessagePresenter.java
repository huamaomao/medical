package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.domain.UserResponse;
import com.rolle.doctor.ui.BaseActivity;
import com.rolle.doctor.ui.MainActivity;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author hua
 * @Description: 注册
 */
public class MessagePresenter extends Presenter {

    private IMessageView view;
    private UserModel model;
    public MessagePresenter(IMessageView iView) {
        this.view = iView;
        model=new UserModel((BaseActivity)view.getContext());
    }



   public void doMessage(){

    }




    public static interface IMessageView extends IView{

    }

}
