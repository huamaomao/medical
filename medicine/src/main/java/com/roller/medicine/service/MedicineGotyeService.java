package com.roller.medicine.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;

import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeNotify;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.gotye.api.listener.LoginListener;
import com.gotye.api.listener.NotifyListener;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MedicineGotyeService extends Service implements NotifyListener,LoginListener {
	private GotyeAPI api;
    private DataModel userModel;
    private static final String TAG="GotyeService";

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		api = GotyeAPI.getInstance();
		api.init(getBaseContext(), AppConstants.QINJIA_KEY);
		api.beginReceiveOfflineMessage();
		api.addListener(this);
        userModel=new DataModel();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        TokenInfo token=userModel.getToken();
		//Log.e(TAG, "开始登陆了......" + token+api.isOnLine()==GotyeUser.LOGIN_STATE_ONLINE);
        if (CommonUtil.notNull(token)&&api.isOnLine()==GotyeUser.LOGIN_STATE_ONLINE){
            int code = api.login(token.id+"");
			Log.e(TAG, "开始登陆了......" + code + "==getLoginUser==" + api.getLoginUser().getName());
            if (code == GotyeStatusCode.CodeSystemBusy) {
                // 已经登陆了
                Log.e(TAG, "已经登陆登陆了......");
            }

        }
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		/*try {
			// GotyeAPI.getInstance().removeListener(this);
			Intent localIntent = new Intent();
			localIntent.setClass(this, GotyeService.class); // 銷毀時重新啟動Service 流氓
			this.startService(localIntent);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		super.onDestroy();
	}

	@SuppressLint("NewApi")
	private void notify(String msg) {
		/*String currentPackageName = AppUtil.getTopAppPackage(getBaseContext());
		if (currentPackageName.equals(getPackageName())) {
			return;
		}
		NotificationManager notificationManager = (NotificationManager) this
				.getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
		Notification notification = new Notification(R.drawable.logo,
				msg, System.currentTimeMillis());
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		Intent intent = new Intent(this, LoadingActivity.class);
		intent.putExtra("notify", 1);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, getString(R.string.app_name),
				msg, pendingIntent);
		notificationManager.notify(0, notification);*/

	}

	@Override
	public void onReceivePushMessage(GotyeMessage message) {
		String msg = null;
		// switch (message.getType()) {
		// case GotyeMessageTypeText:
		// msg = message.getSender().getName() + "发来了一条消息";
		// break;
		// case GotyeMessageTypeImage:
		// msg = message.getSender().getName() + "发来了一条图片消息";
		// break;
		// case GotyeMessageTypeAudio:
		// msg = message.getSender().getName() + "发来了一条语音消息";
		// break;
		// case GotyeMessageTypeUserData:
		// msg = message.getSender().getName() + "发来了一条自定义消息";
		// break;
		// default:
		// msg = message.getSender().getName() + "发来了一条群邀请信息";
		// break;
		// }
		msg = "收到一条消息";
		notify(msg);
	}

	@Override
	public void onReceiveNotify(GotyeNotify notify) {
		String msg = notify.getSender().getName() + "邀请您加入群[";
		if (!TextUtils.isEmpty(notify.getFrom().getName())) {
			msg += notify.getFrom().getName() + "]";
		} else {
			msg += notify.getFrom().getId() + "]";
		}
		notify(msg);
	}

	@Override
	public void onNotifyStateChanged() {

	}

	@Override
	public void onFriendChanged(boolean arg0, GotyeUser arg1) {

	}

	@Override
	public void onLogout(int code) {

	}

	@Override
	public void onLogin(int code, GotyeUser currentLoginUser) {
		Log.e(TAG, "onLogin......" + code + ".currentLoginUser..." + currentLoginUser.getName());
	}

	@Override
	public void onReconnecting(int code, GotyeUser currentLoginUser) {

	}
}
