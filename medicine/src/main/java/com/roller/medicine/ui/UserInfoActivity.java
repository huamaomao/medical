package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.adapter.UserDetialAdapater;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.info.ItemInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycker);
	}
	
	protected void initView(){
		setBackActivity("个人信息");
		dataModel=new DataModel();
		lsData=new ArrayList();
		adapater=new UserDetialAdapater(this,lsData);
		ViewUtil.initRecyclerView(rvView, getContext(), adapater);
		loadData();

	}

	private void loadData(){
		user=dataModel.getLoginUser();
		lsData.clear();
		lsData.add(new ItemInfo());
		lsData.add(new ItemInfo("工作地址", CommonUtil.initTextNull(user.doctorDetail.workAddress)));
		lsData.add(new ItemInfo("所在医院",CommonUtil.initTextNull(user.doctorDetail.hospitalName)));
		lsData.add(new ItemInfo("医生职称",CommonUtil.initTextNull(user.doctorDetail.doctorTitle)));
		lsData.add(new ItemInfo("所在科室",CommonUtil.initTextNull(user.doctorDetail.department)));
		lsData.add(new ItemInfo("专长", CommonUtil.initTextNull(user.doctorDetail.speciality)));
		adapater.setUserDetail(user);
		adapater.notifyDataSetChanged();
	}

	

	
	/*@OnClick({R.id.relativelayout_nickname,R.id.relativelayout_phone
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
	}*/

}
