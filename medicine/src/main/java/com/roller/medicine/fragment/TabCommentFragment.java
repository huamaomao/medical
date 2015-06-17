package com.roller.medicine.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.info.KnowledgeQuizItemInfo;
import com.roller.medicine.ui.CommentDetailActivity;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class TabCommentFragment extends BaseToolbarFragment{

	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView rv_view;

	private List<KnowledgeQuizItemInfo.Item> mDatas = new ArrayList<>();
	private int pageNum = 1;
	private RecyclerAdapter<KnowledgeQuizItemInfo.Item> adapter;

	private DataModel model;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.refresh_recycler_view);
	}

	@Override
	protected void initView(View view, LayoutInflater inflater) {
		super.initView(view, inflater);
		setTitle("论坛");
		model=new DataModel();
		refresh.setRefreshStyle(Constants.PULL_STYLE);
		refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadData();
			}
		});
		adapter=new RecyclerAdapter(getActivity(),mDatas,rv_view);
		adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<KnowledgeQuizItemInfo.Item>() {
			@Override
			public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder,final KnowledgeQuizItemInfo.Item item, final int position) {
				viewHolder.setText(R.id.tv_title, item.title);
				viewHolder.setText(R.id.tv_content, item.content);
				viewHolder.setText(R.id.tv_source, "来源：" + item.source);
				viewHolder.setText(R.id.tv_date, TimeUtil.getFmdLongTime(item.createTime));
				/*
				viewHolder.setText(R.id.tv_praise, CommonUtil.initTextValue(item.praiseCount));
				viewHolder.setText(R.id.tv_comment, CommonUtil.initTextValue(item.replyCount));
				TextView textView = viewHolder.getView(R.id.tv_praise);
				if ("false".equals(item.praise)) {
					textView.setCompoundDrawablesWithIntrinsicBounds(getActivity().getResources().getDrawable(R.drawable.image_praise_btn_unselect), null, null, null);
					textView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							setLastClickTime();
							savePraise(position, item.id, item.createUserId, "74", item.createUserId);
						}
					});
				} else {
					textView.setCompoundDrawablesWithIntrinsicBounds(getActivity().getResources().getDrawable(R.drawable.image_praise_btn_select), null, null, null);
				}
				viewHolder.getView(R.id.tv_comment).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						setLastClickTime();
						//评论
						setLastClickTime();
						Bundle bundle = new Bundle();
						bundle.putString(Constants.ITEM, item.id);
						ViewUtil.openActivity(KnowledgeQuizContentActivity.class, bundle, getActivity(), ActivityModel.ACTIVITY_MODEL_2);
					}
				});


				*/
				viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						setLastClickTime();
						Bundle bundle = new Bundle();
						bundle.putString(Constants.ITEM, item.id);
						ViewUtil.openActivity(CommentDetailActivity.class, bundle, getActivity(), ActivityModel.ACTIVITY_MODEL_2);
					}
				});

				String url=null;
				if (CommonUtil.notNull(item.images) && item.images.size() > 0) {
					url=item.images.get(0).url;
				}
				Picasso.with(getActivity()).load(DataModel.getImageUrl(url)).placeholder(R.drawable.icon_comment_default).error(R.drawable.icon_comment_error)
						.resize(160, 160).into((ImageView) viewHolder.getView(R.id.iv_photo));
			}

			@Override
			public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
				return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.listview_knowledge_quiz, viewGroup, false));
			}

			@Override
			public int getItemCount() {
				return mDatas.size();
			}
		});
		ViewUtil.initRecyclerViewDecoration(rv_view, getActivity(), adapter);
		refresh.setRefreshing(true);
		loadData();

	}


	public void  loadData(){
		model.getPostListByMap(pageNum, new SimpleResponseListener<KnowledgeQuizItemInfo>() {
			@Override
			public void requestSuccess(KnowledgeQuizItemInfo info, Response response) {
				adapter.addItemAll(info.list);
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {

			}

			@Override
			public void requestView() {
				adapter.checkEmpty();
				refresh.setRefreshing(false);
			}
		});
	}



	@Override
	public void onDestroyView() {
		super.onDestroyView();
		adapter.onDestroyReceiver();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
