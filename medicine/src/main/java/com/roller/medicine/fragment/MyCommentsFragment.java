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
import com.android.common.widget.AlertDialogFragment;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.info.CommentInfo;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

	private RecyclerOnScrollListener scrollListener;

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

		scrollListener=new RecyclerOnScrollListener((LinearLayoutManager)rv_view.getLayoutManager()) {
			@Override
			public void onLoadMore(int current_page) {
				requestData(current_page);
			}
		};

		ViewUtil.initRecyclerViewDecoration(rv_view, getActivity(), adapter);
		rv_view.addOnScrollListener(scrollListener);
		requestData(1);
		refresh.setRefreshing(true);
		dialog=new AlertDialogFragment();
		dialog.msg="是否删除此评论?";
		dialog.setClickListener(new AlertDialogFragment.OnClickListener() {
			@Override
			public void onCancel() {

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
			dataModel.deleteReply(item.id, new SimpleResponseListener<ResponseMessage>() {
				@Override
				public void requestSuccess(ResponseMessage info, Response response) {
					msgShow("评论已成功删除");
					adapter.removeItem(index);
				}

				@Override
				public void requestError(HttpException e, ResponseMessage info) {
					new AppHttpExceptionHandler().via(rootView).handleException(e,info);
				}
			});
		}

	}

	 void requestData(final int page){

		dataModel.getPostReplyListByMap(page,userId,new SimpleResponseListener<CommentInfo>() {
			@Override
			public void requestSuccess(CommentInfo info, Response response) {
				if (page==1){
					adapter.addItemAll(info.list);
				}else {
					adapter.addMoreItem(info.list);
					scrollListener.nextPage(info.list);
				}
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
}
