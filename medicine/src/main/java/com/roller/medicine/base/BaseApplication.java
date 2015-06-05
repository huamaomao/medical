package com.roller.medicine.base;

import android.app.Application;

import com.android.common.util.Log;
import com.gotye.api.GotyeAPI;
import com.roller.medicine.httpservice.MedicineDataService;
import com.roller.medicine.utils.Constants;

public class BaseApplication extends Application {

	/** 这个J8玩意儿不能随便改, 改之前先确认对方code的一致! */
	public static final String DEBUG_CODE = "修改IM服务|发现刷新";
	
	public static BaseApplication application;
	
	public static final String LOCAL_CACHE = "workpai.cache";
	public static final String LOCAL_DEBUG = "workpai.debug";
	
	public static final String TOKEN = "6";
	public static final String USERID = "6";
	
	public static BaseApplication getInstance() {
		if (application == null) {
			application = new BaseApplication();
		}
		return application;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		//调用JPush API设置Alias
		application = this;
		MedicineDataService.initLiteOrm(getApplicationContext());
		GotyeAPI gotyeApi=GotyeAPI.getInstance();
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
