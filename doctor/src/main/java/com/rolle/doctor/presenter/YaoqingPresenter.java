package com.rolle.doctor.presenter;

import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.view.IView;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.InviteCodeResponse;
import com.rolle.doctor.viewmodel.UserModel;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/22 - 14:16
 */
public class YaoqingPresenter extends Presenter {
    private UserModel model;
    private IYaoqingView view;

    public YaoqingPresenter(IYaoqingView view){
        this.view=view;
        model=new UserModel(view.getContext());
    }

    public void doMyCode(){

        model.requestModelUserCode(new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                InviteCodeResponse token=res.getObject(InviteCodeResponse.class);
                Log.d(token);
                if (CommonUtil.notNull(token)){
                    switch (token.statusCode){
                        case "200":
                            view.setMyCode(token.inviteCode);
                            view.setMyNum(token.inviteSum);
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

    public static interface IYaoqingView extends IView {
        void  setMyCode(String code);
        void  setMyNum(String num);
    }
}
