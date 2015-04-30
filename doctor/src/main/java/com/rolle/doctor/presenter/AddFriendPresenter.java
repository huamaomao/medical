package com.rolle.doctor.presenter;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.view.IView;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.response.Response;
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
        model=new UserModel(view.getContext());
    }

    public void doAdd(){
        if (flag)return;
        flag=true;
        model.requestAddFriend(view.getTel(),"",new ViewModel.ModelListener<ResponseMessage>(){
            @Override
            public void model(Response response, ResponseMessage message) {
                    view.msgShow("添加成功");
            }

            @Override
            public void errorModel(ModelEnum modelEnum) {
                view.msgShow("添加失败");
            }

            @Override
            public void view() {
                flag=false;
            }
        });
    }

    public static interface IFriendView extends IView{
        String getTel();
    }

}
