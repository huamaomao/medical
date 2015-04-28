package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.view.IView;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.PatientSum;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.List;

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
         model.requestFriendPatient(new ViewModel.ModelListener<List<FriendResponse.Item>>() {
            @Override
            public void model(Response response, List<FriendResponse.Item> items) {

            }

            @Override
            public void errorModel(ModelEnum modelEnum) {

            }

            @Override
            public void view() {

            }
        });

        model.requestFriendDietitan(new ViewModel.ModelListener<List<FriendResponse.Item>>() {
            @Override
            public void model(Response response, List<FriendResponse.Item> items) {

            }

            @Override
            public void errorModel(ModelEnum modelEnum) {

            }

            @Override
            public void view() {

            }
        });

        model.requestFriendDoctor(new ViewModel.ModelListener<List<FriendResponse.Item>>() {
            @Override
            public void model(Response response, List<FriendResponse.Item> items) {

            }

            @Override
            public void errorModel(ModelEnum modelEnum) {

            }

            @Override
            public void view() {

            }
        });

    }






   public static interface IFriendView extends IView{

   }
}
