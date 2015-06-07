package com.roller.medicine.base;

import android.app.Application;

import com.android.common.util.LiteUtil;
import com.android.common.util.Log;
import com.gotye.api.GotyeAPI;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;

public class BaseApplication extends Application {

	/** 这个J8玩意儿不能随便改, 改之前先确认对方code的一致! */
	public static final String DEBUG_CODE = "修改IM服务|发现刷新";


	public static final String TOKEN = "6";
	public static final String USERID = "6";

	@Override
	public void onCreate() {
		super.onCreate();
		DataModel.initLiteOrm(getApplicationContext());
		GotyeAPI gotyeApi=GotyeAPI.getInstance();
		LiteUtil.initLite(getApplicationContext());
		gotyeApi.init(getApplicationContext(), Constants.QINJIA_KEY);

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

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
