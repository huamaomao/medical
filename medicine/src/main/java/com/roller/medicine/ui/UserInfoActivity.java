package com.roller.medicine.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.common.util.ActivityModel;
import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseToolbarActivity{
	
	@InjectView(R.id.text_nickname)
	 TextView text_nickname;
	@InjectView(R.id.text_phone)
	 TextView text_phone;
	@InjectView(R.id.text_describe)
	 TextView text_describe;
	@InjectView(R.id.text_sex)
	 TextView text_sex;
	@InjectView(R.id.text_birthday)
	 TextView text_birthday;
	@InjectView(R.id.text_weight)
	 TextView text_weight;
	@InjectView(R.id.text_height)
	 TextView text_height;
	@InjectView(R.id.text_disease)
	 TextView text_disease;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_information);
	}
	
	protected void initView(){
		setBackActivity("个人信息");
		initData();
	}
	
	private void initData(){
	/*	try {
			DataService.getInstance().getUserByToken(this, BaseApplication.TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	
	@OnClick({R.id.relativelayout_nickname,R.id.relativelayout_phone
		,R.id.relativelayout_describe})
	public void onViewOnClick(View view){
		Bundle bundle = null;
		switch (view.getId()) {

			
		case R.id.relativelayout_nickname:

			ViewUtil.openActivity(UpdatePersonalInformationActivity.class,getContext() , ActivityModel.ACTIVITY_MODEL_2);
			break;
			
		case R.id.relativelayout_phone:
			ViewUtil.openActivity(UpdatePersonalInformationActivity.class, getContext(), ActivityModel.ACTIVITY_MODEL_2);
			break;
			
		case R.id.relativelayout_describe:
			ViewUtil.openActivity(UpdatePersonalInformationActivity.class, getContext(), ActivityModel.ACTIVITY_MODEL_2);
			break;

		}
	}
	
/*	@Override
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
	}*/
	
}
