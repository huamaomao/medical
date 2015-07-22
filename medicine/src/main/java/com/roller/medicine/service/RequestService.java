package com.roller.medicine.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.viewmodel.SimpleResponseListener;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeNotify;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.gotye.api.listener.LoginListener;
import com.gotye.api.listener.NotifyListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.event.BaseEvent;
import com.roller.medicine.info.FamilytInfo;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;
import com.roller.medicine.viewmodel.RequestTag;

import de.greenrobot.event.EventBus;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RequestService extends IntentService{

    private DataModel userModel;
    private static final String TAG="RequestService";
	public RequestService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int tag=intent.getIntExtra(RequestTag.TAG,0);
		switch (tag){
			case RequestTag.R_FAMILY:
				userModel.getFamilyListByMap(new SimpleResponseListener<FamilytInfo>() {
					@Override
					public void requestSuccess(FamilytInfo info, Response response) {
						userModel.ormSaveFamily(info.list);
						EventBus.getDefault().post(new BaseEvent(BaseEvent.EV_FAMILY));
					}

					@Override
					public void requestError(HttpException e, ResponseMessage info) {

					}
				});
				break;
		}

	}




	@Override
	public void onCreate() {
		super.onCreate();
        userModel=new DataModel();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
