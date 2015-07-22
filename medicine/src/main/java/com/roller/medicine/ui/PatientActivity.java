package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.common.adapter.RecyclerOnScrollListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
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

public class PatientActivity extends BaseToolbarActivity {
	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView recyclerView;
	private List<UserInfo> data;
	private DataModel service;
	private FriendListAdapater adapater;
	private RecyclerOnScrollListener scrollListener;

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
		refresh.setRefreshStyle(AppConstants.PULL_STYLE);
		refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				doFriendList(1);
				scrollListener.setPageInit();
			}
		});
		data=new ArrayList<>();
		adapater=new FriendListAdapater(this,data,recyclerView,FriendListAdapater.TYPE_PATIENT);
		ViewUtil.initRecyclerViewDecoration(recyclerView, this, adapater);

		scrollListener=new RecyclerOnScrollListener((LinearLayoutManager)recyclerView.getLayoutManager()) {
			@Override
			public void onLoadMore(int current_page) {
				doFriendList(current_page);
			}
		};
		recyclerView.addOnScrollListener(scrollListener);
		refresh.setRefreshing(true);
		doFriendList(1);
	}
	public void doFriendList(final int page){
		service.requestPatientList(page,new SimpleResponseListener<FriendResponseInfo>() {
			@Override
			public void requestSuccess(FriendResponseInfo info, Response response) {
				if (page==1){
					adapater.addItemAll(info.list);
				}else {
					adapater.addMoreItem(info.list);
					scrollListener.nextPage(info.list);
				}
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				if (page!=1){
					scrollListener.setLoadMore();
				}
			}

			@Override
			public void requestView() {
				if (page==1) {
					refresh.setRefreshing(false);
				}else{
					scrollListener.setLoadMore();
				}
				adapater.checkEmpty();
			}
		});

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapater.onDestroyReceiver();
	}

}


