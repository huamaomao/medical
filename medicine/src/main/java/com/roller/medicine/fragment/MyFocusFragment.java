package com.roller.medicine.fragment;

import android.os.Bundle;
import android.widget.ListView;

import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.customview.pulltorefreshview.PullToRefreshListView;
import com.roller.medicine.info.MyFocusFansCommentsItemInfo;

import java.util.LinkedList;

import butterknife.InjectView;

public class MyFocusFragment extends BaseToolbarFragment{

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
