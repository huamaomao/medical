package com.roller.medicine.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.DateUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.UploadPicture;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;

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
	private SlideDateTimePicker timePicker;
	private UserInfo user;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_staff);
	}

	protected void initView() {
		super.initView();
		setBackActivity("新建人员");
		dataModel=new DataModel();
		user=new UserInfo();
		timePicker=new SlideDateTimePicker.Builder(getSupportFragmentManager())
				.setListener(new SlideDateTimeListener() {
					@Override
					public void onDateTimeSet(Date date) {
						tv_date.setText(DateUtil.formatYMD(date));
						user.birthday=tv_date.getText().toString();
					}
				}).setInitialDate(new Date())
				.build();
		//Picasso.with(getContext()).load(DataModel.getImageUrl(userInfo.headImage)).transform(new CircleTransform()).placeholder(R.drawable.icon_default).into(iv_photo);

	}


	@OnClick(R.id.iv_photo)
	void onUploadPhoto(){
		ViewUtil.startPictureActivity(getContext());
	}

	//选择日期
	@OnClick(R.id.ll_item_0)
	void  doChooseDate(){
		timePicker.show();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 选取图片的返回值
		if (requestCode == com.android.common.util.Constants.CODE_PIC) {
			if (data != null) {
				Uri uri = data.getData();
				if (uri != null) {
					user.headImage=ViewUtil.getRealFilePath(getContext(), uri);
					uploadPhoto();
				}
			}
		}

	}


	private void uploadPhoto() {
		Picasso.with(getContext()).load(new File(user.headImage)).placeholder(R.drawable.icon_default).
				transform(new CircleTransform()).into(iv_photo);
		dataModel.uploadPicture("71", user.headImage, new SimpleResponseListener<UploadPicture>() {
			@Override
			public void requestSuccess(UploadPicture info, Response response) {
				user.photoId = info.id;
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				showMsg("图片上传失败...");
			}
		});
	}


	/**
	 * 添加成员
	 */
	private void saveFamilyGroup(){
		String tel=et_tel.getText().toString();
		String name=et_name.getText().toString();
		String date=tv_date.getText().toString();

		if (CommonUtil.notEmpty(tel)&&!CommonUtil.isMobileNO(tel)){
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
				new AppHttpExceptionHandler().via(getContext()).handleException(e,info);
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
