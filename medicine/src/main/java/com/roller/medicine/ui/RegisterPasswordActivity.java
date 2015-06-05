package com.roller.medicine.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.lidroid.xutils.exception.HttpException;
import com.litesuits.android.log.Log;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.httpservice.MedicineDataService;
import com.roller.medicine.info.BaseInfo;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.info.UserResponseInfo;
import com.roller.medicine.myinterface.SimpleResponseListener;
import com.roller.medicine.utils.Constants;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterPasswordActivity extends BaseLoadingToolbarActivity{

	@InjectView(R.id.et_tel)
	AppCompatEditText et_tel;
	@InjectView(R.id.et_pwd)
	EditText et_pwd;
	@InjectView(R.id.et_code)
	EditText et_code;
	@InjectView(R.id.btn_send)
	Button btn_send;

	private MedicineDataService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_password_change);
	}

	@Override
	protected void initView() {
		super.initView();
		setBackActivity("找回密码");
		service=new MedicineDataService();
	}

	@OnClick(R.id.btn_send)
	 void onSendsms(){
		doSendSms();
	}

	void doRegisterUser(){
		if (!CommonUtil.isMobileNO(et_tel.getText().toString())){
			showLongMsg("请填写正确的手机号");
			return;
		}
		if (CommonUtil.isEmpty(et_code.getText().toString())){
			showLongMsg("请填写验证码");
			return;
		}
		if (et_code.getText().toString().length()<6){
			showLongMsg("请填写6位数字验证码");
			return;
		}
		if (CommonUtil.isEmpty(et_pwd.getText().toString())){
			showLongMsg("请求输入密码");
			return;
		}
		if (!CommonUtil.checkPassword(et_pwd.getText().toString())){
			showLongMsg("密码格式错误,6-15位");
			return;
		}
		showLoading();
		try {
			service.requestPassword(et_tel.getText().toString(),et_pwd.getText().toString() ,et_code.getText().toString(), new SimpleResponseListener() {
				@Override
				public void requestSuccess(BaseInfo info, String result) {
					//成功
					TokenInfo tokenInfo= JSON.parseObject(result,TokenInfo.class);
					tokenInfo.tel=et_tel.getText().toString();
					service.setToken(tokenInfo);
					try {
						service.requestUserInfo(new SimpleResponseListener() {
							@Override
							public void requestSuccess(BaseInfo info, String result) {
								UserResponseInfo userInfo = JSON.parseObject(result, UserResponseInfo.class);
								if (userInfo.user != null) {
									service.saveUser(userInfo.user);
									//登陆成功
									ViewUtil.startTopActivity(HomeActivity.class, RegisterPasswordActivity.this);
									return;
								}
								showLongMsg(" 获取个人信息失败....");
							}

							@Override
							public void requestError(HttpException e, BaseInfo info) {
								if (CommonUtil.notNull(info)) {
									showLongMsg(info.message);
								} else if (CommonUtil.notNull(e)) {
									showLongMsg(e.getMessage());
								}
							}

							@Override
							public void requestView() {
								hideLoading();
							}
						});
					}catch (Exception e){
						hideLoading();
					}

				}

				@Override
				public void requestError(HttpException e, BaseInfo info) {
					if (CommonUtil.notNull(info)){
						showLongMsg(info.message);
					}else if (CommonUtil.notNull(e)){
						showLongMsg(e.getMessage());
					}
				}

				@Override
				public void requestView() {
					hideLoading();
				}
			});
		}catch (Exception e){
			hideLoading();
		}

	}


	public void timeSendStart(){
		downTimer.start();
	}


	/****
	 * 计时器
	 */
	private CountDownTimer downTimer=new CountDownTimer(Constants.SMS_SEBD_TIME,1000) {
		@Override
		public void onTick(long millisUntilFinished) {
			btn_send.setEnabled(false);
			StringBuilder builder=new StringBuilder();
			builder.append(millisUntilFinished/1000);
			builder.append("s");
			btn_send.setTextColor(getResources().getColor(R.color.public_white));
			btn_send.setText(builder.toString());
		}

		@Override
		public void onFinish() {
			btn_send.setText("没有收到?");
			btn_send.setEnabled(true);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_next, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.toolbar_next:
				doRegisterUser();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void doSendSms(){
		if (!CommonUtil.isMobileNO(et_tel.getText().toString())){
			showLongMsg("请填写正确的手机号");
			return;
		}
		try {
			timeSendStart();
			service.requestSendSms(et_tel.getText().toString(), "84", new SimpleResponseListener() {
				@Override
				public void requestSuccess(BaseInfo info, String result) {
					Log.d("onSuccess:" + result);
					showLongMsg("发送成功");
				}

				@Override
				public void requestError(HttpException e, BaseInfo info) {
					showLongMsg("发送失败...");
					downTimer.onFinish();
				}
			});
		}catch (Exception e){

		}
	}

}


