package com.roller.medicine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.widget.InputMethodLinearLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.info.UserResponseInfo;
import com.roller.medicine.service.MedicineGotyeService;
import com.roller.medicine.viewmodel.DataModel;

import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends BaseLoadingToolbarActivity{

	@InjectView(R.id.ll_login)
	InputMethodLinearLayout llLogin;
	@InjectView(R.id.iv_photo)
	ImageView ivLogo;
	@InjectView(R.id.et_tel)
	EditText et_tel;
	@InjectView(R.id.et_pwd)
	EditText et_pwd;
	private DataModel service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	protected void initView(){
		super.initView();
		llLogin.setOnSizeChangedListenner(new InputMethodLinearLayout.OnSizeChangedListenner() {
			@Override
			public void onSizeChange(boolean paramBoolean, int w, int h) {
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.gravity = Gravity.CENTER_HORIZONTAL;
				if (paramBoolean) {
					lp.setMargins(0, ViewUtil.px2dip(LoginActivity.this, 80f), 0, 0);
					ivLogo.setLayoutParams(lp);
				} else {
					lp.setMargins(0, ViewUtil.px2dip(LoginActivity.this, 640f), 0, 0);
					ivLogo.setLayoutParams(lp);
				}
			}
		});

		service=new DataModel();
		loadingFragment.setLoginMessage();
		TokenInfo tokenInfo=service.getToken();
		if (CommonUtil.notNull(tokenInfo)){
			et_tel.setText(tokenInfo.tel);
			et_tel.setSelection(tokenInfo.tel.length());
			et_pwd.requestFocus();
			if (tokenInfo.isLogin()){
				doMainActivty();
			}
		}
	}

	@OnClick(R.id.tv_create_account)
	void onRegister(){
		ViewUtil.openActivity(RegisterTelActivity.class, this);
	}

	@OnClick(R.id.tv_forgot_pwd)
	void onRetrievePassword(){
		ViewUtil.openActivity(RegisterPasswordActivity.class,this);
	}

	@OnClick(R.id.btn_login)
	void onLogin(){
		if (CommonUtil.isEmpty(et_tel.getText().toString())){
			showLongMsg("请输入手机号");
			return;
		}
		if (!CommonUtil.isMobileNO(et_tel.getText().toString())){
			showLongMsg("手机格式错误");
			return;
		}
		if (CommonUtil.isEmpty(et_pwd.getText().toString())){
			showLongMsg("请输入密码");
			return;
		}
		if (!CommonUtil.checkPassword(et_pwd.getText().toString())){
			showLongMsg("密码格式错误,6-15位");
			return;
		}
		showLoading();
		service.requestLogin(et_tel.getText().toString(), et_pwd.getText().toString(), new SimpleResponseListener<TokenInfo>() {
			@Override
			public void requestSuccess(TokenInfo info, Response response) {
				info.tel=et_tel.getText().toString();
				service.setToken(info);
				service.requestUserInfo(new SimpleResponseListener<UserResponseInfo>() {
					@Override
					public void requestSuccess(UserResponseInfo info, Response response) {
						service.saveUser(info.user);
						//登录成功
						doMainActivty();
						return;
					}

					@Override
					public void requestError(HttpException e, ResponseMessage info) {
						new AppHttpExceptionHandler().via(getContext()).handleException(e,info);
					}

					@Override
					public void requestView() {
						super.requestView();
						hideLoading();
					}
				});
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				new AppHttpExceptionHandler().via(getContext()).handleException(e,info);
			}

			@Override
			public void requestView() {
				super.requestView();
				hideLoading();
			}
		});
	}

	public void doMainActivty(){
		finish();
		startService(new Intent(this, MedicineGotyeService.class));
		ViewUtil.startTopActivity(HomeActivity.class, LoginActivity.this);
	}
}


