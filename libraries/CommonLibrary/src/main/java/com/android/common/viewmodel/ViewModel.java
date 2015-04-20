package com.android.common.viewmodel;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.LiteUtil;
import com.android.common.util.Log;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.Request;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;

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
                   Log.d(TAG,res.getString()+"");
                   Log.d(TAG,data+"");
                   listener.onSuccess(data);
                   listener.onFinally();
               }

               @Override
               protected void onFailure(HttpException e, Response res) {
                   ResponseMessage message=JSON.parseObject(res.getString(),ResponseMessage.class);
                   listener.onError(e, message);
                   listener.onFinally();
               }
           });


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
       void onError(HttpException e,ResponseMessage message);

       /*****
        * 最后回调函数 ，用来回调view操作
        */
       void onFinally();
   }

}
