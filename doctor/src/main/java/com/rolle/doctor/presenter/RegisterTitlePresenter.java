package com.rolle.doctor.presenter;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.RegisterInfoActivity;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.ListModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hua
 * @Description: 注册  填写用户
 */
public class RegisterTitlePresenter extends Presenter {

    private IRegisterView view;
    private ListModel model;
    private UserModel userModel;
    public RegisterTitlePresenter(IRegisterView iView) {
        this.view = iView;
        model=new ListModel(view.getContext());
        userModel=new UserModel(view.getContext());
    }



    public void doLoad(){
        model.requestTitle("65", new SimpleResponseListener<List<CityResponse.Item>>() {
            @Override
            public void requestSuccess(List<CityResponse.Item> info, Response response) {
                view.setTitleList((ArrayList)info);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }
        });

    }


    public  interface IRegisterView extends IView{
        CityResponse.Item getTitleItem();
        void setTitleList(ArrayList<CityResponse.Item> list);
    }
}
