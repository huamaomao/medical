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
import com.rolle.doctor.viewmodel.GotyeModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/22 - 13:10
 */
public class FriendPresenter extends Presenter {
    private UserModel model;
    private IFriendView view;
    private GotyeModel gotyeModel;

    public FriendPresenter(IFriendView view){
        this.view=view;
        model=new UserModel(view.getContext());
        gotyeModel=new GotyeModel();
    }

    public void doPaitentSum(){
        model.requestModelNum(model.getToken().token,new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                PatientSum token=res.getObject(PatientSum.class);
                Log.d(token);
                if (CommonUtil.notNull(token)){
                    switch (token.statusCode){
                        case "200":
                            view.setPatientSum(token.friendSum);
                            break;
                        case "300":

                            break;
                    }
                }
            }

            @Override
            protected void onFailure(HttpException e, Response res) {

            }
        });
    }





   public static interface IFriendView extends IView{
       void setPatientSum(String sum);
   }
}
