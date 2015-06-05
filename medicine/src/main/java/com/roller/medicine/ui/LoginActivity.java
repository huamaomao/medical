package com.roller.medicine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.alibaba.fastjson.JSON;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.lidroid.xutils.exception.HttpException;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.httpservice.MedicineDataService;
import com.roller.medicine.info.BaseInfo;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.info.UserResponseInfo;
import com.roller.medicine.myinterface.SimpleResponseListener;
import com.roller.medicine.service.MedicineGotyeService;
import com.roller.medicine.widget.InputMethodLinearLayout;
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

	private MedicineDataService service;

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

		service=new MedicineDataService();
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
		try {
			service.requestLogin(et_tel.getText().toString(), et_pwd.getText().toString(), new SimpleResponseListener() {
				@Override
				public void requestSuccess(BaseInfo info, String result) {
					TokenInfo tokenInfo= JSON.parseObject(result,TokenInfo.class);
					tokenInfo.tel=et_tel.getText().toString();
					service.setToken(tokenInfo);
					service.getToken();
					try {
						service.requestUserInfo(new SimpleResponseListener() {
							@Override
							public void requestSuccess(BaseInfo info, String result) {
								UserResponseInfo userInfo=JSON.parseObject(result,UserResponseInfo.class);
								if (userInfo.user!=null){
									service.saveUser(userInfo.user);
									//登陆成功
									doMainActivty();
									return;
								}
								showLongMsg(" 获取个人信息失败....");
						}

							@Override
							public void requestError(HttpException e, BaseInfo info) {
								showLongMsg("登陆异常....");
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
					hideLoading();
				}

			});
		}catch (Exception e){
			hideLoading();
		}
	}

	public void doMainActivty(){
		finish();
		startService(new Intent(this, MedicineGotyeService.class));
		ViewUtil.startTopActivity(HomeActivity.class, LoginActivity.this);
	}
}


