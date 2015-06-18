package com.android.common.viewmodel;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.LiteUtil;
import com.android.common.view.IView;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.request.Request;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.FutureTask;

/**
 *ViewModel 处理数据
 */
public abstract class ViewModel{
    private static final String TAG="ViewModel";
    /*****
     *异步访问
     * @param request
     * @param
     */
   protected void  execute(Request request,final HttpModelHandler<String> listener){
         LiteUtil.getInstance().execute(request,listener);
   }

    /*****
     *异步访问
     * @param request
     * @param
     */
    protected  <T extends ResponseMessage>FutureTask<String>  execute(Request request,final SimpleResponseListener<T> responseService){
        return LiteUtil.getInstance().execute(request, new HttpModelHandler<String>(){
            @Override
            protected void onSuccess(String data, Response res) {
                try {
                    Type type = ((ParameterizedType) responseService.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                    T t=null;
                    if (type instanceof Class<?>) {
                        t=res.getObject((Class<T>) type);
                    } else if (type instanceof ParameterizedType) {
                        t=res.getObject((Class<T>) ((ParameterizedType) type).getRawType());
                    }
                    if (CommonUtil.isNull(t)){
                        responseService.requestError(null,null);
                    }else if ("200".equals(t.statusCode)){
                        responseService.requestSuccess(t,res);
                    }else{
                        responseService.requestError(null, t);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                responseService.requestView();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                responseService.requestError(e,null);
                responseService.requestView();
            }
        });
    }




    protected void requestHandle(HttpException e,Response response,Context iView){
        IView view=(IView)iView;
        if (e instanceof HttpNetException) {
            HttpNetException netException = (HttpNetException) e;
            view.msgShow(netException.getMessage());
        } else if (e instanceof HttpServerException) {
           // HttpServerException serverException = (HttpServerException) e;
            view.msgShow("无法访问服务器....");
        }
    }




}
