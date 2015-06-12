package com.roller.medicine.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.info.LoveInfo;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/***
 * 喜欢
 */
public class MyLikeFragment extends BaseToolbarFragment {


	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView rv_view;
	private RecyclerAdapter<LoveInfo.Item> adapter;
	private List<LoveInfo.Item> mData;
	private DataModel dataModel;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.refresh_recycler_view);
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

		adapter=new RecyclerAdapter(getActivity(),mData,rv_view);
		adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<LoveInfo.Item>() {
			@Override
			public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, LoveInfo.Item item, int position) {

			}

			@Override
			public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
				return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.listview_like,viewGroup,false));
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
		dataModel.getPraiseListByMap(new SimpleResponseListener<LoveInfo>() {
			@Override
			public void requestSuccess(LoveInfo info, Response response) {
				Log.d(info);
				adapter.addItemAll(info.list);
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

	/*	private void initView(){
		pullToRefreshListView.setPullRefreshEnabled(true);
		pullToRefreshListView.setPullLoadEnabled(false);
		pullToRefreshListView.setScrollLoadEnabled(true);
		pullToRefreshListView.setOnRefreshListener(this);
		setLastUpdateTime();
		listView = pullToRefreshListView.getRefreshableView();
		listView.setDivider(getResources().getDrawable(R.color.public_bg));
		listView.setDividerHeight(20);
//		listView.setOnItemClickListener(this);
		adapter = new PublicViewAdapter<MyLikeItemInfo>(
				getActivity(), mDatas, R.layout.listview_like, this, this, Constants.TAG.TAG_NONE);
		
		listView.setAdapter(adapter);
		pullToRefreshListView.doPullRefreshing(true, 300);
	}
	
	*//**
	 * 设置刷新时间
	 *//*
	private void setLastUpdateTime() {
		pullToRefreshListView.setLastUpdatedLabel(TimeUtils.currentLocalTimeString());
	}
	
	*//**
	 * 加载点赞的数据
	 *//*
	private void getPraiseListByMap(){
		try {
			DataService.getInstance().getPraiseListByMap(this, BaseApplication.TOKEN);
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
		
		if(Constants.URL.GETPRAISELISTBYMAP.equals(url)){
			MyLikeInfo mLikeInfo = JSON.parseObject(result, MyLikeInfo.class);
			if(mLikeInfo.getList() != null){
				mDatas.clear();
				mDatas.addAll(mLikeInfo.getList());
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
		getPraiseListByMap();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		getPraiseListByMap();
	}

	@Override
	public void commonOnClick(View v) {
		
	}

	@Override
	public void commonGetView(PublicViewHolder helper, MyLikeItemInfo item,
			OnClickListener onClick, int position, Object tag) {
		LinearLayout linearlayout_item_like_comments = helper.getView(R.id.linearlayout_item_like_comments);
		View view = helper.getView(R.id.include_item_like_content);
		if("74".equals(item.getTypeId())){
			//
			//点赞帖子
			//
			MyLikePostInfo post = item.getPost();
			linearlayout_item_like_comments.setVisibility(View.GONE);
			view.setVisibility(View.VISIBLE);
			LinearLayout linearlayout_item_praise = helper.getView(R.id.linearlayout_item_praise);
			ImageView image_item_picture = helper.getView(R.id.image_item_picture);
			ImageView image_item_shared = helper.getView(R.id.image_item_shared);
			ImageView image_item_praidse = helper.getView(R.id.image_item_praidse);
			TextView text_item_title = helper.getView(R.id.text_item_title);
			TextView text_item_praidse_count = helper.getView(R.id.text_item_praidse_count);
			TextView text_item_content = helper.getView(R.id.text_item_content);
			TextView text_item_from = helper.getView(R.id.text_item_from);
			TextView text_item_of_taste = helper.getView(R.id.text_item_of_taste);
			
			text_item_title.setText(post.getTitle());
			text_item_content.setText(post.getContent());
			text_item_of_taste.setText(post.getReplyCount());
			text_item_praidse_count.setText(post.getPraiseCount());
			text_item_from.setText("晚上22:08  来源："+ post.getSource());
			
			linearlayout_item_praise.setOnClickListener(onClick);
			image_item_shared.setOnClickListener(onClick);
			
			linearlayout_item_praise.setTag(item);
			linearlayout_item_praise.setTag(R.id.position, position);
			image_item_picture.setTag(item);
			
			if(post.getPraise().equals("false")){
				image_item_praidse.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_praise_btn_unselect));
			}else{
				image_item_praidse.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_praise_btn_select));
			}
			
			if(post.getImages().size() > 0){
				mBitmapUtils = XUtilsBitmapHelp.getBitmapUtilsInstance(
						getActivity(),R.drawable.public_image_loading, R.drawable.public_image_failure);
				
				mBitmapUtils.display(image_item_picture, post.getImages().get(0).getUrl(), new BitmapLoadCallBack<ImageView>() {
		
					@Override
					public void onLoadCompleted(ImageView imageView, String arg1, Bitmap bitmap,
							BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
						imageView.setImageBitmap(ImageUtils.centerSquareScaleBitmap(bitmap, 160));
					}
		
					@Override
					public void onLoadFailed(ImageView arg0, String arg1, Drawable arg2) {
						
					}
				});
			}
		}else if("78".equals(item.getTypeId())){
			//
			//点赞回复
			//
			MyLikeReplyInfo reply = item.getReply();
			linearlayout_item_like_comments.setVisibility(View.VISIBLE);
			view.setVisibility(View.GONE);
			ImageView image_item_like_head = helper.getView(R.id.image_item_like_head);
			TextView text_item_like_nickname = helper.getView(R.id.text_item_like_nickname);
			TextView text_item_like_time = helper.getView(R.id.text_item_like_time);
			TextView image_item_like_content = helper.getView(R.id.image_item_like_content);
			LinearLayout linearlayout_item_like_praise = helper.getView(R.id.linearlayout_item_like_praise);
			ImageView image_item_like_praidse = helper.getView(R.id.image_item_like_praidse);
			TextView text_item_like_praidse_count = helper.getView(R.id.text_item_like_praidse_count);
			
			text_item_like_nickname.setText(reply.getNickname());
			text_item_like_time.setText("晚上22:08");
			image_item_like_content.setText(reply.getContent());
			text_item_like_praidse_count.setText(reply.getPraiseCount());
			
			if(reply.getPraise().equals("false")){
				image_item_like_praidse.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_praise_btn_unselect));
			}else{
				image_item_like_praidse.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_praise_btn_select));
			}
			
			mBitmapUtils = XUtilsBitmapHelp.getBitmapUtilsInstance(
					getActivity(),R.drawable.public_default_head, R.drawable.public_default_head);
			
			mBitmapUtils.display(image_item_like_head, item.getHeadImage(),OtherUtils.roundBitmapLoadCallBack);
		}
	}*/

}
