package com.rolle.doctor.viewmodel;

import android.content.Context;

import com.android.common.util.CommonUtil;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.util.Constants;
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
        db= LiteOrm.newCascadeInstance(context, Constants.DB_NAME);
    }
    /***
     *@Description 获取省会城市
     * @param id
     * @param listener
     */
    public void requestCity(String id,final ModelListener<List<CityResponse.Item>> listener){
        execute(RequestApi.requestCity(id),new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                CityResponse   cityResponse= res.getObject(CityResponse.class);
                if (CommonUtil.notNull(cityResponse)){
                    if ("200".equals(cityResponse.statusCode)){
                        if (CommonUtil.notNull(cityResponse.selectList)){
                            listener.model(res,cityResponse.selectList);
                            return;
                        }
                    }
                }

            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                listener.errorModel(null);
            }
        });
    }

    /***
     *@Description 获取省会城市
     * @param id
     * @param listener
     */
    public void requestTitle(final String id,final ModelListener<List<CityResponse.Item>> listener){
        final int id_=Integer.parseInt(id);
        execute(RequestApi.requestTitle(id),new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                CityResponse   cityResponse= res.getObject(CityResponse.class);
                if (CommonUtil.notNull(cityResponse)){
                    if ("200".equals(cityResponse.statusCode)){
                        if (CommonUtil.notNull(cityResponse.selectList)){
                            listener.model(res,cityResponse.selectList);
                            cityResponse.id=id_;
                            db.save(cityResponse);
                            return;
                        }
                    }
                }else {
                    CityResponse cityResponse1= db.queryById(id_, CityResponse.class);
                    if (CommonUtil.notNull(cityResponse1)){
                        listener.model(res,cityResponse1.selectList);
                    }else {
                        listener.errorModel(null);
                    }
                }

            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                CityResponse cityResponse1 = db.queryById(id_, CityResponse.class);
                if (CommonUtil.notNull(cityResponse1)){
                    listener.model(res,cityResponse1.selectList);
                    return;
                }
                listener.errorModel(null);
            }
        });
    }

}
