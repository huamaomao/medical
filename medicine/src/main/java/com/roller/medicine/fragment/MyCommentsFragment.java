package com.roller.medicine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.litesuits.android.async.AsyncExecutor;
import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.customview.pulltorefreshview.PullToRefreshListView;
import com.roller.medicine.info.MyFocusFansCommentsItemInfo;

import java.util.LinkedList;

import butterknife.InjectView;

public class MyCommentsFragment extends BaseToolbarFragment{

	@InjectView(R.id.listview)
	 PullToRefreshListView pullToRefreshListView;
	private ListView listView;
	private LinkedList<MyFocusFansCommentsItemInfo> mDatas = new LinkedList<MyFocusFansCommentsItemInfo>();
	private PublicViewAdapter<MyFocusFansCommentsItemInfo> adapter;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.fragment_my_clffans);
	}

	@Override
	protected void initView(View view, LayoutInflater inflater) {
		super.initView(view, inflater);

	}

	private void initView(){
	/*	pullToRefreshListView.setPullRefreshEnabled(true);
		pullToRefreshListView.setPullLoadEnabled(false);
		pullToRefreshListView.setScrollLoadEnabled(true);
		pullToRefreshListView.setOnRefreshListener(this);
		setLastUpdateTime();
		listView = pullToRefreshListView.getRefreshableView();
		listView.setDivider(getResources().getDrawable(R.color.public_line));
		listView.setDividerHeight(1);
//		listView.setOnItemClickListener(this);
		adapter = new PublicViewAdapter<MyFocusFansCommentsItemInfo>(
				getActivity(), mDatas, R.layout.listview_fragment_comments, this, this, Constants.TAG.TAG_NONE);
		
		listView.setAdapter(adapter);
		pullToRefreshListView.doPullRefreshing(true, 300);*/
	}
	
/*
	*//**
	 * 我的评论
	 *//*
	private void getPostReplyListByMap(){
		try {
			DataService.getInstance().getPostReplyListByMap(this, BaseApplication.TOKEN);
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
		
		if(Constants.URL.GETPOSTREPLYLISTBYMAP.equals(url)){
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
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		getPostReplyListByMap();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		getPostReplyListByMap();
	}

	@Override
	public void commonOnClick(View v) {
		
	}

	@Override
	public void commonGetView(PublicViewHolder helper, MyFocusFansCommentsItemInfo item,
			OnClickListener onClick, int position, Object tag) {
		ImageView image_item_head = helper.getView(R.id.image_item_head);
		TextView text_item_nickname = helper.getView(R.id.text_item_nickname);
		TextView text_item_time = helper.getView(R.id.text_item_time);
		TextView text_item_comments = helper.getView(R.id.text_item_comments);
		ImageView image_item_picture = helper.getView(R.id.image_item_picture);
		TextView image_item_content = helper.getView(R.id.image_item_content);
		
//		text_item_time.setText(item.get)
		text_item_comments.setText(item.getReplyContent());
		text_item_nickname.setText(item.getNickname());
		image_item_content.setText(item.getContent());
		mHeadBitmapUtils = XUtilsBitmapHelp.getBitmapUtilsInstance(
				getActivity(),R.drawable.public_default_head, R.drawable.public_default_head);
		
		mHeadBitmapUtils.display(image_item_head, item.getHeadImage(),OtherUtils.roundBitmapLoadCallBack);
		mPictureBitmapUtils = XUtilsBitmapHelp.getBitmapUtilsInstance(
				getActivity(),R.drawable.public_image_loading, R.drawable.public_image_failure);
		
		if(item.getImages().size() > 0)mPictureBitmapUtils.display(image_item_picture, item.getImages().get(0).getUrl());
	}*/
}
