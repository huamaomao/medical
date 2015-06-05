package com.roller.medicine.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseActivity;
import com.roller.medicine.base.BaseApplication;
import com.roller.medicine.httpservice.DataService;
import com.roller.medicine.info.PublicOnReturnInfo;

public class NewStaffActivity extends BaseActivity {

	@ViewInject(R.id.text_title)
	private TextView text_title;
	@ViewInject(R.id.image_head)
	private ImageView image_head;
	@ViewInject(R.id.edit_between)
	private EditText edit_between;
	@ViewInject(R.id.edit_phone)
	private EditText edit_phone;
	@ViewInject(R.id.edit_birthday)
	private EditText edit_birthday;
	@ViewInject(R.id.radio_group_personal_information_sex)
	private RadioGroup radioGroupPersonalInformationSex;
	@ViewInject(R.id.radio_group_personal_information_man)
	private RadioButton radioGroupPersonalInformationMan;
	@ViewInject(R.id.radio_group_personal_information_woman)
	private RadioButton radioGroupPersonalInformationWoman;
	
	private String sex = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_staff);
		ViewUtils.inject(this);
		initView();
	}

	private void initView() {
		text_title.setText("新建人员");
		radioGroupPersonalInformationSex.setOnCheckedChangeListener(onCheckedChangeListener);
	}
	
	/**
	 * 添加成员
	 */
	private void saveFamilyGroup(){
		try {
			DataService.getInstance().saveFamilyGroup(this, BaseApplication.TOKEN, edit_phone.getText().toString(),
					sex, edit_birthday.getText().toString(), edit_between.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选择男女
	 */
	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			switch (checkedId) {
			case R.id.radio_group_personal_information_man:
				sex = "0";
				break;
			case R.id.radio_group_personal_information_woman:
				sex = "1";
				break;
			}
		}
	};
	
	@OnClick({ R.id.image_return ,R.id.button_save })
	public void onViewClick(View view) {
		switch (view.getId()) {
		case R.id.image_return:
			onReturn();
			break;

		case R.id.button_save:
			saveFamilyGroup();
			break;
		}
	}

	@Override
	public void onSuccess(String url, String result, int resultCode, Object tag) {
		super.onSuccess(url, result, resultCode, tag);
		if(resultCode != 200){
			PublicOnReturnInfo mInfo = JSON.parseObject(result, PublicOnReturnInfo.class);
			disPlay(mInfo.message);
			return;
		}
		onReturn();
	}
}
