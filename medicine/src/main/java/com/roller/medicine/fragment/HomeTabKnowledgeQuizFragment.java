package com.roller.medicine.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.baoyz.widget.PullRefreshLayout;
import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.info.KnowledgeQuizItemInfo;

import java.util.LinkedList;

import butterknife.InjectView;

public class HomeTabKnowledgeQuizFragment extends BaseToolbarFragment{

	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView rv_view;


	private LinkedList<KnowledgeQuizItemInfo> mDatas = new LinkedList<KnowledgeQuizItemInfo>();
	private PublicViewAdapter<KnowledgeQuizItemInfo> adapter;
	private Resources mResources;
	private int pageNum = 1;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.public_wrap_recycler_view);
	}


	@Override
	protected void initView(View view, LayoutInflater inflater) {
		super.initView(view, inflater);
		setTitle("论坛");
	}

/*
	*//**
	 * 加载数据
	 *//*
	public void initData() {
		try {
			DataService.getInstance().getPostListByMap(this, BaseApplication.TOKEN,pageNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 点赞
	 *//*
	private void savePraise(String postId,String repiyId,String typeId,String mainUserId){
		try {
			DataService.getInstance().savePraise(this, BaseApplication.TOKEN, postId, repiyId, typeId, mainUserId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 取消赞
	 * @param id
	 *//*
	private void deletePraise(String id){
		try {
			DataService.getInstance().deletePraise(this, BaseApplication.TOKEN,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onStart(String url, Object tag) {
		 super.onStart(url, tag);
	}

	@Override
	public void onSuccess(String url, String result, int resultCode, Object tag) {
		super.onSuccess(url, result, resultCode, tag);
		if(resultCode != 200){
			onPullDownUpRefreshComplete();
			return;
		}
		
		if(Constants.URL.GETPOSTLISTBYMAP.equals(url)){
			KnowledgeQuizInfo mInfo = JSON.parseObject(result, KnowledgeQuizInfo.class);
			
			if(pageNum == 1){
				//
				//下拉刷新
				//
				if(mInfo.getList() != null){
					mDatas.clear();
					mDatas.addAll(mInfo.getList());
					adapter.notifyDataSetChanged();
				}
			}else{
				//
				//上拉加载
				//
				if(mInfo.getList() != null){
					mDatas.addAll(mInfo.getList());
					adapter.notifyDataSetChanged();
				}
			}
			pageNum = mInfo.getPageNum();
			onPullDownUpRefreshComplete();
			if (mInfo.getList().size() < 10) {
				pullToRefreshListView.setHasMoreData(false);
			}
		}
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
	protected void fragmentHide() {

	}

	@Override
	public void commonOnClick(View v) {
		switch (v.getId()) {
		case R.id.linearlayout_item_praise:
			KnowledgeQuizItemInfo item = (KnowledgeQuizItemInfo) v.getTag();
			String praise = "";
			if(item.getPraise().equals("false")){
				praise = "true";
				savePraise(item.getId(), item.getReplyId(), "74", item.getCreateUserId());
				mDatas.get(Integer.valueOf(v.getTag(R.id.position).toString())).setPraiseCount((Integer.valueOf(item.getPraiseCount())+1)+"");
				
			}else{
				praise = "false";
				deletePraise(item.getId());
				mDatas.get(Integer.valueOf(v.getTag(R.id.position).toString())).setPraiseCount((Integer.valueOf(item.getPraiseCount())-1)+"");
			}
			mDatas.get(Integer.valueOf(v.getTag(R.id.position).toString())).setPraise(praise);
			adapter.notifyDataSetChanged();
			break;
		}
	}

	@Override
	public void commonGetView(PublicViewHolder helper, KnowledgeQuizItemInfo item,
			OnClickListener onClick, int position, Object tag) {
		LinearLayout linearlayout_item_praise = helper.getView(R.id.linearlayout_item_praise);
		ImageView image_item_picture = helper.getView(R.id.image_item_picture);
		ImageView image_item_shared = helper.getView(R.id.image_item_shared);
		ImageView image_item_praidse = helper.getView(R.id.image_item_praidse);
		TextView text_item_title = helper.getView(R.id.text_item_title);
		TextView text_item_praidse_count = helper.getView(R.id.text_item_praidse_count);
		TextView text_item_content = helper.getView(R.id.text_item_content);
		TextView text_item_from = helper.getView(R.id.text_item_from);
		TextView text_item_of_taste = helper.getView(R.id.text_item_of_taste);
		
		text_item_title.setText(item.getTitle());
		text_item_content.setText(item.getContent());
		text_item_of_taste.setText(item.getReplyCount());
		text_item_praidse_count.setText(item.getPraiseCount());
		text_item_from.setText("晚上22:08  来源："+ item.getSource());
		
		linearlayout_item_praise.setOnClickListener(onClick);
		image_item_shared.setOnClickListener(onClick);
		
		linearlayout_item_praise.setTag(item);
		linearlayout_item_praise.setTag(R.id.position, position);
		image_item_picture.setTag(item);
		
		if(item.getPraise().equals("false")){
			image_item_praidse.setBackgroundDrawable(mResources.getDrawable(R.drawable.image_praise_btn_unselect));
		}else{
			image_item_praidse.setBackgroundDrawable(mResources.getDrawable(R.drawable.image_praise_btn_select));
		}
		
		if(item.getImages().size() > 0){
			mBitmapUtils.display(image_item_picture, Constants.URL.IMAGEIP + item.getImages().get(0).getUrl());
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		pageNum = 1;
		initData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		pageNum = pageNum + 1;
		initData();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ImageView image_picture = (ImageView) view.findViewById(R.id.image_item_picture);
		KnowledgeQuizItemInfo item = (KnowledgeQuizItemInfo) image_picture.getTag();
		Bundle bundle = new Bundle();
		bundle.putString(Constants.ID, item.getId());
		bundle.putString("boardId", item.getBoardId());
		openActivity(KnowledgeQuizContentActivity.class, bundle);
	}*/

}
