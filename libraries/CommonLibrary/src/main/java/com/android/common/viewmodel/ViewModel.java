package com.android.common.viewmodel;

import com.android.common.util.OkHttpUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

/**
 *ViewModel 处理数据
 */
public abstract class ViewModel{

    /*****
     *异步访问
     * @param request
     * @param responseCallback
     */
   private void execute(Request request, Callback responseCallback){
       OkHttpUtil.enqueue(request,responseCallback);
   }
}
