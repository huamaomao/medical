package com.rolle.doctor.ui;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.android.common.util.CommonUtil;
import com.android.common.util.LiteUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.gotye.api.GotyeAPI;
import com.rolle.doctor.event.BaseEvent;
import com.rolle.doctor.util.AppConstants;

import de.greenrobot.event.EventBus;

public class DoctorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LiteUtil.initLite(getApplicationContext());
        GotyeAPI gotyeApi=GotyeAPI.getInstance();
        gotyeApi.init(getApplicationContext(), AppConstants.QINJIA_KEY);
        EventBus.getDefault().register(this);

    }


    private void exceptionHandle(){
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e("AppError", ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    public void onEvent(BaseEvent event)
    {
        if (CommonUtil.isNull(event))return;
        if (event.type==BaseEvent.EV_TOKEN_OUT){
            msgLongShow("token过期,请重新登录....");
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
        }

    }

    /**
     * 提示条
     *
     * @param content 提示的内容
     */
    public void msgLongShow(String content) {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }
}
