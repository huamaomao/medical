package com.roller.medicine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.roller.medicine.event.BaseEvent;
import com.roller.medicine.event.WeixinEvent;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.info.UserResponseInfo;
import com.roller.medicine.service.MedicineGotyeService;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


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
	private IWXAPI iwxipi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		EventBus.getDefault().register(this);
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
			if (tokenInfo.isLogin()){
				doMainActivty();
			}
			if (CommonUtil.notEmpty(tokenInfo.tel)){
				et_tel.setText(tokenInfo.tel);
				et_tel.setSelection(tokenInfo.tel.length());
				et_pwd.requestFocus();
			}

		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event){
		ViewUtil.onHideSoftInput(this,getCurrentFocus(),event);
		return super.onTouchEvent(event);
	}


	@OnClick(R.id.tv_create_account)
	void onRegister(){
		ViewUtil.openActivity(RegisterTelActivity.class, this);
	}

	@OnClick(R.id.tv_forgot_pwd)
	void onRetrievePassword(){
		ViewUtil.openActivity(RegisterPasswordActivity.class, this);
	}


	@OnClick({R.id.ll_item_1,R.id.btn_next})
	void onWeixin(){
		if(CommonUtil.isNull(iwxipi)){
			iwxipi= WXAPIFactory.createWXAPI(getContext(), AppConstants.APPID_WEIXIN,true);
			iwxipi.registerApp(AppConstants.APPID_WEIXIN);
		}
		if (!iwxipi.isWXAppInstalled()) {
			showMsg("无法授权登陆，请先安装微信");
			return;
		}
		if (CommonUtil.isFastClick())return;
		final SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = "rolle";
		iwxipi.sendReq(req);

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
				info.tel = et_tel.getText().toString();
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
						new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
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
				new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
				hideLoading();
			}

			@Override
			public void requestView() {
				super.requestView();
			}
		});
	}

	public void doMainActivty(){
		startService(new Intent(this, MedicineGotyeService.class));
		ViewUtil.startTopActivity(HomeActivity.class, LoginActivity.this);
		finish();
	}

	/*****
	 * 收到Event 事件
	 * @param event
	 */
	public void onEvent(BaseEvent event){
		if (event instanceof WeixinEvent){
			WeixinEvent weixinEvent=(WeixinEvent)event;
			if (CommonUtil.notNull(weixinEvent)){
				doLogin3rd(weixinEvent.getCode());
			}
		}
	}



	private void doLogin3rd(String code){
		showLoading();
		service.requestLogin(code, new SimpleResponseListener<TokenInfo>() {
			@Override
			public void requestSuccess(TokenInfo info, Response response) {
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
						new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
					}

					@Override
					public void requestView() {
						hideLoading();
					}
				});

			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
				hideLoading();
			}
		});
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}




}


