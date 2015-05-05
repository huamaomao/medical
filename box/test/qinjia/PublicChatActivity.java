package com.tryhard.workpai.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.gotye.api.GotyeChatTargetType;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeMessageStatus;
import com.gotye.api.GotyeUser;
import com.gotye.api.WhineMode;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnTouch;
import com.tryhard.workpai.R;
import com.tryhard.workpai.adapter.ChatMessageAdapter;
import com.tryhard.workpai.adapter.PublicViewAdapter.ICommonOnClick;
import com.tryhard.workpai.base.BaseApplication;
import com.tryhard.workpai.base.BaseChatActivity;
import com.tryhard.workpai.customview.pulltorefreshview.PullToRefreshBase;
import com.tryhard.workpai.customview.pulltorefreshview.PullToRefreshListView;
import com.tryhard.workpai.httpservice.Constants;
import com.tryhard.workpai.utils.OtherUtils;
import com.tryhard.workpai.utils.PathUtil;
import com.tryhard.workpai.utils.SendImageMessageTask;
import com.tryhard.workpai.utils.URIUtil;
import com.umeng.analytics.MobclickAgent;

public class PublicChatActivity extends BaseChatActivity implements ICommonOnClick {
	
	private Context context;
	

	private static final int INTENT_REQUEST_PIC = 1;
	private static final int INTENT_REQUEST_CAMERA = 2;
	/** 拍照发送图片的图片存储路径 */
	private File cameraFile;
	
	
	@ViewInject(R.id.progressbar_small)
	private ProgressBar mLoadJuhuaPb;
	@ViewInject(R.id.title)
	private TextView mHeadTitle;
	@ViewInject(R.id.public_chat_userinfo_tv)
	private TextView mSeeUserInfoTv;
	@ViewInject(R.id.chat_msg_plv)
	private PullToRefreshListView mMsgPlv;
	@ViewInject(R.id.chat_to_text_voice)
	private ImageView mTextOrVoiceImg;
	@ViewInject(R.id.chat_press_to_voice)
	private Button mProssToVoiceBtn;/* 按住说话按钮 */
	@ViewInject(R.id.chat_text_msg_input)
	private EditText mTextMsgInputEd;/* 文本信息输入框 */
	@ViewInject(R.id.chat_send_tv)
	private TextView mTextSendTv;/* 发送按钮 */
	@ViewInject(R.id.chat_more_type)
	private ImageView mMoreTypeBtn;/* 显示更多选项按钮 */
	@ViewInject(R.id.chat_more_type_layout)
	private LinearLayout mMoreTypeLl;/* 发送更多类型Box */
	
	
	
	public ChatMessageAdapter mAdapter;

	/** 当前登陆的user */
	public GotyeUser currentLoginUser;
	/** 对面聊天的user */
	public GotyeUser otherUser = null;
	/** 对面聊天的group */
	private GotyeGroup otherGroup = null;
	/** 聊天类型 */
	public GotyeChatTargetType chatType = null;
	/** 正在发送的语音Id */
	private long playingId;
	
	/** 是否取消发送语音 */
	private boolean isCancelSendVoice = false;

	private PopupWindow mChatVoicePopw;
	private ImageView mChatVoicePopwImg;
	private TextView mChatVoicePopwTv;
	
	
	// 动画资源文件,用于录制语音时
	private Drawable[] recoreAnimate;
	
	
	/**
	 * 弹出语音popupwindow
	 */
	public void initChatVoicePopw(){
		recoreAnimate = new Drawable[] { getResources().getDrawable(R.drawable.chat_record_animate_01),
				getResources().getDrawable(R.drawable.chat_record_animate_02), getResources().getDrawable(R.drawable.chat_record_animate_03),
				getResources().getDrawable(R.drawable.chat_record_animate_04), getResources().getDrawable(R.drawable.chat_record_animate_05),
				getResources().getDrawable(R.drawable.chat_record_animate_06), getResources().getDrawable(R.drawable.chat_record_animate_07),
				getResources().getDrawable(R.drawable.chat_record_animate_08), getResources().getDrawable(R.drawable.chat_record_animate_09),
				getResources().getDrawable(R.drawable.chat_record_animate_10), getResources().getDrawable(R.drawable.chat_record_animate_11),
				getResources().getDrawable(R.drawable.chat_record_animate_12), getResources().getDrawable(R.drawable.chat_record_animate_13),
				getResources().getDrawable(R.drawable.chat_record_animate_14), };
		
		LayoutInflater inflater = LayoutInflater.from(PublicChatActivity.this);
		View view = inflater.inflate(R.layout.popupwindow_chat_voice, null);
		mChatVoicePopwImg = (ImageView) view.findViewById(R.id.chat_voice_anim_img);
		mChatVoicePopwTv = (TextView) view.findViewById(R.id.chat_voice_anim_cancel_tv);
		mChatVoicePopw = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
	}

	private Handler talkPowerImageHandler = new Handler() {
		int lastTalkPowerIndex = 0;
		@Override
		public void handleMessage(android.os.Message msg) {
			int talkPowerImgIndex = msg.what/17;
			if(talkPowerImgIndex >= recoreAnimate.length-1){
				talkPowerImgIndex = recoreAnimate.length-1;
			}
			// 切换msg切换图片
			mChatVoicePopwImg.setImageDrawable(recoreAnimate[(talkPowerImgIndex+lastTalkPowerIndex)/2]);
			lastTalkPowerIndex = talkPowerImgIndex;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		ViewUtils.inject(this);
		context = getApplicationContext();
		currentLoginUser = api.getLoginUser();
		api.addListener(this);
		otherUser = (GotyeUser) getIntent().getSerializableExtra(Constants.CHAT.CHAT_TYPE_USER);
		otherGroup = (GotyeGroup) getIntent().getSerializableExtra(Constants.CHAT.CHAT_TYPE_GROUP);
		initView();
		initChatVoicePopw();
		
		if(chatType == GotyeChatTargetType.GotyeChatTargetTypeUser){
			api.activeSession(otherUser);
			/** 由于activeSession只能在本地缓存, 所以通过只要聊过天的用户就将之加为好友解决换手机不显示用户的情况 */
			api.requestAddFriend(otherUser);
		}else if(chatType == GotyeChatTargetType.GotyeChatTargetTypeGroup){
			api.activeSession(otherGroup);
		}
		loadInitData();
		int state=api.isOnLine();
		if(state!=1){
			setErrorTip(0);
		}else{
			setErrorTip(1);
		}
	}

	private void initView() {
		setTitleText();
		mLoadJuhuaPb.setVisibility(View.GONE);
		mMsgPlv.setPullRefreshEnabled(true);
		mMsgPlv.setPullLoadEnabled(false);
		mMsgPlv.getRefreshableView().setDivider(null);
		mMsgPlv.getRefreshableView().setScrollbarFadingEnabled(false);
		mAdapter = new ChatMessageAdapter(this, new ArrayList<GotyeMessage>(), this);
		mMsgPlv.getRefreshableView().setAdapter(mAdapter);
		mMsgPlv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount()+1);
//		mMsgPlv.doPullRefreshing(true, 300);
		setListViewListener();
	}
	
	/**
	 * 刚进聊天界面加载本地聊天记录
	 */
	private void loadInitData(){
		List<GotyeMessage> messages = null;
		if (otherUser != null) {
			messages = api.getMessageList(otherUser, true);
		}else if (otherGroup != null) {
			messages = api.getMessageList(otherGroup, true);
		}
		if (messages == null) {
			messages = new ArrayList<GotyeMessage>();
		}
		for (GotyeMessage msg : messages) {
			api.downloadMediaInMessage(msg);
		}
		mAdapter.refreshData(messages);
		mMsgPlv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount()+1);
	}
	
	/***
	 * 标题栏显示
	 */
	@SuppressLint("NewApi")
	private void setTitleText(){
		if(otherUser != null){
			chatType = GotyeChatTargetType.GotyeChatTargetTypeUser;
			String nameStr = BaseApplication.getInstance().targetUserNick;
			if(nameStr == null || "".equals(nameStr)){
				nameStr = otherUser.getName();
			}
			mHeadTitle.setText(nameStr);
//			api.requestUserInfo("apple", true);
		}else if(otherGroup != null){
			String titleStr = "";
			chatType = GotyeChatTargetType.GotyeChatTargetTypeGroup;
			if(otherGroup.getGroupName() == null
					|| "".equals(otherGroup.getGroupName())){
				titleStr = otherGroup.getGroupName();
			} else {
				GotyeGroup target = api.getGroupDetail(otherGroup.getGroupID(), true);
				if(target == null || target.getGroupName() == null 
						|| "".equals(target.getGroupName())){
					titleStr = "群号:"+otherGroup.getGroupID();
				}else{
					titleStr = target.getGroupName();
				}
			}
			mHeadTitle.setText(titleStr);
		}
	}
	
	@OnClick({R.id.chat_to_text_voice, R.id.chat_send_tv
		, R.id.chat_more_type, R.id.chat_more_type_gallery
		, R.id.chat_more_type_camera, R.id.public_chat_userinfo_tv})
	public void onViewClick(View view){
		int viewId = view.getId();
		switch (viewId) {
		case R.id.chat_to_text_voice:
			if (mProssToVoiceBtn.getVisibility() == View.VISIBLE) {
				//发送文字模板
				mProssToVoiceBtn.setVisibility(View.GONE);
				mTextMsgInputEd.setVisibility(View.VISIBLE);
				mTextSendTv.setVisibility(View.VISIBLE);
				mTextOrVoiceImg
						.setImageResource(R.drawable.chat_voice_btn_selector);
				mMoreTypeLl.setVisibility(View.GONE);
			}else{
				//发送语音模板
				mProssToVoiceBtn.setVisibility(View.VISIBLE);
				mTextMsgInputEd.setVisibility(View.GONE);
				mTextSendTv.setVisibility(View.GONE);
				mTextOrVoiceImg
						.setImageResource(R.drawable.chat_text_btn_selector);
				hideKeyboard();
			}
			break;
		case R.id.chat_send_tv:
			hideKeyboard();
			String msgStr = mTextMsgInputEd.getText().toString();
			sendTextMessage(msgStr);
			mTextMsgInputEd.setText("");
			break;
		case R.id.chat_more_type:
			hideKeyboard();
			if (mMoreTypeLl.getVisibility() == View.VISIBLE){
				mMoreTypeLl.setVisibility(View.GONE);
			}else{
				mMoreTypeLl.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.chat_more_type_gallery:
			//TODO 相册选择图片
			takePic();
			break;
			
		case R.id.chat_more_type_camera:
			//TODO 拍照图片
			takePhoto();
			break;
		case R.id.public_chat_userinfo_tv:
			Bundle bundle = new Bundle();
			bundle.putString("opusername", otherUser.getName());
			openActivity(YPersonalInformationViewPageActivity.class, bundle);
			onGoActivity();
			break;
		default:
			break;
		}
	}
	
	@OnTouch(R.id.chat_press_to_voice)
	public boolean onTouch(View v, MotionEvent event){
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isCancelSendVoice = false;
			int code = 0;
			if (chatType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
				code=api.startTalk(otherUser, WhineMode.DEFAULT, false, 60 * 1000);
			} else if (chatType == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
				code=api.startTalk(otherGroup, WhineMode.DEFAULT, false,
						60 * 1000);
			}
			mChatVoicePopw.showAtLocation(v, Gravity.CENTER, 0, 0);
			mProssToVoiceBtn.setText("松开 发送");
			break;
		case MotionEvent.ACTION_UP:
			mChatVoicePopw.dismiss();
			int upstopCode = api.stopTalk();
			LogUtils.i("停止发送语音up_code:"+upstopCode);
			if(isCancelSendVoice){
				if (chatType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
					LogUtils.i("删除上一条语音up");
					api.deleteMessage(api.getLastMessage(otherUser));
				} else if (chatType == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
					api.deleteMessage(api.getLastMessage(otherGroup));
				}
			}
			mProssToVoiceBtn.setText("按住 说话");
			break;
		case MotionEvent.ACTION_CANCEL:
			mChatVoicePopw.dismiss();
			if(isCancelSendVoice){
				if (chatType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
					LogUtils.i("删除上一条语音cancel");
					api.deleteMessage(api.getLastMessage(otherUser));
				} else if (chatType == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
					api.deleteMessage(api.getLastMessage(otherGroup));
				}
			}
			int cancelstopCode = api.stopTalk();
			mProssToVoiceBtn.setText("按住 说话");
			LogUtils.i("停止发送语音cancel_code:"+cancelstopCode);
			break;
		case MotionEvent.ACTION_MOVE:
			int talkPower = api.getTalkingPower();//power取值范围[0, 255]
			Message msg = new Message();
			msg.what = talkPower;
			talkPowerImageHandler.dispatchMessage(msg);
			LogUtils.i("声音大小:"+talkPower);
			if (event.getY() < 0){
				mChatVoicePopwTv.setText("松开手指,取消发送");
				isCancelSendVoice = true;
			}else{
				mChatVoicePopwTv.setText("手指上滑,取消发送");
			}
			break;
		default:
//			LogUtils.d("无法判断是否该发送语音Action"+event.getAction());
			break;
		}
		
		
		return false;
	}
	
	private void setListViewListener() {
		mMsgPlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				List<GotyeMessage> list = null;

				if (chatType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
					list = api.getMessageList(otherUser, true);
				} else if (chatType == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
					list = api.getMessageList(otherGroup, true);
				}
				LogUtils.i("找到本地聊天记录"+list);
				if (list != null && list.size() > 0) {
					for (GotyeMessage msg : list) {
						api.downloadMediaInMessage(msg);
						LogUtils.i("找到的消息:"+msg.getText());
					}
					mAdapter.refreshData(list);
				} else {
					Toast.makeText(context, "没有更多历史消息", 0).show();;
				}
				mAdapter.notifyDataSetChanged();
				mMsgPlv.onPullDownRefreshComplete();
				mMsgPlv.onPullUpRefreshComplete();
//				pullListView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				
			}
		});

	}
	
	/**
	 * 图片已经处理到预定大小并且发送成功
	 * @param msg
	 */
	public void callBackSendImageMessage(GotyeMessage msg) {
		mAdapter.addMsgToBottom(msg);
		scrollToBottom();
	}

	
	/**
	 * 发送普通文字信息
	 * @param msgStr
	 */
	private void sendTextMessage(String msgStr){
		if(msgStr == null || "".equals(msgStr)) return;
		GotyeMessage toSendMsg = null;
		if(chatType == GotyeChatTargetType.GotyeChatTargetTypeUser){
			LogUtils.i("是user");
			toSendMsg = GotyeMessage.createTextMessage(currentLoginUser, otherUser,
					msgStr);
		} else if (chatType == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
			LogUtils.i("是group");
			toSendMsg = GotyeMessage.createTextMessage(currentLoginUser, otherUser,
					msgStr);
		} else {
			LogUtils.i("不是user也不是group");
//			Toast.makeText(context, "不是user也不是group", 0).show();
		}
		/** toSend.putExtraData(extraStr.getBytes()); 发送额外信息 */
		int code = api.sendMessage(toSendMsg);
		LogUtils.d("发送普通文字信息Code:"+code);
		mAdapter.addMsgToBottom(toSendMsg);
		scrollToBottom();
	}
	
	@Override
	public void onSendMessage(int code, GotyeMessage message) {
		LogUtils.i("onSendMessage:"+code+"-message:"+message.getText()+"-status:"+api.isOnLine()+"-user:"+api.getLoginUser());
		mAdapter.updateMessage(message);
		mMsgPlv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount());
	}
	
	@Override
	public void onReceiveMessage(GotyeMessage message) {
		super.onReceiveMessage(message);
		if (chatType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			LogUtils.i("onReceiveMessage=user:"+otherUser.getName()+"-"+message.getSender().getName()
					+"|"+currentLoginUser.getName()+"-"+message.getReceiver().getName()+"|"+api.getLoginUser().getName());
			LogUtils.i("收到信息发送者ICON:"+((GotyeUser)message.getSender()).getIcon());
			LogUtils.i("收到信息发送者ICON:"+api.getUserDetail(message.getSender().getName(),false).getIcon());
			
			if(message.getSender()!=null
					&& otherUser.getName().equals(message.getSender().getName())
					&& currentLoginUser.getName().equals(message.getReceiver().getName())){
				mAdapter.addMsgToBottom(message);
				mAdapter.notifyDataSetChanged();
				mMsgPlv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount()+1);
				api.downloadMediaInMessage(message);
			}
		} else if (chatType == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
			LogUtils.i("onReceiveMessage=group:"+message.getReceiver().getId()+"-"+otherGroup.getGroupID());
			if (message.getReceiver().getId() == otherGroup.getGroupID()) {
				mAdapter.addMsgToBottom(message);
				mAdapter.notifyDataSetChanged();
				mMsgPlv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount());
				api.downloadMediaInMessage(message);
			}
		}
		
	}
	
	public List<GotyeMessage> toSendTalk;
	
	@Override
	public void onStopTalk(int code, GotyeMessage message, boolean isVoiceReal) {
		LogUtils.i("onStopTalk:"+code);
		if(isCancelSendVoice){
			Toast.makeText(context, "取消发送", 0).show();
			return;
		}
		if (isVoiceReal) {
			/*realTimeAnim.stop();
			realTalkView.setVisibility(View.GONE);*/
		} else {
			if (code != 0) {
				Toast.makeText(context, "时间太短", 0).show();
				return;
			} else if (message == null) {
				Toast.makeText(context, "时间太短", 0).show();
				return;
			}
			int decodeCode = api.decodeMessage(message);
			LogUtils.i("api.decodeMessage_code:"+decodeCode);
			if (toSendTalk == null) {
				toSendTalk = new ArrayList<GotyeMessage>();
			}
			toSendTalk.add(message);
			 api.sendMessage(message);
			 message.setStatus(GotyeMessageStatus.GotyeMessageStatusSending);
			 mAdapter.addMsgToBottom(message);
			 scrollToBottom();
		}
	}
	
	@Override
	public void onGetMessageList(int code, List<GotyeMessage> list) {
		LogUtils.i("在亲加服务器中取到聊天数据:"+code+"-list:"+list.toString());
		
		if (chatType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			List<GotyeMessage> listmessages = api.getMessageList(otherUser, false);
			LogUtils.i("请求服务器聊天记录"+listmessages);
			if (listmessages != null && listmessages.size() > 0) {
				for (GotyeMessage temp : listmessages) {
					api.downloadMediaInMessage(temp);
				}
				mAdapter.refreshData(listmessages);
			} else {
				Toast.makeText(this, "没有历史记录", 0).show();
			}
		}else if (chatType == GotyeChatTargetType.GotyeChatTargetTypeGroup){
			List<GotyeMessage> listmessages = api.getMessageList(otherGroup, false);
			if (listmessages != null) {
				for (GotyeMessage temp : listmessages) {
					api.downloadMediaInMessage(temp);
				}
				mAdapter.refreshData(listmessages);
			} else {
				Toast.makeText(this, "没有历史记录", 0).show();
			}
		}
		mAdapter.notifyDataSetInvalidated();
//		pullListView.onRefreshComplete();
		mMsgPlv.onPullDownRefreshComplete();
		mMsgPlv.onPullUpRefreshComplete();
	}
	
	@Override
	public void onGetUserDetail(int code, GotyeUser user) {
		super.onGetUserDetail(code, user);
		if(chatType == GotyeChatTargetType.GotyeChatTargetTypeUser){
        	if(user.getName().equals(this.otherUser.getName())){
        		LogUtils.i("publicChatActiviy: NAME"+user.getNickname()+"-NICK"+user.getNickname()+"-ICON"+user.getIcon());
    			this.otherUser = user;
    		}
        }
	}
	
	@Override
	public void onUserDismissGroup(GotyeGroup group, GotyeUser user) {
		if (this.otherGroup != null && otherGroup.getGroupID() == this.otherGroup.getGroupID()) {
			Intent i = new Intent(this, PagesHomeActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Toast.makeText(getBaseContext(), "群主解散了该群,会话结束", Toast.LENGTH_SHORT)
					.show();
			finish();
			startActivity(i);
		}
	}
	
	@Override
	public void onAddFriend(int code, GotyeUser user) {
		super.onAddFriend(code, user);
		
	}
	

	@Override
	public void onUserKickedFromGroup(GotyeGroup group, GotyeUser kicked,
			GotyeUser actor) {
		if (this.otherGroup != null && group.getGroupID() == this.otherGroup.getGroupID()) {
			if (kicked.getName().equals(currentLoginUser.getName())) {
				Intent i = new Intent(this, PagesHomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Toast.makeText(getBaseContext(), "您被踢出了群,会话结束",
						Toast.LENGTH_SHORT).show();
				finish();
				startActivity(i);
			}
		}
	}
	
	@Override
	public void onLogin(int code, GotyeUser currentLoginUser) {
		setErrorTip(1);
	}

	@Override
	public void onLogout(int code) {
		setErrorTip(0);
	}

	@Override
	public void onReconnecting(int code, GotyeUser currentLoginUser) {
		setErrorTip(-1);
	}
	
	@ViewInject(R.id.error_tip_tv)
	private TextView errorTipTv;
	@ViewInject(R.id.error_tip_pb)
	private ProgressBar errorTipPb;
	
	private void setErrorTip(int code) {
		if (code == 1) {
			findViewById(R.id.error_tip).setVisibility(View.GONE);
		} else {
			errorTipPb.setVisibility(View.VISIBLE);
			 findViewById(R.id.error_tip).setVisibility(View.VISIBLE);
			if (code == -1) {
				 errorTipTv.setText("正在连接登陆...");
			} else {
				 errorTipTv.setText("当前未登陆或网络异常");
			}
		}
	}
	
	private void takePic() {
		Intent intent;
		intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/jpeg");
		startActivityForResult(intent, INTENT_REQUEST_PIC);
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		// intent.setType("image/*");
		// startActivityForResult(intent, REQUEST_PIC);
	}
	private void takePhoto(){
		selectPicFromCamera();
	}
	
	public void selectPicFromCamera() {
		if (!OtherUtils.isExitsSdcard()) {
			Toast.makeText(getApplicationContext(), "SD卡不存在，无法拍照",
					Toast.LENGTH_SHORT).show();
			return;
		}
		cameraFile = new File(PathUtil.getAppFIlePath()
				+ +System.currentTimeMillis() + ".jpg");
		cameraFile.getParentFile().mkdirs();
		startActivityForResult(
				new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
						MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
				INTENT_REQUEST_CAMERA);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 选取图片的返回值
		if (requestCode == INTENT_REQUEST_PIC) {
			if (data != null) {
				Uri selectedImage = data.getData();
				if (selectedImage != null) {
					String path = URIUtil.uriToPath(this, selectedImage);
					sendPicture(path);
				}
			}

		} else if (requestCode == INTENT_REQUEST_CAMERA) {
			if (resultCode == RESULT_OK) {
				if (cameraFile != null && cameraFile.exists())
					sendPicture(cameraFile.getAbsolutePath());
			}
		}
//		LogUtils.i("error:无法获取系统返回图片");
//		Toast.makeText(context, "获取图片失败", 0).show();
		// TODO 获取图片失败
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void sendPicture(String path) {
		SendImageMessageTask task = null;
		if (chatType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			task = new SendImageMessageTask(this, otherUser);
		} else if (chatType == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
			task = new SendImageMessageTask(this, otherGroup);
		}
		task.execute(path);
	}
	
	
	/**
	 * 隐藏输入法
	 */
	private void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager) getApplicationContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 显示或者隐藏输入法
		imm.hideSoftInputFromWindow(mTextMsgInputEd.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	/**
	 * 设置当前播放的语音id
	 * @param playingId
	 */
	public void setPlayingId(long playingId) {
		this.playingId = playingId;
		mAdapter.notifyDataSetChanged();
	}
	/**
	 * 得到发送的语音Id
	 * @return
	 */
	public long getPlayingId() {
		return playingId;
	}
	
	public void scrollToBottom() {
		mMsgPlv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount() - 1);
//		pullListView.setSelection(adapter.getCount() - 1);
	}
	
	@Override
	protected void onDestroy() {
		api.removeListener(this);
		if (chatType == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			api.deactiveSession(otherUser);
		}else if (chatType == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
			api.deactiveSession(otherGroup);
		}
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		api.stopTalk();
		api.stopPlay();
		super.onBackPressed();
	}

	@Override
	public void commonOnClick(View v) {
		int vId = v.getId();
		switch (vId) {
		case R.id.msg_status:
			GotyeMessage message = (GotyeMessage) v.getTag();
			sendTextMessage(message.getText());
//			Toast.makeText(context, "正在重新发送", 0).show();
			break;

		default:
			break;
		}
	}
	
	@OnClick(R.id.to_back)
	public void onBackClick(View view){
		OtherUtils.hindEditTextInput(mTextMsgInputEd);
		onReturn();
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("SplashScreen"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写)
	    MobclickAgent.onResume(this);          //统计时长
	}
	public void onPause() {
	    super.onPause();
		OtherUtils.hindEditTextInput(mTextMsgInputEd);
	    MobclickAgent.onPageEnd("SplashScreen"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息 
	    MobclickAgent.onPause(this);
	}

}
