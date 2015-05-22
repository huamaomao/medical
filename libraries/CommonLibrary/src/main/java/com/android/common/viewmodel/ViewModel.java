package com.android.common.viewmodel;

import android.content.Context;

import com.android.common.util.LiteUtil;
import com.android.common.view.IView;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.request.Request;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;

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

    public static interface ModelListener<T>{
        public void model(Response response,T t);
        public void errorModel(HttpException e,Response response);
        /***最终反馈 view **/
        public void view();
    }
}
