package com.roller.medicine.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseActivity;
import com.roller.medicine.base.BaseApplication;
import com.roller.medicine.httpservice.Constants;
import com.roller.medicine.httpservice.DataService;
import com.roller.medicine.info.MyPersonalInformationInfo;

public class PersonalInformationActivity extends BaseActivity{
	
	@ViewInject(R.id.text_nickname)
	private TextView text_nickname;
	@ViewInject(R.id.text_phone)
	private TextView text_phone;
	@ViewInject(R.id.text_describe)
	private TextView text_describe;
	@ViewInject(R.id.text_title)
	private TextView text_title;
	@ViewInject(R.id.text_sex)
	private TextView text_sex;
	@ViewInject(R.id.text_birthday)
	private TextView text_birthday;
	@ViewInject(R.id.text_weight)
	private TextView text_weight;
	@ViewInject(R.id.text_height)
	private TextView text_height;
	@ViewInject(R.id.text_disease)
	private TextView text_disease;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_information);
		ViewUtils.inject(this);
		initView();
	}
	
	private void initView(){
		text_title.setText("个人信息");
		initData();
	}
	
	private void initData(){
		try {
			DataService.getInstance().getUserByToken(this, BaseApplication.TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@OnClick({R.id.image_return,R.id.relativelayout_nickname,R.id.relativelayout_phone
		,R.id.relativelayout_describe})
	public void onViewOnClick(View view){
		Bundle bundle = null;
		switch (view.getId()) {
		case R.id.image_return:
			onReturn();
			break;
			
		case R.id.relativelayout_nickname:
			bundle = new Bundle();
			bundle.putString(Constants.ID, Constants.NICKNAME);
			bundle.putString(Constants.CONTENT, text_nickname.getText().toString());
			openActivity(UpdatePersonalInformationActivity.class, bundle);
			break;
			
		case R.id.relativelayout_phone:
			bundle = new Bundle();
			bundle.putString(Constants.ID, Constants.PHONE);
			bundle.putString(Constants.CONTENT, text_phone.getText().toString());
			openActivity(UpdatePersonalInformationActivity.class, bundle);
			break;
			
		case R.id.relativelayout_describe:
			bundle = new Bundle();
			bundle.putString(Constants.ID, Constants.DESCRIBE);
			bundle.putString(Constants.CONTENT, text_describe.getText().toString());
			openActivity(UpdatePersonalInformationActivity.class, bundle);
			break;

		}
	}
	
	@Override
	public void onSuccess(String url, String result, int resultCode, Object tag) {
		super.onSuccess(url, result, resultCode, tag);
		if(resultCode == 200){
			MyPersonalInformationInfo mInfo = JSON.parseObject(result, MyPersonalInformationInfo.class);
			text_nickname.setText(mInfo.getNickname());
			text_phone.setText(mInfo.getMobile());
			text_describe.setText(mInfo.getDescribe());
			text_birthday.setText(mInfo.getBirthday());
			text_weight.setText(mInfo.getWeight());
			text_height.setText(mInfo.getHeight());
			text_disease.setText(mInfo.getDisease());
			text_sex.setText(mInfo.getSex());
		}
	}
	
}
