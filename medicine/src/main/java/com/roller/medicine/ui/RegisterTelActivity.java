package com.roller.medicine.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;

import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterTelActivity extends BaseLoadingToolbarActivity{

	@InjectView(R.id.et_tel)
	AppCompatEditText et_tel;
	@InjectView(R.id.et_code)
	EditText et_code;
	@InjectView(R.id.btn_send)
	Button btn_send;

	private DataModel service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_one);
	}

	@Override
	protected void initView() {
		super.initView();
		setBackActivity("注册");
		service=new DataModel();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		ViewUtil.onHideSoftInput(this,getCurrentFocus(),event);
		return super.onTouchEvent(event);
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
		if (et_code.getText().toString().length()<4){
			showLongMsg("请填写4位数字验证码");
			return;
		}
		showLoading();
		service.requestCheckTelCode(et_tel.getText().toString(), et_code.getText().toString(), new SimpleResponseListener<ResponseMessage>() {
			@Override
			public void requestSuccess(ResponseMessage info, Response response) {
				Bundle bundle=new Bundle();
				bundle.putString(AppConstants.DATA_TEL,et_tel.getText().toString());
				bundle.putString(AppConstants.DATA_CODE, et_code.getText().toString());
				ViewUtil.openActivity(RegisterUserActivity.class, bundle, RegisterTelActivity.this, ActivityModel.ACTIVITY_MODEL_1);
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				new AppHttpExceptionHandler().via(getContext()).handleException
						(e, info);
			}

			@Override
			public void requestView() {
				hideLoading();
			}
		});

	}


	public void timeSendStart(){
		downTimer.start();
	}


	/****
	 * 计时器
	 */
	private CountDownTimer downTimer=new CountDownTimer(AppConstants.SMS_SEBD_TIME,1000) {
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

		service.requestSendSms(et_tel.getText().toString(), "83", new SimpleResponseListener<ResponseMessage>() {
			@Override
			public void requestSuccess(ResponseMessage info, Response response) {
				showLongMsg("发送成功");
				timeSendStart();
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				new AppHttpExceptionHandler().via(getContext()).handleException
						(e,info);
			}
		});


	}
}


