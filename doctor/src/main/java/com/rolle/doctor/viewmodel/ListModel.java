package com.rolle.doctor.viewmodel;

import android.content.Context;

import com.android.common.domain.ResponseMessage;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.util.RequestApi;

import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/28 - 9:25
 */
public class ListModel extends ViewModel {
    public static DataBase db;

    public ListModel(Context context){
        db= LiteOrm.newCascadeInstance(context, AppConstants.DB_NAME);
    }
    /***
     *@Description 获取省会城市
     * @param id
     * @param listener
     */
    public void requestCity(String id,final SimpleResponseListener<List<CityResponse.Item>> listener){
        execute(RequestApi.requestCity(id), new SimpleResponseListener<CityResponse>() {
            @Override
            public void requestSuccess(CityResponse info, Response response) {
                listener.requestSuccess(info.list,response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });
    }

    /***
     *@Description 获取省会城市
     * @param id
     * @param listener
     */
    public void requestTitle(final String id,final SimpleResponseListener<List<CityResponse.Item>> listener){
        final int id_=Integer.parseInt(id);
        execute(RequestApi.requestTitle(id), new SimpleResponseListener<CityResponse>() {
            @Override
            public void requestSuccess(CityResponse info, Response response) {
                info.id=id_;
                db.save(info);
                listener.requestSuccess(info.list,response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });
    }

}
