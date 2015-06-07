package com.roller.medicine.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarActivity;

import butterknife.InjectView;

public class NewStaffActivity extends BaseToolbarActivity {


	@InjectView(R.id.image_head)
	 ImageView image_head;
	@InjectView(R.id.edit_between)
	 EditText edit_between;
	@InjectView(R.id.edit_phone)
	 EditText edit_phone;
	@InjectView(R.id.edit_birthday)
	 EditText edit_birthday;
	@InjectView(R.id.radio_group_personal_information_sex)
	 RadioGroup radioGroupPersonalInformationSex;
	@InjectView(R.id.radio_group_personal_information_man)
	 RadioButton radioGroupPersonalInformationMan;
	@InjectView(R.id.radio_group_personal_information_woman)
	 RadioButton radioGroupPersonalInformationWoman;
	
	private String sex = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_staff);
	}

	protected void initView() {
		setBackActivity("新建人员");
		radioGroupPersonalInformationSex.setOnCheckedChangeListener(onCheckedChangeListener);
	}
	
	/**
	 * 添加成员
	 */
	private void saveFamilyGroup(){
		/*try {
			DataService.getInstance().saveFamilyGroup(this, BaseApplication.TOKEN, edit_phone.getText().toString(),
					sex, edit_birthday.getText().toString(), edit_between.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
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
	

}
