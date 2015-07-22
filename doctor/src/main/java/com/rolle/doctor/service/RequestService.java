package com.rolle.doctor.service;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Intent;
import android.os.Build;

import com.android.common.domain.ResponseMessage;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.event.BaseEvent;
import com.rolle.doctor.viewmodel.RequestTag;
import com.rolle.doctor.viewmodel.UserModel;
import de.greenrobot.event.EventBus;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RequestService extends IntentService{

    private UserModel userModel;
    private static final String TAG="RequestService";
	public RequestService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int tag=intent.getIntExtra(RequestTag.TAG,0);
		switch (tag){
			case RequestTag.R_USER_UP:
				userModel.requestUserInfo(new SimpleResponseListener<User>() {
					@Override
					public void requestSuccess(User info, Response response) {
						EventBus.getDefault().post(new BaseEvent(BaseEvent.EV_USER_INFO));
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
        userModel=new UserModel(getApplication());
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
