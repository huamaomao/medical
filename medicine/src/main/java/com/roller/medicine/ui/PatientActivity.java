package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.widget.EmptyView;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.adapter.FriendListAdapater;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.info.FriendResponseInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class PatientActivity extends BaseToolbarActivity {
	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView recyclerView;
	private List<UserInfo> data;
	private DataModel service;
	private FriendListAdapater adapater;
	private EmptyView emptyView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refresh_recycker);

	}

	@Override
	protected void initView() {
		super.initView();
		setBackActivity("患者");
		service=new DataModel();
		refresh.setRefreshStyle(Constants.PULL_STYLE);
		refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				doFriendList();
			}
		});
		data=new ArrayList<>();
		adapater=new FriendListAdapater(this,data,FriendListAdapater.TYPE_PATIENT);
		ViewUtil.initRecyclerView(recyclerView,this,adapater);
		adapater.addCleanItems(service.queryFriendList(Constants.USER_TYPE_DOCTOR));
	}

	public void doFriendList(){
		service.requestPatientList(new SimpleResponseListener<FriendResponseInfo>() {
			@Override
			public void requestSuccess(FriendResponseInfo info, Response response) {
				adapater.addCleanItems(info.friendList);
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {

			}
			@Override
			public void requestView() {
				refresh.setRefreshing(false);
			}
		});

	}

}


