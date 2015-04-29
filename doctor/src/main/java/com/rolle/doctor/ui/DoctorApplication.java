package com.rolle.doctor.ui;

import android.app.Application;

import com.android.common.util.LiteUtil;
import com.android.common.util.Log;
import com.gotye.api.GotyeAPI;
import com.rolle.doctor.util.Constants;

public class DoctorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LiteUtil.initLite(getApplicationContext());
        GotyeAPI gotyeApi=GotyeAPI.getInstance();
        gotyeApi.init(getApplicationContext(), Constants.QINJIA_KEY);


    }


    private void exceptionHandle(){
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e("AppError",ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

}
