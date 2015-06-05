package com.roller.medicine.ui;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.adapter.PublicViewAdapter.ICommonGetView;
import com.roller.medicine.adapter.PublicViewAdapter.ICommonOnClick;
import com.roller.medicine.adapter.PublicViewHolder;
import com.roller.medicine.base.BaseActivity;
import com.roller.medicine.base.BaseApplication;
import com.roller.medicine.customview.listview.HorizontalListView;
import com.roller.medicine.customview.listview.PublicListView;
import com.roller.medicine.customview.scrollview.PublicScrollView;
import com.roller.medicine.httpservice.Constants;
import com.roller.medicine.httpservice.DataService;
import com.roller.medicine.info.KnowledgeQuizContentInfo;
import com.roller.medicine.info.KnowledgeQuizContentReplyListItemInfo;
import com.roller.medicine.info.KnowledgeQuizItemImageListInfo;
import com.roller.medicine.info.KnowledgeQuizItemInfo;
import com.roller.medicine.utils.OtherUtils;
import com.roller.medicine.utils.XUtilsBitmapHelp;

public class KnowledgeQuizContentActivity extends BaseActivity implements
		ICommonGetView<Object>, ICommonOnClick ,OnItemClickListener{

	@ViewInject(R.id.text_title)
	private TextView text_title;
	@ViewInject(R.id.text_from)
	private TextView text_from;
	@ViewInject(R.id.text_content_title)
	private TextView text_content_title;
	@ViewInject(R.id.text_content)
	private TextView text_content;
	@ViewInject(R.id.text_of_taste)
	private TextView text_of_taste;
	@ViewInject(R.id.text_comments_count)
	private TextView text_comments_count;
	@ViewInject(R.id.image_praidse)
	private ImageView image_praidse;
	@ViewInject(R.id.text_praidse_count)
	private TextView text_praidse_count;
	@ViewInject(R.id.horizontal_listview)
	private HorizontalListView horizontal_listview;
	@ViewInject(R.id.public_of_taste_listview)
	private PublicListView public_of_taste_listview;
	@ViewInject(R.id.edit_comments_content)
	private EditText edit_comments_content;
	@ViewInject(R.id.include_comments_popuwindow)
	private View include_comments_popuwindow;
	@ViewInject(R.id.linearlayout_praise)
	private LinearLayout linearlayout_praise;
	@ViewInject(R.id.image_praise_buttom)
	private ImageView image_praise_buttom;
	@ViewInject(R.id.scrollview)
	private ScrollView scrollview;
	@ViewInject(R.id.button_release)
	private Button button_release;
	@ViewInject(R.id.text_pupopwindow_delete)
	private TextView text_pupopwindow_delete;
	@ViewInject(R.id.text_pupopwindow_inform_reply)
	private TextView text_pupopwindow_inform_reply;

	private PublicViewAdapter<Object> listAdapter;
	private PublicViewAdapter<Object> replyListAdapter;
	private LinkedList<Object> mListDatas = new LinkedList<Object>();
	private LinkedList<Object> mReplyListDatas = new LinkedList<Object>();
	private BitmapUtils mBitmapUtils;
	private BitmapUtils mHeadBitmapUtils;
	private DisplayMetrics dm;
	private PopupWindow mPopupWindow;
	private Bundle bundle;
	private boolean commentsIsContent = true;
	private boolean buttomPraise = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_knowledge_quiz_content);
		ViewUtils.inject(this);
		initView();
	}

	@SuppressLint("NewApi")
	private void initView() {
		text_title.setText("论坛正文");
		initPopupWindow();
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		bundle = this.getIntent().getBundleExtra("bundle");
		public_of_taste_listview.setOnItemClickListener(this);
		
		listAdapter = new PublicViewAdapter<Object>(this, mListDatas,
				R.layout.listview_recommended, this, this,Constants.TAG.TAG_RECOMMENDED_LIST);

		horizontal_listview.setAdapter(listAdapter);
		replyListAdapter = new PublicViewAdapter<Object>(this, mReplyListDatas,
				R.layout.listview_comments, this, this,Constants.TAG.TAG_COMMENTS_LIST);

		public_of_taste_listview.setAdapter(replyListAdapter);
		getPostByMap();
	}

	/**
	 * 加载论坛数据
	 * 
	 * @param id
	 * @param boardId
	 */
	private void getPostByMap() {
		try {
			DataService.getInstance().getPostByMap(this, BaseApplication.TOKEN,
					bundle.getString(Constants.ID), bundle.getString("boardId"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 评论帖子时为createUserId
	 * 评论个人时为replyId
	 * 点赞
	 */
	private void savePraise(String postId,String repiyId,String typeId,String mainUserId){
		try {
			DataService.getInstance().savePraise(this, BaseApplication.TOKEN, postId, repiyId, typeId, mainUserId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消赞
	 * @param id
	 */
	private void deletePraise(String id){
		try {
			DataService.getInstance().deletePraise(this, BaseApplication.TOKEN,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发表评论
	 * @param id
	 * @param boardId
	 * @param content
	 * @param byReplyUserId 评论帖子时为createUserId  评论个人时为replyId
	 */
	private void saveReply(String id,String boardId,String content,String byReplyUserId){
		try {
			DataService.getInstance().saveReply(this, BaseApplication.TOKEN, id, boardId, content, byReplyUserId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 举报内容
	 * @param id
	 */
	private void informReply(String id){
		try {
			DataService.getInstance().informReply(this, BaseApplication.TOKEN, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除评论
	 * @param id
	 */
	private void deleteReply(String id){
		try {
			DataService.getInstance().deleteReply(this, BaseApplication.TOKEN, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
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
	

	/**
	 * 底部弹窗
	 */
	private void initPopupWindow() {
		View view = getLayoutInflater().inflate(R.layout.layout_pupopwindow_knowledge_item, null);
		ViewUtils.inject(this, view);
		mPopupWindow = new PopupWindow(view,WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT, true);
		mPopupWindow.setTouchable(true);
		view.setFocusableInTouchMode(true);
		view.setFocusable(true);
		view.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && mPopupWindow.isShowing()) {
					popupWindowDismiss();
					return false;
				} else {
					return true;
				}
			}
		});;
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
	
}
