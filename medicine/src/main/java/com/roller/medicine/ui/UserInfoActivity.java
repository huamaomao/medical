package com.roller.medicine.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
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
import com.roller.medicine.adapter.UserDetialAdapater;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.fragment.SexDialogFragment;
import com.roller.medicine.info.ItemInfo;
import com.roller.medicine.info.UploadPicture;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.info.UserResponseInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.utils.ImageCropUtils;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseToolbarActivity{

	@InjectView(R.id.rv_view)
	RecyclerView rvView;
	private List<ItemInfo> lsData;
	private UserDetialAdapater adapater;
	private DataModel dataModel;
	private UserInfo user;
	private SexDialogFragment sexDialogFragment;
	private SlideDateTimePicker timePicker;

	private String photoUrl=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycker);
	}


	@Override
	protected void onResume() {
		super.onResume();
		loadData();
	}

	protected void initView(){
		setBackActivity("个人信息");
		dataModel=new DataModel();
		lsData=new ArrayList();
		timePicker=new SlideDateTimePicker.Builder(getSupportFragmentManager())
				.setListener(new SlideDateTimeListener() {
					@Override
					public void onDateTimeSet(Date date) {
						user.birthday=DateUtil.formatYMD(date);
						dataModel.saveUser(user);
						dataModel.saveDoctor(user, new SimpleResponseListener<ResponseMessage>() {
							@Override
							public void requestSuccess(ResponseMessage info, Response response) {

							}

							@Override
							public void requestError(HttpException e, ResponseMessage info) {

							}
						});
						loadData();
					}
				}).setInitialDate(new Date())
				.build();
		timePicker.setTheme(R.style.AppToolbarTheme);

		sexDialogFragment=new SexDialogFragment();
		adapater=new UserDetialAdapater(this,lsData);
		ViewUtil.initRecyclerView(rvView, getContext(), adapater);
		rvView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				setLastClickTime();
				switch (position) {
					case 0:
						ViewUtil.startPictureActivity(getContext());
						break;
					case 2:
						sexDialogFragment.show(getSupportFragmentManager(), "sex");
						break;
					case 3:
						timePicker.show();
						break;
					case 1:
					case 7:
					case 8:
					case 9:
						Bundle bundle = new Bundle();
						bundle.putInt(Constants.ITEM, position);
						ViewUtil.openActivity(UpdateUserActivity.class, bundle, getContext());
						break;
					case 5:
						ViewUtil.openActivity(UpdateIntroActivity.class, getContext());
						break;
				}
			}
		}));
		loadData();

	}

	private void loadData(){
		user=dataModel.getLoginUser();
		lsData.clear();
		lsData.add(new ItemInfo());
		lsData.add(new ItemInfo("昵称", CommonUtil.initTextNull(user.nickname)));
		lsData.add(new ItemInfo("性别", CommonUtil.getSex(user.sex)));
		lsData.add(new ItemInfo("出生日期", CommonUtil.initTextNull(user.birthday)));
		lsData.add(new ItemInfo("手机号",CommonUtil.initTextNull(user.tel)));
		lsData.add(new ItemInfo("简介", CommonUtil.initTextNull(user.intro)));
		lsData.add(new ItemInfo("所在地", CommonUtil.initTextNull(user.address)));
		lsData.add(new ItemInfo("体重", CommonUtil.initTextNull(user.patientDetail.weight)));
		lsData.add(new ItemInfo("身高", CommonUtil.initTextNull(user.patientDetail.height)));
		lsData.add(new ItemInfo("病类", CommonUtil.initTextNull(user.patientDetail.disease)));
		adapater.notifyDataSetChanged();
		adapater.setUserDetail(user);

	}




	private Uri uri_;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 选取图片的返回值
		if (requestCode == com.android.common.util.Constants.CODE_PIC) {
			if (data != null) {
				Uri uri = data.getData();
				if (uri != null) {
					/*Bundle bundle=new Bundle();
					bundle.putString(Constants.ITEM, ViewUtil.getRealFilePath(getContext(), uri));*/
					//ViewUtil.openActivity(ClipActivity.class,bundle,getContext());
					//photoUrl= ViewUtil.getRealFilePath(getContext(), uri);
					//uploadPhoto();
					Bitmap bitmap = null;
					try {
						bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//这里做了判断  如果图片大于 512KB 就进行压缩
					if(bitmap.getRowBytes()*bitmap.getHeight() > 20*1024){
						bitmap = ImageCropUtils.compressImage(bitmap);
						uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
					}

					File file=new File(Constants.PATH + "picture/thumb_"+System.currentTimeMillis() + ".jpg");
					if (!file.getParentFile().exists())
						file.getParentFile().mkdirs();
					uri_=Uri.fromFile(file);
					startActivityForResult(ImageCropUtils.getCropImageIntent(uri,uri_),3);
				}
			}
		}else if (requestCode==3){
			if (CommonUtil.notNull(uri_)){
				Log.d("uri"+uri_.getPath());
				//Uri uri = data.getData();
				photoUrl= ViewUtil.getRealFilePath(getContext(), uri_);
				uploadPhoto(photoUrl);
			}

		}
	}






	private void uploadPhoto(String photoUrl){

		dataModel.uploadPicture("71",photoUrl, new SimpleResponseListener<UploadPicture>() {
			@Override
			public void requestSuccess(UploadPicture info, Response response) {
				Log.d(""+info.id);
				UserInfo userInfo=new UserInfo();
				userInfo.photoId=info.id;
				dataModel.saveDoctor(userInfo, new SimpleResponseListener<ResponseMessage>() {
					@Override
					public void requestSuccess(ResponseMessage info, Response response) {
						dataModel.saveUser(user);
						dataModel.requestUserInfo(new SimpleResponseListener<UserResponseInfo>() {
							@Override
							public void requestSuccess(UserResponseInfo info, Response response) {
								dataModel.saveUser(info.user);
								adapater.setUserDetail(info.user);
							}

							@Override
							public void requestError(HttpException e, ResponseMessage info) {

							}
						});

					}

					@Override
					public void requestError(HttpException e, ResponseMessage info) {

					}

					@Override
					public void requestView() {
						super.requestView();
					}
				});

			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				showMsg("图片上传失败...");
			}

			@Override
			public void requestView() {
				super.requestView();
			}
		});

	}


}
