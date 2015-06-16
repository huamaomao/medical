package com.roller.medicine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.customview.pulltorefreshview.PullToRefreshListView;
import com.roller.medicine.info.CommentInfo;
import com.roller.medicine.info.FocusInfo;
import com.roller.medicine.info.MyFocusFansCommentsItemInfo;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.InjectView;

public class MyFocusFragment extends BaseToolbarFragment{

	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView rv_view;
	private RecyclerAdapter<FocusInfo.Item> adapter;
	private List<FocusInfo.Item> mData;
	private DataModel dataModel;
	public String userId=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.refresh_recycler_view);
	}

	public static Fragment newInstantiate(Bundle bundle){
		MyFocusFragment fragment=new MyFocusFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void initView(View view, LayoutInflater inflater) {
		super.initView(view, inflater);
		mData=new ArrayList<>();
		dataModel=new DataModel();
		refresh.setRefreshStyle(Constants.PULL_STYLE);
		refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				requestData();
			}
		});
		userId=getArguments().getString(Constants.ITEM);
		adapter=new RecyclerAdapter<>(getActivity(),mData,rv_view);
		adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<FocusInfo.Item>() {
			@Override
			public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, FocusInfo.Item item, int position) {
				viewHolder.setText(R.id.tv_name, item.nickname);
				Util.loadPhoto(getActivity(), item.headImage, (ImageView) viewHolder.getView(R.id.iv_photo));
				if (Constants.USER_TYPE_DIETITAN.equals(item.typeId)||Constants.USER_TYPE_DOCTOR==item.typeId){
					viewHolder.getView(R.id.iv_pic).setVisibility(View.VISIBLE);
				}else {
					viewHolder.getView(R.id.iv_pic).setVisibility(View.GONE);
				}
				if (Constants.USER_ADD.equals(item.statusId)){
					viewHolder.setText(R.id.iv_more,"已关注");
				}else{
					viewHolder.setText(R.id.iv_more, "+ 关注");
					viewHolder.getView(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

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
		requestData();
	}

	private void requestData(){
		refresh.setRefreshing(true);
		dataModel.getRelationListByMap(userId,"2",new SimpleResponseListener<FocusInfo>() {
			@Override
			public void requestSuccess(FocusInfo info, Response response) {
				adapter.addItemAll(info.list);
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {

			}

			@Override
			public void requestView() {
				if (CommonUtil.notNull(refresh)){
					refresh.setRefreshing(false);
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

  /*private void initView(){
		mBitmapUtils = XUtilsBitmapHelp.getBitmapUtilsInstance(
				getActivity(),R.drawable.public_default_head, R.drawable.public_default_head);
		
		pullToRefreshListView.setPullRefreshEnabled(true);
		pullToRefreshListView.setPullLoadEnabled(false);
		pullToRefreshListView.setScrollLoadEnabled(true);
		pullToRefreshListView.setOnRefreshListener(this);
		setLastUpdateTime();
		listView = pullToRefreshListView.getRefreshableView();
		listView.setDivider(getResources().getDrawable(R.color.public_line));
		listView.setDividerHeight(1);
//		listView.setOnItemClickListener(this);
		adapter = new PublicViewAdapter<MyFocusFansCommentsItemInfo>(
				getActivity(), mDatas, R.layout.listview_onfocus, this, this, Constants.TAG.TAG_NONE);
		listView.setAdapter(adapter);
		pullToRefreshListView.doPullRefreshing(true, 300);
	}
	
	*//**
	 * 设置刷新时间
	 *//*
	private void setLastUpdateTime() {
		pullToRefreshListView.setLastUpdatedLabel(TimeUtils.currentLocalTimeString());
	}
	
	private void getRelationListByMap(){
		try {
			DataService.getInstance().getRelationListByMap(this, BaseApplication.TOKEN, Constants.ONFOCUS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onSuccess(String url, String result, int resultCode, Object tag) {
		super.onSuccess(url, result, resultCode, tag);
		if(resultCode != 200){
			onPullDownUpRefreshComplete();
			return;
		}
		
		if(Constants.URL.GETRELATIONLISTBYMAP.equals(url)){
			MyFocusFansCommentsInfo mFocusInfo = JSON.parseObject(result, MyFocusFansCommentsInfo.class);
			if(mFocusInfo.getList() != null){
				mDatas.clear();
				mDatas.addAll(mFocusInfo.getList());
				adapter.notifyDataSetChanged();
			}
		}
		onPullDownUpRefreshComplete();
	}
	
	@Override
	public void onFailure(String url, HttpException error, String msg,
			Object tag) {
		super.onFailure(url, error, msg, tag);
		onPullDownUpRefreshComplete();
	}
	
	private void onPullDownUpRefreshComplete(){
		pullToRefreshListView.onPullDownRefreshComplete();
		pullToRefreshListView.onPullUpRefreshComplete();
	}
	
	@Override
	protected void lazyLoad(boolean willRefresh) {
		
	}

	@Override
	protected void fragmentHide() {
		
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		getRelationListByMap();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		getRelationListByMap();
	}

	@Override
	public void commonOnClick(View v) {
		
	}

	@Override
	public void commonGetView(PublicViewHolder helper, MyFocusFansCommentsItemInfo item,
			OnClickListener onClick, int position, Object tag) {
		ImageView image_item_head = helper.getView(R.id.image_item_head);
		TextView text_item_nickname = helper.getView(R.id.text_item_nickname);
		ImageView image_item_sex = helper.getView(R.id.image_item_sex);
		ImageView image_item_type = helper.getView(R.id.image_item_type);
		
		text_item_nickname.setText(item.getNickname());
		mBitmapUtils.display(image_item_head, item.getHeadImage(),OtherUtils.roundBitmapLoadCallBack);
	}
	*/
}
