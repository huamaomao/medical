package com.android.common.util;

import android.app.Activity;

import com.android.common.domain.ResponseMessage;
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
    protected  Activity activity;

    public  AppHttpExceptionHandler via(Activity activity){
        this.activity=activity;
        return this;
    }
    public HttpExceptionHandler handleException(Exception e,ResponseMessage message) {
        if (message!=null){
            onResponseException(message);
            return this;
        }
        if (e != null) {
            if (e instanceof HttpClientException) {
                HttpClientException ce = ((HttpClientException) e);
                onClientException(ce, ce.getExceptionType());
            } else if (e instanceof HttpNetException) {
                HttpNetException ne = ((HttpNetException) e);
                onNetException(ne, ne.getExceptionType());
            } else if (e instanceof HttpServerException) {
                HttpServerException se = ((HttpServerException) e);
                onServerException(se, se.getExceptionType(), se.getHttpStatus());
            }else {
                HttpClientException ce = new HttpClientException(e);
                onClientException(ce, ce.getExceptionType());
            }
        }else {
            new Toastor(activity).showSingletonToast("请求失败...");
        }

        return this;
    }

    /******
     * 服务器返回处理
     * @param message
     */
    protected void  onResponseException(ResponseMessage message){
        new Toastor(activity).showSingletonToast(message.message);
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
