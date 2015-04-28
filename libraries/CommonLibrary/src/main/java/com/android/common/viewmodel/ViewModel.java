package com.android.common.viewmodel;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.LiteUtil;
import com.android.common.util.Log;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.Request;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

    public static interface ModelListener<T>{
        public void model(Response response,T t);
        public void errorModel(ModelEnum modelEnum);
        /***最终反馈 view **/
        public void view();
    }
}
