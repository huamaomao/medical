package com.android.common.viewmodel;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.LiteUtil;
import com.android.common.util.Log;
import com.android.common.util.OkHttpUtil;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.Request;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *ViewModel 处理数据
 */
public abstract class ViewModel<Model>{
    private static final String TAG="ViewModel";
    /*****
     *异步访问
     * @param request
     * @param
     */
   protected void execute(Request request,final OnModelListener<Model> listener){

          LiteUtil.getInstance().execute(request, new HttpModelHandler<Model>() {
               @Override
               protected void onSuccess(Model data, Response res) {
                   Log.d(TAG,res.getString());
                   ResponseMessage message=JSON.parseObject(res.getString(),ResponseMessage.class);
                   listener.onSuccess(data);
               }

               @Override
               protected void onFailure(HttpException e, Response res) {
                   listener.onFailure(e, res);
               }
           });

           /***
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            Model model= JSON.parseObject(response.body().toString(),params[0]);
            * **/

   }


   public interface  OnModelListener<Model>{
       /**
        * 成功时回调
        *
        * @param model
        */
       void onSuccess(Model model);
       /**
        * 失败时回调
        */
       void onError(ResponseMessage message);

       /****
        * 异常
        * @param ex
        */
       void onFailure(Exception ex,Response response);
   }

}
