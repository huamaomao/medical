package com.android.common.util;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.android.common.R;
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
public final class AppHttpExceptionHandler extends HttpExceptionHandler {
    private   Activity activity;
    private View view;
    public  AppHttpExceptionHandler via(Activity activity){
        this.activity=activity;
        return this;
    }

    public  AppHttpExceptionHandler via(View view){
        this.view=view;
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
            show("请求失败...");
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
        show("客户端异常...");
    }

    @Override
    protected void onNetException(HttpNetException e, HttpNetException.NetException e1) {
        // 网络异常
        show("网络异常...");

    }


    @Override
    protected void onServerException(HttpServerException e, HttpServerException.ServerException e1, HttpStatus httpStatus) {
        //服务异常
        show("服务异常...");
    }

    private void show(String msg){
        if (CommonUtil.notNull(view)){
           Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
        }else if (CommonUtil.notNull(activity)){
            try {
               Snackbar.make(activity.getCurrentFocus(), msg, Snackbar.LENGTH_SHORT).setActionTextColor(activity.getResources().getColor(R.color.write)).show();
            }catch (Exception e){
                new Toastor(activity).showSingletonToast(msg);
            }
        }

    }
}
