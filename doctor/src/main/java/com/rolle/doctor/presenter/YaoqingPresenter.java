package com.rolle.doctor.presenter;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.view.IView;
import com.android.common.viewmodel.SimpleResponseListener;
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
        model.requestModelUserCode(new SimpleResponseListener<InviteCodeResponse>() {
            @Override
            public void requestSuccess(InviteCodeResponse info, Response response) {
                view.setMyCode(info.inviteCode);
                view.setMyNum(info.inviteSum);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }
        });
    }

    public static interface IYaoqingView extends IView {
        void  setMyCode(String code);
        void  setMyNum(String num);
    }
}
