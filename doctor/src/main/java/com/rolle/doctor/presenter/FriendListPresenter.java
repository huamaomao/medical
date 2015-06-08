package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.view.IView;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/22 - 13:10
 */
public class FriendListPresenter extends Presenter {
    private UserModel model;
    private IFriendView view;

    public FriendListPresenter(IFriendView view){
        this.view=view;
        model=new UserModel(view.getContext());
    }

    public void initFriendList(){
       model.requestFriendList();
    }


   public static interface IFriendView extends IView{

   }
}
