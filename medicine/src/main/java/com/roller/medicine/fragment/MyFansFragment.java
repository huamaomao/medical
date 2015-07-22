package com.roller.medicine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.adapter.RecyclerOnScrollListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarFragment;
import com.roller.medicine.info.FocusInfo;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MyFansFragment extends BaseLoadingToolbarFragment{


	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView rv_view;
	private RecyclerAdapter<FocusInfo.Item> adapter;
	private List<FocusInfo.Item> mData;
	private DataModel dataModel;
	public String userId=null;

	private RecyclerOnScrollListener scrollListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.refresh_recycler_view);
	}

	public static Fragment newInstantiate(Bundle bundle){
		MyFansFragment fragment=new MyFansFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void initView(View view, LayoutInflater inflater) {
		super.initView(view, inflater);
		mData=new ArrayList<>();
		dataModel=new DataModel();
		refresh.setRefreshStyle(AppConstants.PULL_STYLE);
		refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				requestData(1);
				scrollListener.setPageInit();
			}
		});
		userId=getArguments().getString(AppConstants.ITEM);
		adapter=new RecyclerAdapter<>(getActivity(),mData,rv_view);
		adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<FocusInfo.Item>() {
			@Override
			public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder,final FocusInfo.Item item,final int position) {
				viewHolder.setText(R.id.tv_name, item.nickname);
				Util.loadPhoto(getActivity(),item.headImage,(ImageView)viewHolder.getView(R.id.iv_photo));
				if (AppConstants.USER_TYPE_DIETITAN.equals(item.typeId)|| AppConstants.USER_TYPE_DOCTOR==item.typeId){
					viewHolder.getView(R.id.iv_pic).setVisibility(View.VISIBLE);
				}else {
					viewHolder.getView(R.id.iv_pic).setVisibility(View.GONE);
				}
				if (AppConstants.USER_ADD.equals(item.statusId)){
					viewHolder.setText(R.id.iv_more,"已关注");
				}else{
					viewHolder.setText(R.id.iv_more, "+ 关注");
					viewHolder.getView(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							doAddFriend(item.id, position);
						}
					});

				}
			}

			@Override
			public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
				return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.listview_onfans,viewGroup,false));
			}

			@Override
			public int getItemCount() {
				return mData.size();
			}
		});
		ViewUtil.initRecyclerViewDecoration(rv_view, getActivity(), adapter);
		scrollListener=new RecyclerOnScrollListener((LinearLayoutManager)rv_view.getLayoutManager()) {
			@Override
			public void onLoadMore(int current_page) {
				requestData(current_page);
			}
		};
		rv_view.addOnScrollListener(scrollListener);
		refresh.setRefreshing(true);
		requestData(1);
	}

	public void doAddFriend(String id,final int position) {
		showLoading();
		dataModel.requestAddFriendId(id, null, new SimpleResponseListener<ResponseMessage>() {
			@Override
			public void requestSuccess(ResponseMessage info, Response response) {
				showMsg("关注成功");
				FocusInfo.Item item = mData.get(position);
				item.statusId = AppConstants.USER_ADD;
				adapter.notifyItemUpdate(position);
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				new AppHttpExceptionHandler().via(rootView).handleException(e, info);
			}

			@Override
			public void requestView() {
				hideLoading();
			}
		});
	}


	private void requestData(final int page){

		dataModel.getRelationListByMap(userId,"1",page, new SimpleResponseListener<FocusInfo>() {
			@Override
			public void requestSuccess(FocusInfo info, Response response) {
				if (page==1){
					adapter.addItemAll(info.list);
				}else {
					adapter.addMoreItem(info.list);
				}
				scrollListener.nextPage(info.list);
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {

			}

			@Override
			public void requestView() {
				if (CommonUtil.notNull(refresh)){
					if (page==1) {
						refresh.setRefreshing(false);
					}else{
						scrollListener.setLoadMore();
					}
					adapter.checkEmpty();
				}
			}
		});
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		adapter.onDestroyReceiver();
	}
}
