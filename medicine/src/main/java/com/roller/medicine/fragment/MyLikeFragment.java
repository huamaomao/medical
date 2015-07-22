package com.roller.medicine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.adapter.RecyclerOnScrollListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.info.LoveInfo;
import com.roller.medicine.ui.CommentDetailActivity;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

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
	public String userId=null;

	private RecyclerOnScrollListener scrollListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.refresh_recycler_view);
	}

	public static Fragment newInstantiate(Bundle bundle){
		MyLikeFragment fragment=new MyLikeFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void initView(final View view, LayoutInflater inflater) {
		super.initView(view, inflater);
		mData=new ArrayList();
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
		adapter=new RecyclerAdapter(getActivity(),mData,rv_view){
			@Override
			public int getItemType(int position) {
				LoveInfo.Item item=adapter.getData().get(position);
				if (CommonUtil.notNull(item.post)){
					//帖子
					return  22;
				}
				return super.getItemType(position);
			}
		};
		adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<LoveInfo.Item>() {
			@Override
			public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, final LoveInfo.Item item, int position) {
				if (CommonUtil.notNull(item.post)) {

					viewHolder.setText(R.id.tv_content, item.post.content);
					viewHolder.setText(R.id.tv_praise, CommonUtil.initTextValue(item.post.praiseCount));
					viewHolder.setText(R.id.tv_comment, CommonUtil.initTextValue(item.post.replyCount));
					viewHolder.setText(R.id.tv_source, "来源：" + item.post.source);
					viewHolder.setText(R.id.tv_date, TimeUtil.getFmdLongTime(item.createTime));
					TextView textView = viewHolder.getView(R.id.tv_praise);
					if ("false".equals(item.post.praise)) {
						textView.setCompoundDrawablesWithIntrinsicBounds(getActivity().getResources().getDrawable(R.drawable.image_praise_btn_unselect), null, null, null);
						textView.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								if (CommonUtil.isFastClick())return;
								///savePraise(position, item.id, item.replyId, "74", item.createUserId);
							}
						});
					} else {
						textView.setCompoundDrawablesWithIntrinsicBounds(getActivity().getResources().getDrawable(R.drawable.image_praise_btn_select), null, null, null);
					}
					viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (CommonUtil.isFastClick())return;
							Bundle bundle = new Bundle();
							bundle.putString(AppConstants.ITEM, item.post.id);
							ViewUtil.openActivity(CommentDetailActivity.class, bundle, getActivity(), ActivityModel.ACTIVITY_MODEL_2);
						}
					});
					viewHolder.getView(R.id.tv_comment).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (CommonUtil.isFastClick())return;
							//评论
							Bundle bundle = new Bundle();
							bundle.putString(AppConstants.ITEM, item.id);
							ViewUtil.openActivity(CommentDetailActivity.class, bundle, getActivity(), ActivityModel.ACTIVITY_MODEL_2);
						}
					});

					String url = null;
					if (CommonUtil.notNull(item.post.images) && item.post.images.size() > 0) {
						url = item.post.images.get(0).url;
					}
					Picasso.with(getActivity()).load(DataModel.getImageUrl(url)).placeholder(R.drawable.icon_comment_default).error(R.drawable.icon_comment_error)
							.resize(160, 160).into((ImageView) viewHolder.getView(R.id.iv_pic));
				} else {
					if (CommonUtil.notNull(item.reply)) {
						Util.loadPhoto(getActivity(), item.reply.headImage, (ImageView) viewHolder.getView(R.id.iv_photo));
						viewHolder.setText(R.id.tv_date, TimeUtil.getFmdLongTime(item.reply.createTime));
						viewHolder.setText(R.id.tv_name, item.reply.nickname);
						viewHolder.setText(R.id.tv_content, Html.fromHtml(item.reply.content));
						viewHolder.setText(R.id.tv_praise, CommonUtil.initTextValue(item.reply.praiseCount));
					}
				}


			}

			@Override
			public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
				if (viewType == 22)
					return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_like_comment, viewGroup, false));
				return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.listview_like, viewGroup, false));
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
		requestData(1);
		refresh.setRefreshing(true);

	}
	private void requestData(final int page){
		refresh.setRefreshing(true);
		dataModel.getPraiseListByMap(userId,page,new SimpleResponseListener<LoveInfo>() {
			@Override
			public void requestSuccess(LoveInfo info, Response response) {
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
