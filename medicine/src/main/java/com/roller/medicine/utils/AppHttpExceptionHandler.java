package com.roller.medicine.utils;

import android.app.Activity;

import com.litesuits.http.data.HttpStatus;
import com.litesuits.http.exception.HttpClientException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.response.handler.HttpExceptionHandler;

/**
 * @author Hua_
 * @Description:
 * @date 2015/5/21 - 17:29
 */
public class AppHttpExceptionHandler extends HttpExceptionHandler {
    protected Activity activity;


    @Override
    protected void onClientException(HttpClientException e, HttpClientException.ClientException e1) {
        //
    }

    @Override
    protected void onNetException(HttpNetException e, HttpNetException.NetException e1) {
        //
    }

    @Override
    protected void onServerException(HttpServerException e, HttpServerException.ServerException e1, HttpStatus httpStatus) {
        //
    }
}
