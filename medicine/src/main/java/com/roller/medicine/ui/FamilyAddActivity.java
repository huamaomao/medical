package com.roller.medicine.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.DateUtil;
import com.android.common.util.Log;
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
import com.roller.medicine.service.RequestService;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.utils.ImageCropUtils;
import com.roller.medicine.viewmodel.DataModel;
import com.roller.medicine.viewmodel.RequestTag;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
	private String sex= AppConstants.SEX_BOY;
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
				}).setInitialDate(DateUtil.getBirthDate())
				.build();

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

	private Uri uri_;
	private boolean flag;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 选取图片的返回值
		if (requestCode == com.android.common.util.Constants.CODE_PIC) {
			if (data != null) {
				Uri uri = data.getData();
				if (uri != null) {
					flag=false;
					Bitmap bitmap = null;
					try {
						bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
						if(bitmap.getRowBytes()*bitmap.getHeight() > 50*1024){
							bitmap = ImageCropUtils.compressImage(bitmap);
							uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
						}
					} catch (FileNotFoundException e) {
						return;
					} catch (IOException e) {
						return;
					}
					//这里做了判断  如果图片大于 512KB 就进行压缩
					flag=true;
					File file=new File(AppConstants.PATH + "picture/thumb_"+System.currentTimeMillis() + ".png");
					if (!file.getParentFile().exists())
						file.getParentFile().mkdirs();
					uri_=Uri.fromFile(file);
					startActivityForResult(ImageCropUtils.getCropImageIntent(uri,uri_),3);
				}
			}
		}else if (requestCode==3){
			if (CommonUtil.notNull(uri_)){
				Log.d("uri" + uri_.getPath());
				//Uri uri = data.getData();
				String photoUrl= ViewUtil.getRealFilePath(getContext(), uri_);
				uploadPhoto(photoUrl);
			}

		}

	}


	private void uploadPhoto(String url) {
		Picasso.with(getContext()).load(new File(url)).placeholder(R.drawable.icon_default).
				transform(new CircleTransform()).into(iv_photo);
		dataModel.uploadPicture("71", url, new SimpleResponseListener<UploadPicture>() {
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
				Intent intent=new Intent(getContext(), RequestService.class);
				intent.putExtra(RequestTag.TAG,RequestTag.R_FAMILY);
				getContext().startService(intent);
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
				if (CommonUtil.isFastClick())return true;
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

	@Override
	public boolean onTouchEvent(MotionEvent event){
		ViewUtil.onHideSoftInput(this,getCurrentFocus(),event);
		return super.onTouchEvent(event);
	}
}
