package com.roller.medicine.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.RecyclerAdapter;
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
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.info.CommentDetailInfo;
import com.roller.medicine.info.CommentInfo;
import com.roller.medicine.info.ReplyInfo;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/****
 * 帖子详细
 */
public class CommentDetailActivity extends BaseToolbarActivity{

	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView rv_view;

	@InjectView(R.id.iv_praise)
	ImageView iv_praise;



	private HashMap<String, Drawable> mImageCache = new HashMap();

	private RecyclerAdapter<ReplyInfo> adapter;
	private List<ReplyInfo> mData;

	private String id=null;
	private String boardId=null;
	private DataModel dataModel;

	private CommentDetailInfo contentInfo;
	private TokenInfo tokenInfo;

	private UserInfo userInfo;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_knowledge_quiz_content);
	}

	protected void initView() {
		id=getIntent().getExtras().getString(AppConstants.ITEM);
		boardId=getIntent().getExtras().getString(AppConstants.DATA_BOARD_ID);

		setBackActivity("详情");
		dataModel=new DataModel();
		mData=new ArrayList();
		tokenInfo=dataModel.getToken();
		userInfo=dataModel.getLoginUser();
		refresh.setRefreshStyle(AppConstants.PULL_STYLE);
		refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadData();
			}
		});
		adapter=new RecyclerAdapter(getContext(),mData,rv_view){
			@Override
			public int getItemType(int position) {
				if (mData.get(position)==null){
					return 22;
				}
				return super.getItemType(position);
			}
		};
		adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<ReplyInfo>() {
			@Override
			public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, final ReplyInfo item, int position) {
				if (mData.get(position) == null) {
						StringBuilder builder = new StringBuilder();
						builder.append("评论").append(CommonUtil.initTextValue(contentInfo.replyCount));
						viewHolder.setText(R.id.tv_date, TimeUtil.getFmdLongTime(contentInfo.createTime));
						viewHolder.setText(R.id.tv_source, "来源：" + contentInfo.source);
						viewHolder.setText(R.id.tv_title, contentInfo.title);
						viewHolder.setText(R.id.tv_comment,builder.toString() );
						viewHolder.setText(R.id.tv_comment_count,builder.toString() );
						viewHolder.setText(R.id.tv_praise, CommonUtil.initTextValue(contentInfo.praiseCount));
						viewHolder.setText(R.id.tv_content, Html.fromHtml(contentInfo.content, new Html.ImageGetter() {
							@Override
							public Drawable getDrawable(final String source) {
								final Drawable drawable = mImageCache.get(source);
								if (CommonUtil.notNull(drawable)) {
									return drawable;
								}
								Picasso.with(getContext()).load(source).into(new Target() {
									@Override
									public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
										Drawable drawable1 = new BitmapDrawable(getResources(), bitmap);
										DisplayMetrics metrics = getResources().getDisplayMetrics();
										float i = metrics.widthPixels / drawable1.getIntrinsicWidth();
										float height = drawable1.getIntrinsicHeight() * i;
										float width = drawable1.getIntrinsicWidth() * i;
										drawable1.setBounds(0, 0, (int) width, (int) height);
										mImageCache.put(source, drawable1);
										try {
											adapter.notifyItemUpdate(0);
										} catch (Exception e) {
										}
									}

									@Override
									public void onBitmapFailed(Drawable errorDrawable) {

									}

									@Override
									public void onPrepareLoad(Drawable placeHolderDrawable) {

									}
								});
								return getResources().getDrawable(R.drawable.icon_comment_default);
							}
						}, null));
						TextView tv_praise = viewHolder.getView(R.id.tv_praise);

						if (contentInfo.isPraise) {
							iv_praise.setImageResource(R.drawable.image_praise_btn_select);
							tv_praise.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.image_praise_btn_select), null, null, null);

						} else {
							iv_praise.setImageResource(R.drawable.image_praise_btn_unselect);
							tv_praise.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.image_praise_btn_unselect), null, null, null);
						}
						tv_praise.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								savePraise(contentInfo.id, null, contentInfo.isPraise, AppConstants.PRAISE_COMMENT);
							}
						});
						viewHolder.getView(R.id.tv_comment).setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								doComment();
							}
						});
						RecyclerView recyclerView =viewHolder.getView(R.id.rv_view);
						final  List<CommentInfo.Item> data=new ArrayList<>();
						RecyclerAdapter adapter=new RecyclerAdapter(getContext(),data);
						adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<CommentInfo.Item>() {
							@Override
							public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder,final CommentInfo.Item item,final int position) {
								viewHolder.setText(R.id.tv_title, item.title);
								viewHolder.setText(R.id.tv_content, item.content);
								viewHolder.setText(R.id.tv_source, "来源：" + item.source);
								viewHolder.setText(R.id.tv_date, TimeUtil.getFmdLongTime(item.createTime));
								viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										setLastClickTime();
										Bundle bundle = new Bundle();
										bundle.putString(AppConstants.ITEM, item.id);
										ViewUtil.openActivity(CommentDetailActivity.class, bundle, getContext(), ActivityModel.ACTIVITY_MODEL_2);
									}
								});

								String url=null;
								if (CommonUtil.notNull(item.images) && item.images.size() > 0) {
									url=item.images.get(0).url;
								}
								Picasso.with(getContext()).load(DataModel.getImageUrl(url)).placeholder(R.drawable.icon_comment_default).error(R.drawable.icon_comment_error)
										.resize(160, 160).into((ImageView) viewHolder.getView(R.id.iv_photo));
							}

							@Override
							public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
								float width= getContext().getResources().getDisplayMetrics().widthPixels;
								View view=getLayoutInflater().inflate(R.layout.list_item_recommended,viewGroup,false);
								RecyclerView.LayoutParams params=new RecyclerView.LayoutParams((int)(width/6*5),RecyclerView.LayoutParams.MATCH_PARENT);
								view.setLayoutParams(params);
								return new RecyclerAdapter.ViewHolder(view);
							}

							@Override
							public int getItemCount() {
								return data.size();
							}
						});
						LinearLayoutManager manager=new LinearLayoutManager(getContext());
						manager.setOrientation(LinearLayoutManager.HORIZONTAL);
						recyclerView.setLayoutManager(manager);
						recyclerView.setAdapter(adapter);
						if (CommonUtil.isNull(contentInfo.list)){
							viewHolder.setViewHide(R.id.tv_recommend);
							viewHolder.setViewHide(R.id.rv_view);
						}else {
							viewHolder.setViewShow(R.id.tv_recommend);
							viewHolder.setViewShow(R.id.rv_view);
							data.addAll(contentInfo.list);
							adapter.notifyDataSetChanged();

						}

				} else {
					Util.loadPhoto(getContext(), item.headImage, (ImageView) viewHolder.getView(R.id.iv_photo));
					viewHolder.setText(R.id.tv_name, item.nickname);
					viewHolder.setText(R.id.tv_comment, item.content);
					viewHolder.setText(R.id.tv_date, TimeUtil.getFmdLongTime(item.createTime));
					TextView tv_praise=viewHolder.getView(R.id.tv_praise);
					tv_praise.setText(CommonUtil.initTextValue(item.praiseCount));

					if (item.replyUserId.equals(tokenInfo.userId+"")){
						tv_praise.setVisibility(View.GONE);
					}else {
						if (item.praise) {
							tv_praise.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.image_praise_btn_select), null, null, null);
						} else {
							tv_praise.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.image_praise_btn_unselect), null, null, null);
						}
						tv_praise.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								savePraise(item.id,item.replyId,item.praise, AppConstants.PRAISE_REEPLY);
							}
						});
					}

				}
			}

			@Override
			public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
				if (viewType == 22) {
					return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_head_quiz, viewGroup, false));
				}
				return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.listview_comments, viewGroup, false));
			}

			@Override
			public int getItemCount() {
				return mData.size();
			}
		});


		ViewUtil.initRecyclerViewDecoration(rv_view, getContext(), adapter);
		loadData();

	}



	/**
	 * 加载论坛数据
	 * 
	 */
	private void loadData() {
		refresh.setRefreshing(true);
		dataModel.getPostByMap(id, boardId, new SimpleResponseListener<CommentDetailInfo>() {
			@Override
			public void requestSuccess(final CommentDetailInfo info, Response response) {
				contentInfo = info;
				mData.clear();
				mData.add(null);
				if (CommonUtil.notNull(info.replyList) && info.replyList.size() > 0) {
					mData.addAll(info.replyList);
				}
				adapter.notifyDataSetChanged();

			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {

			}

			@Override
			public void requestView() {
				refresh.setRefreshing(false);
				adapter.checkEmpty();
			}
		});

	}
	@OnClick(R.id.iv_shared)
	void doShared(){
		//分享
	}


	@OnClick(R.id.iv_comment)
	 void doComment(){
		if (CommonUtil.isNull(contentInfo)){
			showMsg("暂无数据，无法评论");
			return;
		}
		setLastClickTime();
		Intent intent=new Intent(getContext(),CommentActivity.class);
		Bundle bundle=new Bundle();
		bundle.putString(AppConstants.ITEM, contentInfo.id);
		bundle.putInt(AppConstants.TYPE, AppConstants.TYPE_COMMENT);
		bundle.putString(AppConstants.DATA_CODE, contentInfo.createUserId);
		intent.putExtras(bundle);
		startActivityForResult(intent, AppConstants.CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==AppConstants.CODE){
			if (CommonUtil.notNull(data)){
				ReplyInfo info=new ReplyInfo();
				info.content=data.getStringExtra(AppConstants.DATA);
				info.nickname=userInfo.nickname;
				info.headImage=userInfo.headImage;
				info.createTime=System.currentTimeMillis()+"";
				info.replyUserId=userInfo.id+"";
				mData.add(1,info);
				adapter.notifyItemAdd(1);
			}
			Log.d("resultCode:.......");

		}
	}

	@OnClick(R.id.iv_praise)
	void onPraise(){
		if (CommonUtil.isNull(contentInfo)){
			showMsg("暂无数据，无法操作");
			return;
		}
		savePraise(contentInfo.id,null,contentInfo.isPraise, AppConstants.PRAISE_COMMENT);
	}

	 void savePraise(final String id,final String replyId,boolean isPraise,String type){
		setLastClickTime();
		if (isPraise){
			deletePraise(contentInfo.id,replyId,type);
		}else{
			dataModel.savePraise(id, replyId, type, contentInfo.createUserId, new SimpleResponseListener<ResponseMessage>() {
				@Override
				public void requestSuccess(ResponseMessage info, Response response) {
					if (CommonUtil.notNull(replyId)){
						//
					}else {
						contentInfo.praiseCount=CommonUtil.numberCount(contentInfo.praiseCount);
						contentInfo.isPraise=true;
						//adapter.notifyItemUpdate(0);
						updateStatus();
						showMsg("点赞成功");

					}


				}

				@Override
				public void requestError(HttpException e, ResponseMessage info) {
					showMsg("点赞失败");
				}
			});
		}

	}

	public void updateStatus(){
	   View view=rv_view.getLayoutManager().findViewByPosition(0);
		StringBuilder builder = new StringBuilder();
		builder.append("评论").append(CommonUtil.initTextValue(contentInfo.replyCount));
		((TextView)ButterKnife.findById(view,R.id.tv_comment_count)).setText(builder.toString());
		((TextView)ButterKnife.findById(view,R.id.tv_praise)).setText(CommonUtil.initTextValue(contentInfo.praiseCount));
		TextView tv_praise = ButterKnife.findById(view,R.id.tv_praise);
		if (contentInfo.isPraise) {
			iv_praise.setImageResource(R.drawable.image_praise_btn_select);
			tv_praise.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.image_praise_btn_select), null, null, null);

		} else {
			iv_praise.setImageResource(R.drawable.image_praise_btn_unselect);
			tv_praise.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.image_praise_btn_unselect), null, null, null);
		}




	}



	private void deletePraise(final String id,final String replyId,String type){
		dataModel.deletePraise(id, type, replyId, new SimpleResponseListener<ResponseMessage>() {
			@Override
			public void requestSuccess(ResponseMessage info, Response response) {
				if (CommonUtil.notNull(replyId)) {
					//
				} else {

					contentInfo.praiseCount = CommonUtil.numberCut(contentInfo.praiseCount);
					contentInfo.isPraise = false;
					//adapter.notifyItemUpdate(0);
					updateStatus();
					showMsg("取消赞成功");
				}
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				showMsg("取消赞失败");
			}
		});
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapter.onDestroyReceiver();
	}
	
	/**
	 * 举报内容
	 * @param id
	 */
	private void informReply(String id){
	/*	try {
			DataService.getInstance().informReply(this, BaseApplication.TOKEN, id);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 删除评论
	 * @param id
	 */
	private void deleteReply(String id){
		/*try {
			DataService.getInstance().deleteReply(this, BaseApplication.TOKEN, id);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
