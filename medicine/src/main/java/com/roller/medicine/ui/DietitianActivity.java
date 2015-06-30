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
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class DietitianActivity extends BaseToolbarActivity {
	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView recyclerView;
	private List<UserInfo> data;
	private DataModel service;
	private FriendListAdapater adapater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refresh_recycker);
	}

	@Override
	protected void initView() {
		super.initView();
		setBackActivity("营养师");
		service=new DataModel();
		refresh.setRefreshStyle(AppConstants.PULL_STYLE);
		refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				doFriendList();
			}
		});
		data=new ArrayList<>();
		adapater=new FriendListAdapater(this,data,recyclerView,FriendListAdapater.TYPE_DOCTOR);
		ViewUtil.initRecyclerViewDecoration(recyclerView, this, adapater);
		adapater.addItemAll(service.queryFriendList(AppConstants.USER_TYPE_DIETITAN));
	}

	public void doFriendList(){
		service.requestDoctorList(AppConstants.USER_TYPE_DIETITAN, new SimpleResponseListener<FriendResponseInfo>() {
			@Override
			public void requestSuccess(FriendResponseInfo info, Response response) {
				adapater.addItemAll(info.list);
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
			}

			@Override
			public void requestView() {
				super.requestView();
				refresh.setRefreshing(false);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapater.onDestroyReceiver();
	}

}


