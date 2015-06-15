package com.roller.medicine.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseApplication;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

public class UpdateUserActivity extends BaseLoadingToolbarActivity{

	@InjectView(R.id.et_name)
	 EditText et_name;

	private DataModel dataModel;
	private int position;
	private UserInfo userInfo;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_personal_information);
	}

	protected void initView(){
		String tilte=null;
		dataModel=new DataModel();
		position=getIntent().getIntExtra(Constants.ITEM, 0);
		userInfo=dataModel.getLoginUser();
		switch (position){
			case 1:
				tilte="修改昵称";
				et_name.setHint("请填写昵称");
				if (CommonUtil.notEmpty(userInfo.nickname))
					et_name.setText(userInfo.nickname);
				break;
			case 7:
				tilte="修改体重";
				et_name.setHint("请填写体重");
				if (CommonUtil.notEmpty(userInfo.patientDetail.weight))
					et_name.setText(userInfo.patientDetail.weight);
				break;
			case 8:
				tilte="修改身高";
				et_name.setHint("请填写身高");
				if (CommonUtil.notEmpty(userInfo.patientDetail.height))
					et_name.setText(userInfo.patientDetail.height);
				break;
			case 9:
				tilte="修改病类";
				if (CommonUtil.notEmpty(userInfo.patientDetail.disease))
					et_name.setText(userInfo.patientDetail.disease);
				et_name.setHint("请填写病类");
				break;
		}
		et_name.setSelection(et_name.getText().toString().length());
		setBackActivity(tilte);
	}

	private void doSave(){
		String value=et_name.getText().toString();
		if (CommonUtil.isEmpty(value)){
			return;
		}
		switch (position){
			case 1:
				userInfo.nickname=value;
				break;
			case 7:
				userInfo.patientDetail.weight=value;
				break;
			case 8:
				userInfo.patientDetail.height=value;
				break;
			case 9:
				userInfo.patientDetail.disease=value;
				break;
		}
		showLoading();
		dataModel.saveDoctor(userInfo, new SimpleResponseListener<ResponseMessage>() {
			@Override
			public void requestSuccess(ResponseMessage info, Response response) {
				finish();
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.toolbar_save:
				doSave();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_save, menu);
		return super.onCreateOptionsMenu(menu);
	}


}