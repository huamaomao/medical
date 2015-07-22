package com.roller.medicine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.info.UserResponseInfo;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;

import butterknife.InjectView;


public class RegisterUserActivity extends BaseLoadingToolbarActivity{
	@InjectView(R.id.et_name)
	AppCompatEditText et_name;
	@InjectView(R.id.et_pwd)
	AppCompatEditText et_pwd;
	@InjectView(R.id.et_invite_code)
	AppCompatEditText et_invite_code;

	private String tel;
	private String code;


	private DataModel service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		ViewUtil.onHideSoftInput(this,getCurrentFocus(),event);
		return super.onTouchEvent(event);
	}

	@Override
	protected void onBackActivty() {
		super.onBackActivty();
	}

	@Override
	protected void initView() {
		super.initView();
		setBackActivity("基本信息");
		service=new DataModel();
		Intent intent=getIntent();
		tel=getIntent().getStringExtra(AppConstants.DATA_TEL);
		code=getIntent().getStringExtra(AppConstants.DATA_CODE);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_complete, menu);
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

	private void doRegisterUser(){
		if (CommonUtil.isEmpty(et_name.getText().toString())){
			showLongMsg("请求输入昵称");
			return;
		}
		if (!CommonUtil.checkName(et_name.getText().toString())){
			showLongMsg("昵称格式错误,只支持中英文、数字、下划线和减号（10字符）");
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
		service.requestRigster(tel, et_name.getText().toString(), et_pwd.getText().toString(), code, et_invite_code.getText().toString(), new SimpleResponseListener<TokenInfo>() {
			@Override
			public void requestSuccess(TokenInfo info, Response response) {
				info.tel=tel;
				service.setToken(info);
				service.requestUserInfo(new SimpleResponseListener<UserResponseInfo>() {
					@Override
					public void requestSuccess(UserResponseInfo info, Response response) {
						service.saveUser(info.user);
						//登陆成功
						ViewUtil.startTopActivity(HomeActivity.class, RegisterUserActivity.this);
						return;
					}

					@Override
					public void requestError(HttpException e, ResponseMessage info) {
						new AppHttpExceptionHandler().via(getContext()).handleException
								(e,info);
					}
					@Override
					public void requestView() {
						hideLoading();
					}
				});
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				new AppHttpExceptionHandler().via(getContext()).handleException
						(e,info);
			}
			@Override
			public void requestView() {
				hideLoading();
			}

		});

	}


}


