package com.roller.medicine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.widget.AlertDialogFragment;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.android.async.AsyncExecutor;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.customview.pulltorefreshview.PullToRefreshListView;
import com.roller.medicine.info.CommentInfo;
import com.roller.medicine.info.MyFocusFansCommentsItemInfo;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.InjectView;

public class MyCommentsFragment extends BaseToolbarFragment{

	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView rv_view;
	private RecyclerAdapter<CommentInfo.Item> adapter;
	private List<CommentInfo.Item> mData;
	private DataModel dataModel;

	protected AlertDialogFragment dialog;
	private int index=-1;

	public String userId=null;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.refresh_recycler_view);
	}

	public static Fragment newInstantiate(Bundle bundle){
		MyCommentsFragment fragment=new MyCommentsFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void initView(View view, final LayoutInflater inflater) {
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
		adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<CommentInfo.Item>() {
			@Override
			public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, CommentInfo.Item item, final int position) {
				viewHolder.setText(R.id.tv_name, item.nickname);
				viewHolder.setText(R.id.tv_content, item.postContent);
				viewHolder.setText(R.id.tv_comment, item.content);
				Util.loadPhoto(getActivity(), item.headImage, (ImageView) viewHolder.getView(R.id.iv_photo));
				viewHolder.setText(R.id.tv_date, TimeUtil.getFmdLongTime(item.createTime));
				Picasso.with(getActivity()).load(DataModel.getImageUrl(item.imageUrl)).placeholder(R.drawable.icon_comment_default).error(R.drawable.icon_comment_error)
						.resize(160, 160).into((ImageView) viewHolder.getView(R.id.iv_pic));
				viewHolder.setOnClick(R.id.btn_delete, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						index=position;
						dialog.show(getFragmentManager(),"delete");
					}
				});
			}

			@Override
			public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
				return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.listview_fragment_comments, viewGroup, false));
			}

			@Override
			public int getItemCount() {
				return mData.size();
			}
		});
		ViewUtil.initRecyclerViewDecoration(rv_view, getActivity(), adapter);
		requestData();
		dialog=new AlertDialogFragment();
		dialog.msg="是否删除此评论?";
		dialog.setClickListener(new AlertDialogFragment.OnClickListener() {
			@Override
			public void onCancel() {
				msgShow("评论已成功删除");
			}

			@Override
			public void onConfirm() {
				//delete
				deleteComment();
			}
		});

	}

	private void deleteComment(){
		if (index==-1) return;
		CommentInfo.Item item=mData.get(index);
		if (CommonUtil.notNull(item)){
			adapter.removeItem(index);
			dataModel.deleteReply(item.id, new SimpleResponseListener<ResponseMessage>() {
				@Override
				public void requestSuccess(ResponseMessage info, Response response) {

				}

				@Override
				public void requestError(HttpException e, ResponseMessage info) {
					new AppHttpExceptionHandler().via(rootView).handleException(e,info);
				}
			});
		}

	}

	private void requestData(){
		refresh.setRefreshing(true);
		dataModel.getPostReplyListByMap(userId,new SimpleResponseListener<CommentInfo>() {
			@Override
			public void requestSuccess(CommentInfo info, Response response) {
				adapter.addItemAll(info.list);
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {

			}

			@Override
			public void requestView() {
				if (CommonUtil.notNull(refresh))
					refresh.setRefreshing(false);
			}
		});
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		adapter.onDestroyReceiver();
	}


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
