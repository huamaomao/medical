package com.rolle.doctor.ui;

import android.app.Application;

import com.android.common.util.Log;

public class DoctorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e("AppError",ex.getMessage());
                ex.printStackTrace();
            }
        });

    }

}
