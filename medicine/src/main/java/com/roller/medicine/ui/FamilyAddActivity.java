package com.roller.medicine.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;

import butterknife.InjectView;
import butterknife.OnClick;


/****
 * 创建新成员
 */
public class FamilyAddActivity extends BaseLoadingToolbarActivity {

	@InjectView(R.id.iv_photo)
	ImageView iv_photo;
	@InjectView(R.id.et_tel)
	EditText et_tel;
	@InjectView(R.id.et_name)
	EditText et_name;
	@InjectView(R.id.tv_date)
	TextView tv_date;
	private DataModel dataModel;
	private String sex=Constants.SEX_BOY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_staff);
	}

	protected void initView() {
		setBackActivity("新建人员");
		dataModel=new DataModel();
	}

	//选择日期
	@OnClick(R.id.ll_item_0)
	void  doChooseDate(){

	}
	
	/**
	 * 添加成员
	 */
	private void saveFamilyGroup(){
		String tel=et_tel.getText().toString();
		String name=et_name.getText().toString();
		String date=tv_date.getText().toString();
		if (CommonUtil.isEmpty(tel)){
			showMsg("输入手机号");
			return;
		}
		if (!CommonUtil.isMobileNO(tel)){
			showMsg("手机格式错误...");
			return;
		}
		if (CommonUtil.isEmpty(name)){
			showMsg("输入称呼");
			return;
		}
		if (CommonUtil.isEmpty(date)){
			showMsg("请选择出生日期");
			return;
		}
		showLoading();
		dataModel.saveFamilyGroup(tel, sex, date, name, new SimpleResponseListener<ResponseMessage>() {
			@Override
			public void requestSuccess(ResponseMessage info, Response response) {
				showLongMsg("添加成功");
				finish();
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				new AppHttpExceptionHandler().via(getContent()).handleException(e,info);
			}

			@Override
			public void requestView() {
				hideLoading();
			}
		});
	}

	/**
	 * 选择男女
	 */
	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			switch (checkedId) {
			case R.id.radio_group_personal_information_man:

				break;
			case R.id.radio_group_personal_information_woman:

				break;
			}
		}
	};


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.toolbar_save:
				setLastClickTime();
				saveFamilyGroup();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_save,menu);
		return super.onCreateOptionsMenu(menu);
	}

}
