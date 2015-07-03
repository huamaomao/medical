package com.roller.medicine.base;

import android.app.Application;

import com.android.common.util.CommonExceptionHandler;
import com.android.common.util.LiteUtil;
import com.android.common.util.Log;
import com.gotye.api.GotyeAPI;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

public class BaseApplication extends Application {

	private CommonExceptionHandler exceptionHandler;

	@Override
	public void onCreate() {
		super.onCreate();
		DataModel.initLiteOrm(getApplicationContext());
		GotyeAPI gotyeApi=GotyeAPI.getInstance();
		LiteUtil.initLite(getApplicationContext());
		gotyeApi.init(getApplicationContext(), AppConstants.QINJIA_KEY);

		Picasso.with(getBaseContext()).setLoggingEnabled(true);
		exceptionHandle();
	}


	private void exceptionHandle(){
		exceptionHandler=new CommonExceptionHandler();
		/*Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Log.e("AppError", ex.getMessage());
				//ex.printStackTrace();
				 Log.e("app",exceptionHandler.handleException(ex,getApplicationContext()).toString());
			}
		});*/
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
