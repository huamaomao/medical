package com.roller.medicine.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeNotify;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.gotye.api.listener.NotifyListener;
import com.lidroid.xutils.util.LogUtils;
import com.roller.medicine.R;
import com.roller.medicine.ui.HomeActivity;
import com.roller.medicine.httpservice.Constants;
import com.roller.medicine.utils.AppUtils;
import com.roller.medicine.utils.OtherUtils;
import com.roller.medicine.utils.SharedPreferencesUtils;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GotyeService extends Service implements NotifyListener {
	public static final String ACTION_LOGIN = "gotyeim.login";
	public static final String ACTION_RUN_BACKGROUND = "gotyeim.login";
	private GotyeAPI api;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		LogUtils.i("GotyeService-onCreate");
		api = GotyeAPI.getInstance();
		int code = api.init(getBaseContext(), Constants.CHAT.APPKEY);
		api.beginReceiveOfflineMessage();
		api.addListener(this);
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtils.i("onStartCommand--------");
		LogUtils.i("flags=" + flags);
		if (intent != null) {
			if (ACTION_LOGIN.equals(intent.getAction())) {
				String name = intent.getStringExtra("name");
				String pwd = intent.getStringExtra("pwd");
				SharedPreferencesUtils.setSharedParam(getApplicationContext(),
						"name", name);
				if (pwd != null)
					SharedPreferencesUtils.setSharedParam(
							getApplicationContext(), "pwd", pwd);
				int code = api.login(name, pwd);
				LogUtils.i("Service准备登陆:" + name + "-" + pwd + "-" + code);
				if (code == GotyeStatusCode.CodeSystemBusy) {
					// 已经登陆了
					LogUtils.i("GotyeService-已经登陆了");
				}
			}
		} else {
//			LoginUserInfo loginUserInfo = BaseApplication.getInstance()
//					.getLoginUserInfo();
			String name = null;
			String pwd = null;
//			if (loginUserInfo == null) {
			if (true) {
				name = (String) SharedPreferencesUtils.getSharedParam(
						getApplicationContext(), Constants.PARAM.USERNAME, "-1");
				pwd = OtherUtils.getBase64DelTipPwd(getApplicationContext());
				if ("-1".equals(name)) {
					return super.onStartCommand(intent, flags, startId);
				}
				LogUtils.i("SharedPreferences-获取用户名密码登陆");
			} else {
//				name = loginUserInfo.getUsername();
//				pwd = Base64Util.decode(loginUserInfo.getUserpwd());
			}
			String[] user = new String[] { name, pwd };
			if (!TextUtils.isEmpty(user[0])) {
				int code = api.login(user[0], user[1]);
			}
		}
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		try {
			// GotyeAPI.getInstance().removeListener(this);
			Log.i("mygotye", "gotyeservice-onDestory-restartService");
			Intent localIntent = new Intent();
			localIntent.setClass(this, GotyeService.class); // 銷毀時重新啟動Service 流氓
			this.startService(localIntent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void notify(String msg) {
		String currentPackageName = AppUtils.getTopAppPackage(getBaseContext());
		if (currentPackageName.equals(getPackageName())) {
			return;
		}
		NotificationManager notificationManager = (NotificationManager) this
				.getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
		Notification notification = new Notification(R.drawable.ic_launcher,
				msg, System.currentTimeMillis());
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		/** 点击亲加通知跳转的界面 */
		Intent intent = new Intent(this, HomeActivity.class);
		intent.putExtra("notify", 1);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, getString(R.string.app_name),
				msg, pendingIntent);
		notificationManager.notify(0, notification);

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
}
