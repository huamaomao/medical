package com.roller.medicine.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.domain.ResponseMessage;
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
import com.roller.medicine.info.ReplyInfo;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/****8
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
	private DataModel dataModel;

	private CommentDetailInfo contentInfo;
	private TokenInfo tokenInfo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_knowledge_quiz_content);
	}

	protected void initView() {
		id=getIntent().getExtras().getString(Constants.ITEM);
		setBackActivity("论坛正文");
		dataModel=new DataModel();
		mData=new ArrayList();
		tokenInfo=dataModel.getToken();
		refresh.setRefreshStyle(Constants.PULL_STYLE);
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
					viewHolder.setText(R.id.tv_comment, CommonUtil.initTextValue(contentInfo.replyCount));
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
							savePraise(contentInfo.id,null,contentInfo.isPraise,Constants.PRAISE_COMMENT);
						}
					});
					viewHolder.getView(R.id.tv_comment).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							doComment();
						}
					});
						/*@InjectView(R.id.horizontal_listview)
					HorizontalListView horizontal_listview;*/

				} else {
					Util.loadPhoto(getContext(), item.headImage, (ImageView) viewHolder.getView(R.id.iv_photo));
					viewHolder.setText(R.id.tv_name, item.nickname);
					viewHolder.setText(R.id.tv_comment, item.content);
					viewHolder.setText(R.id.tv_date,TimeUtil.getFmdLongTime(item.createTime));
					TextView tv_praise=viewHolder.getView(R.id.tv_praise);
					tv_praise.setText(CommonUtil.initTextValue(item.praiseCount));
					if (item.replyUserId.equals(tokenInfo.userId)){
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
								savePraise(item.id,item.replyId,item.praise,Constants.PRAISE_REEPLY);
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
		dataModel.getPostByMap(id, null, new SimpleResponseListener<CommentDetailInfo>() {
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
		bundle.putString(Constants.ITEM, contentInfo.id);
		bundle.putInt(Constants.TYPE, Constants.TYPE_COMMENT);
		bundle.putString(Constants.DATA_CODE, contentInfo.createUserId);
		intent.putExtras(bundle);
		startActivityForResult(intent, Constants.CODE);
	}
	@OnClick(R.id.iv_praise)
	void onPraise(){
		if (CommonUtil.isNull(contentInfo)){
			showMsg("暂无数据，无法操作");
			return;
		}
		savePraise(contentInfo.id,null,contentInfo.isPraise,Constants.PRAISE_COMMENT);
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
						adapter.notifyItemUpdate(0);
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

	private void deletePraise(final String id,final String replyId,String type){
		dataModel.deletePraise(id,type,replyId,new SimpleResponseListener<ResponseMessage>() {
			@Override
			public void requestSuccess(ResponseMessage info, Response response) {
				if (CommonUtil.notNull(replyId)){
					//
				}else {
					contentInfo.praiseCount=CommonUtil.numberCut(contentInfo.praiseCount);
					contentInfo.isPraise=false;
					adapter.notifyItemUpdate(0);
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
	 * 发表评论
	 * @param id
	 * @param boardId
	 * @param content
	 * @param byReplyUserId 评论帖子时为createUserId  评论个人时为replyId
	 */
	private void saveReply(String id,String boardId,String content,String byReplyUserId){
		/*try {
			DataService.getInstance().saveReply(this, BaseApplication.TOKEN, id, boardId, content, byReplyUserId);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
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
	
/*	@Override
	public void onSuccess(String url, String result, int resultCode, Object tag) {
		super.onSuccess(url, result, resultCode, tag);
		if (resultCode != 200) {
			return;
		}
		
		KnowledgeQuizContentInfo mInfo = JSON.parseObject(result,KnowledgeQuizContentInfo.class);
		text_from.setText("晚上22:30  来源：" + mInfo.getSource());
		text_content_title.setText(mInfo.getTitle());
		text_content.setText(Html.fromHtml(mInfo.getContent()));
		text_of_taste.setText(mInfo.getReplyCount());
		text_praidse_count.setText(mInfo.getPraiseCount());
		linearlayout_praise.setTag(mInfo);
		linearlayout_praise.setTag(R.id.is_top_praise, mInfo.getIsPraise());
		image_praise_buttom.setTag(mInfo);
		image_praise_buttom.setTag(R.id.is_buttom_praise, mInfo.getIsPraise());
		button_release.setTag(mInfo.getCreateUserId());
		if (mInfo.getIsPraise().equals("true")){
			image_praidse.setBackgroundResource(R.drawable.image_praise_btn_select);
			image_praise_buttom.setImageDrawable(getResources().getDrawable(R.drawable.image_praise_btn_select));
		}
		
		mListDatas.addAll(mInfo.getList());
		listAdapter.notifyDataSetChanged();
		
		if(mInfo.getReplyList() != null && mInfo.getReplyList().size() > 0){
			text_comments_count.setVisibility(View.VISIBLE);
			text_comments_count.setText("评论"+mInfo.getReplyCount());
			mReplyListDatas.addAll(mInfo.getReplyList());
			replyListAdapter.notifyDataSetChanged();
		}
		
		scrollview.post(new Runnable() {
			
			@Override
			public void run() {
				scrollview.scrollTo(0, 0);
			}
		});
		
	}

	@OnClick({R.id.image_comments,R.id.linearlayout_praise,R.id.image_praise_buttom
		,R.id.text_pupopwindow_cancel,R.id.text_of_taste,R.id.button_release
		,R.id.text_pupopwindow_inform_reply,R.id.text_pupopwindow_delete,R.id.text_pupopwindow_reply})
	public void onViewOnClick(View view){
		switch (view.getId()) {
		case R.id.image_comments:
		case R.id.text_of_taste:
		case R.id.text_pupopwindow_reply:
			popupWindowDismiss();
			commentsIsContent = true;
			edit_comments_content.requestFocus();
			OtherUtils.showEditTextInput(edit_comments_content);
			include_comments_popuwindow.setVisibility(View.VISIBLE);
			break;
			
		case R.id.button_release:
			include_comments_popuwindow.setVisibility(View.GONE);
			String Id = null;
			if(commentsIsContent){
				Id = view.getTag().toString();
			}else{
				Id = view.getTag(R.id.id).toString();
			}
			if(Id != null)saveReply(bundle.getString(Constants.ID),bundle.getString("boardId"),edit_comments_content.getText().toString(),Id);
			break;
			
		case R.id.linearlayout_praise:
			buttomPraise = false;
		case R.id.image_praise_buttom:
			String isPraise = null;
			if(buttomPraise){
				isPraise = view.getTag(R.id.is_buttom_praise).toString();
			}else{
				isPraise = view.getTag(R.id.is_top_praise).toString();
				buttomPraise = true;
			}
			
			KnowledgeQuizContentInfo item = (KnowledgeQuizContentInfo) view.getTag();
			int count = Integer.valueOf(text_praidse_count.getText().toString());
			if(isPraise.equals("false")){
				image_praise_buttom.setTag(R.id.is_buttom_praise, "true");
				linearlayout_praise.setTag(R.id.is_top_praise, "true");
				text_praidse_count.setText((count + 1) + "");
				image_praidse.setImageDrawable(getResources().getDrawable(R.drawable.image_praise_btn_select));
				image_praise_buttom.setImageDrawable(getResources().getDrawable(R.drawable.image_praise_btn_select));
				savePraise(item.id, item.getReplyId(), "74", item.getCreateUserId());
			}else{
				image_praise_buttom.setTag(R.id.is_buttom_praise, "false");
				linearlayout_praise.setTag(R.id.is_top_praise, "false");
				text_praidse_count.setText((count - 1) + "");
				image_praidse.setImageDrawable(getResources().getDrawable(R.drawable.image_praise_btn_unselect));
				image_praise_buttom.setImageDrawable(getResources().getDrawable(R.drawable.image_praise_btn_unselect));
				deletePraise(item.id);
			}
			break;
		
		case R.id.text_pupopwindow_cancel:
			popupWindowDismiss();
			break;
			
		case R.id.text_pupopwindow_inform_reply:
			popupWindowDismiss();
			informReply(text_pupopwindow_inform_reply.getTag().toString());
			break;
			
		case R.id.text_pupopwindow_delete:
			popupWindowDismiss();
			deleteReply(text_pupopwindow_delete.getTag().toString());
			break;
		}
	}
	

	
	@Override
	public void commonOnClick(View v) {

	}

	@Override
	public void commonGetView(PublicViewHolder helper, Object item,
			OnClickListener onClick, int position, Object tag) {
		int from = Integer.valueOf(tag.toString());
		switch (from) {
		case Constants.TAG.TAG_RECOMMENDED_LIST:
			mBitmapUtils = XUtilsBitmapHelp.getBitmapUtilsInstance(
					this,R.drawable.public_image_loading, R.drawable.public_image_failure);
			
			KnowledgeQuizItemInfo mItemInfo = (KnowledgeQuizItemInfo) item;
			LinearLayout linearlayout_item_recommend = helper.getView(R.id.linearlayout_item_recommend);
			ImageView image_item_rPicture = helper.getView(R.id.image_item_picture);
			TextView text_item_content = helper.getView(R.id.text_item_content);
			TextView text_item_from = helper.getView(R.id.text_item_from);
			
			LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) linearlayout_item_recommend.getLayoutParams();
			int width = (int) (dm.widthPixels * 0.8);
			lParams.width = width;
			linearlayout_item_recommend.setLayoutParams(lParams);
			if(mItemInfo.getImages().size() > 0)mBitmapUtils.display(image_item_rPicture, mItemInfo.getImages().get(0).getUrl());
			text_item_content.setText(mItemInfo.getContent());
			text_item_from.setText("来源：椰子 2015-01-11 14:14");
			break;

		case Constants.TAG.TAG_COMMENTS_LIST:
			mHeadBitmapUtils = XUtilsBitmapHelp.getBitmapUtilsInstance(
					this,R.drawable.public_default_head, R.drawable.public_default_head);
			
			KnowledgeQuizContentReplyListItemInfo mReplyListItemInfo = (KnowledgeQuizContentReplyListItemInfo) item;
			ImageView image_item_head = helper.getView(R.id.image_item_head);
			TextView text_item_name = helper.getView(R.id.text_item_name);
			TextView text_item_time = helper.getView(R.id.text_item_time);
			TextView text_item_Ccontent = helper.getView(R.id.text_item_content);
			LinearLayout linearlayout_item_praise = helper.getView(R.id.linearlayout_item_praise);
			ImageView image_item_praidse = helper.getView(R.id.image_item_praidse);
			TextView text_item_praidse_count = helper.getView(R.id.text_item_praidse_count);
			
			linearlayout_item_praise.setTag(mReplyListItemInfo);
			
			linearlayout_item_praise.setOnClickListener(onClick);
			
			if (mReplyListItemInfo.getPraise().equals("true")){
				image_item_praidse.setBackgroundResource(R.drawable.image_praise_btn_select);
			}
			
			mHeadBitmapUtils.display(image_item_head, mReplyListItemInfo.getHeadImage(), OtherUtils.roundBitmapLoadCallBack);
			break;
			
		}
	}
	

	
	private void popupWindowDismiss(){	
		if(mPopupWindow != null && mPopupWindow.isShowing()){
			mPopupWindow.dismiss();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		KnowledgeQuizContentReplyListItemInfo mReplyListItemInfo = (KnowledgeQuizContentReplyListItemInfo) mReplyListDatas.get(position);
		commentsIsContent = false;
		button_release.setTag(R.id.id, mReplyListItemInfo.getReplyId());
		if(BaseApplication.USERID.equals(mReplyListItemInfo.getReplyUserId())){
			text_pupopwindow_delete.setVisibility(View.VISIBLE);
			text_pupopwindow_inform_reply.setVisibility(View.GONE);
			text_pupopwindow_delete.setTag(mReplyListItemInfo.getId());
		}else{
			text_pupopwindow_delete.setVisibility(View.GONE);
			text_pupopwindow_inform_reply.setVisibility(View.VISIBLE);
			text_pupopwindow_inform_reply.setTag(mReplyListItemInfo.getId());
		}
		mPopupWindow.showAtLocation(getWindow().getDecorView(), 0, 0, 0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(mPopupWindow != null && mPopupWindow.isShowing()){
				mPopupWindow.dismiss();
				return false;
			}
			
			if(include_comments_popuwindow.getVisibility() == View.VISIBLE){
				include_comments_popuwindow.setVisibility(View.GONE);
				return false;
			}
			
			return super.onKeyDown(keyCode, event);
		}
		
		return super.onKeyDown(keyCode, event);
	}
	*/
}
