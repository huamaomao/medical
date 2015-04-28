package com.rolle.doctor.presenter;

import com.android.common.domain.ResponseMessage;
import com.android.common.presenter.Presenter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.MainActivity;
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
public class RegisterInfoPresenter extends Presenter {

    private IRegisterView view;
    private UserModel userModel;
    private ListModel listModel;
    public RegisterInfoPresenter(IRegisterView iView) {
        this.view = iView;
        userModel=new UserModel(view.getContext());
        listModel=new ListModel(view.getContext());
    }

   public void doNext(){
        if (CommonUtil.isEmpty(view.getName())){
            view.msgShow("请输入真实姓名");
            return;
        }
       if (CommonUtil.isEmpty(view.getHospital())){
           view.msgShow("请输入工作的医院");
           return;
       }
       if (CommonUtil.isNull(view.getVisit())){
           view.msgShow("请选择省份");
           return;
       }
       if (CommonUtil.isNull(view.getCity())){
           view.msgShow("请选择城市");
           return;
       }
       if (CommonUtil.isNull(view.getTitleItem())){
           view.msgShow("请选择科室");
           return;
       }
       User user=userModel.db.queryById(userModel.getToken().userId,User.class);
       user.nickname=view.getName();
       user.doctorTitle=view.getTitleItem().id;
       user.hospitalAddress=view.getHospital();
       user.regionId=view.getCity().id;
       userModel.db.save(user);
       view.showLoading();
       userModel.requestUpdateUser(user,new HttpModelHandler<String>() {
           @Override
           protected void onSuccess(String data, Response res) {
               ResponseMessage message=res.getObject(ResponseMessage.class);
               if (CommonUtil.notNull(message)){
                    if (message.statusCode.equals("200")){
                        ViewUtil.openActivity(MainActivity.class,view.getContext());
                    }
               }
               view.hideLoading();
           }

           @Override
           protected void onFailure(HttpException e, Response res) {
               view.hideLoading();
           }
       });

    }

    public void doVisitList(){
        listModel.requestCity("1",new ViewModel.ModelListener<List<CityResponse.Item>>() {
            @Override
            public void model(Response response, List<CityResponse.Item> items) {
                view.setVisitList((ArrayList) items);
            }

            @Override
            public void errorModel(ModelEnum modelEnum) {

            }

            @Override
            public void view() {

            }
        });
    }

    public void doCityList(){
        if (view.getVisit()==null){
            doVisitList();
            return;
        }
        listModel.requestCity(view.getVisit().id,new ViewModel.ModelListener<List<CityResponse.Item>>() {
            @Override
            public void model(Response response, List<CityResponse.Item> items) {
                view.setCityList((ArrayList)items);
            }

            @Override
            public void errorModel(ModelEnum modelEnum) {

            }

            @Override
            public void view() {

            }
        });
    }

    public void doTitleList(){
        listModel.requestTitle("44", new ViewModel.ModelListener<List<CityResponse.Item>>() {
            @Override
            public void model(Response response, List<CityResponse.Item> items) {
                view.setTitleList((ArrayList) items);
            }

            @Override
            public void errorModel(ModelEnum modelEnum) {

            }

            @Override
            public void view() {

            }
        });
    }



    public static interface IRegisterView extends IView{
        String getName();
        CityResponse.Item  getCity();
        CityResponse.Item  getVisit();
        String  getHospital();
        CityResponse.Item getTitleItem();
        void setVisitList(ArrayList<CityResponse.Item> list);
        void setCityList(ArrayList<CityResponse.Item> list);
        void setTitleList(ArrayList<CityResponse.Item> list);

    }
}
