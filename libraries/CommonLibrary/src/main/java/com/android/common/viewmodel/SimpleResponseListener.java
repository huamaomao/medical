package com.android.common.viewmodel;

import com.android.common.domain.ResponseMessage;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;

/**
 * @author Hua_
 * @Description:简单回调函数
 * @date 2015/5/20 - 18:49
 */
public abstract class SimpleResponseListener<Model>{

    public abstract void requestSuccess(Model info,Response response);

    public abstract void requestError(HttpException e,ResponseMessage info);

    public  void requestView(){};
}
