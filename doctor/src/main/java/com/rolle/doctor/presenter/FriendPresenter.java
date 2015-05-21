package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.view.IView;
import com.android.common.viewmodel.ModelEnum;
import com.rolle.doctor.ui.BaseActivity;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/22 - 13:10
 */
public class FriendPresenter extends Presenter {
    private UserModel model;
    private IFriendView view;

    public FriendPresenter(IFriendView view){
        this.view=view;
        model=new UserModel((BaseActivity)view.getContext());
    }

    public void doPaitentSum(){
        view.setPatientSum(model.requestModelNum()+"");
    }


   public  interface IFriendView extends IView{
       void setPatientSum(String sum);
   }
}
