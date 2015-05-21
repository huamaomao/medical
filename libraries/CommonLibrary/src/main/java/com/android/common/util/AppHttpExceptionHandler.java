package com.android.common.util;

import android.app.Activity;

import com.litesuits.http.data.HttpStatus;
import com.litesuits.http.exception.HttpClientException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.response.handler.HttpExceptionHandler;

/**
 * @author Hua_
 * @Description: 异常处理
 * @date 2015/5/21 - 17:29
 */
public class AppHttpExceptionHandler extends HttpExceptionHandler {
    protected Activity activity;

    public AppHttpExceptionHandler via(Activity activity){
        this.activity=activity;
        return this;
    }

    @Override
    protected void onClientException(HttpClientException e, HttpClientException.ClientException e1) {
        // 客户端异常
        if (CommonUtil.notNull(activity)){
            new Toastor(activity).showSingletonToast("客户端异常...");
        }else {

        }

    }

    @Override
    protected void onNetException(HttpNetException e, HttpNetException.NetException e1) {
        // 网络异常
        if (CommonUtil.notNull(activity)){
            new Toastor(activity).showSingletonToast("网络异常...");
        }else {
        }
    }

    @Override
    protected void onServerException(HttpServerException e, HttpServerException.ServerException e1, HttpStatus httpStatus) {
        //服务异常
        if (CommonUtil.notNull(activity)){
            new Toastor(activity).showSingletonToast("服务异常...");
        }else {
        }
    }
}
