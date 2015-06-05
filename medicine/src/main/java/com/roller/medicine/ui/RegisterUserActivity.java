package com.roller.medicine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;

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
import com.roller.medicine.utils.Constants;
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


	private MedicineDataService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
	}

	@Override
	protected void onBackActivty() {
		super.onBackActivty();
	}

	@Override
	protected void initView() {
		super.initView();
		setBackActivity("基本信息");
		service=new MedicineDataService();
		Intent intent=getIntent();
		tel=getIntent().getStringExtra(Constants.DATA_TEL);
		code=getIntent().getStringExtra(Constants.DATA_CODE);

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
		try {
			service.requestRigster(tel, et_name.getText().toString(), et_pwd.getText().toString(), code, et_invite_code.getText().toString(), new SimpleResponseListener() {
				@Override
				public void requestSuccess(BaseInfo info, String result) {
					TokenInfo tokenInfo= JSON.parseObject(result,TokenInfo.class);
					tokenInfo.tel=tel;
					service.setToken(tokenInfo);
					try {
						service.requestUserInfo(new SimpleResponseListener() {
							@Override
							public void requestSuccess(BaseInfo info, String result) {
								UserResponseInfo userInfo=JSON.parseObject(result,UserResponseInfo.class);
								if (userInfo.user!=null){
									service.saveUser(userInfo.user);
									//登陆成功
									ViewUtil.startTopActivity(HomeActivity.class, RegisterUserActivity.this);
									return;
								}
								showLongMsg(" 获取个人信息失败....");
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
			e.printStackTrace();
			hideLoading();
		}

	}


}


