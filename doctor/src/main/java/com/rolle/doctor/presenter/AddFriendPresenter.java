package com.rolle.doctor.presenter;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.view.IView;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.ui.BaseActivity;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author hua
 * @Description: 注册
 */
public class AddFriendPresenter extends Presenter {

    private IFriendView view;
    private UserModel model;
    public AddFriendPresenter(IFriendView iView) {
        this.view = iView;
        model=new UserModel((BaseActivity)view.getContext());
    }

    public void doAdd(){
        if (flag)return;
        flag=true;
        model.requestAddFriend(view.getTel(), "", new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                view.msgShow("添加成功");
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                view.msgShow("添加失败");
            }

            @Override
            public void requestView() {
                flag=false;
            }
        });

    }

    public  interface IFriendView extends IView{
        String getTel();
    }

}
