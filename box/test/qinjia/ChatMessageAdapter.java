package com.tryhard.workpai.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeMessageStatus;
import com.gotye.api.GotyeMessageType;
import com.gotye.api.GotyeUser;
import com.gotye.api.listener.UserListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.util.LogUtils;
import com.tryhard.workpai.R;
import com.tryhard.workpai.activity.PublicChatActivity;
import com.tryhard.workpai.activity.ViewPageImageActivity;
import com.tryhard.workpai.adapter.PublicViewAdapter.ICommonOnClick;
import com.tryhard.workpai.base.BaseApplication;
import com.tryhard.workpai.customview.drawable.ImageBitmapCompression;
import com.tryhard.workpai.httpservice.Constants;
import com.tryhard.workpai.utils.GotyeVoicePlayClickPlayListener;
import com.tryhard.workpai.utils.ImageUtils;
import com.tryhard.workpai.utils.OtherUtils;
import com.tryhard.workpai.utils.TimeUtil;
import com.tryhard.workpai.utils.XUtilsBitmapHelp;

public class ChatMessageAdapter extends BaseAdapter implements UserListener {

	public static final int TYPE_RECEIVE_TEXT = 0;
	public static final int TYPE_RECEIVE_IMAGE = 2;
	public static final int TYPE_RECEIVE_VOICE = 4;
	public static final int TYPE_RECEIVE_USER_DATA = 6;
	public static final int TYPE_RECEIVE_TEXT_NOTIFY = 8;

	public static final int TYPE_SEND_TEXT = 1;
	public static final int TYPE_SEND_IMAGE = 3;
	public static final int TYPE_SEND_VOICE = 5;
	public static final int TYPE_SEND_USER_DATA = 7;
	public static final int TYPE_SEND_TEXT_NOTIFY = 9;

	/** 直接收到 */
	public static final int MESSAGE_DIRECT_RECEIVE = 1;
	/** 直接发送 */
	public static final int MESSAGE_DIRECT_SEND = 0;

	private BitmapUtils mBitmapUtil;

	private PublicChatActivity chatPage;
	private List<GotyeMessage> messageList;

	private LayoutInflater inflater;
	private String currentLoginName;
	private GotyeAPI api;
	
	private ICommonOnClick viewClick;

	public ChatMessageAdapter(PublicChatActivity activity,
			List<GotyeMessage> messageList, ICommonOnClick viewClick) {
		this.chatPage = activity;
		this.messageList = messageList;
		this.viewClick = viewClick;
		inflater = activity.getLayoutInflater();
		mBitmapUtil = XUtilsBitmapHelp.getBitmapUtilsInstance(activity);
		api = GotyeAPI.getInstance();
		currentLoginName = api.getLoginUser().getName();
	}

	public void addMsgToBottom(GotyeMessage msg) {
		int position = messageList.indexOf(msg);
		if (position < 0) {
			messageList.add(msg);
			return;
		}
		messageList.remove(position);
		messageList.add(position, msg);
		notifyDataSetChanged();
	}

	/**
	 * 得到发送状态后修改UI显示
	 * @param msg
	 */
	public void updateMessage(GotyeMessage msg) {
		int position = messageList.indexOf(msg);
		if (position < 0) {
			return;
		}
		messageList.remove(position);
		messageList.add(position, msg);
		notifyDataSetChanged();
	}

	public void addMessagesToTop(List<GotyeMessage> histMessages) {
		messageList.addAll(0, histMessages);
	}

	public void addMessageToTop(GotyeMessage msg) {
		messageList.add(0, msg);
	}

	@Override
	public int getCount() {
		return messageList.size();
	}

	@Override
	public GotyeMessage getItem(int position) {
		if (position < 0 || position >= messageList.size()) {
			return null;
		} else {
			return messageList.get(position);
		}

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		GotyeMessage message = getItem(position);
		if (message.getType() == GotyeMessageType.GotyeMessageTypeText) {
			if(message.getExtraData() != null){
				/** 包含额外数据 */
				return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? TYPE_RECEIVE_TEXT_NOTIFY
						: TYPE_SEND_TEXT_NOTIFY;
			}else{
				/** 普通文本消息 */
				return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? TYPE_RECEIVE_TEXT
						: TYPE_SEND_TEXT;
			}
		}
		if (message.getType() == GotyeMessageType.GotyeMessageTypeImage) {
			return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? TYPE_RECEIVE_IMAGE
					: TYPE_SEND_IMAGE;

		}
		if (message.getType() == GotyeMessageType.GotyeMessageTypeAudio) {
			return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? TYPE_RECEIVE_VOICE
					: TYPE_SEND_VOICE;
		}
		if (message.getType() == GotyeMessageType.GotyeMessageTypeUserData) {
			return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? TYPE_RECEIVE_USER_DATA
					: TYPE_SEND_USER_DATA;
		}
		return -1;// invalid
	}

	public int getViewTypeCount() {
		return 10;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final GotyeMessage message = getItem(position);
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = createViewByMessage(message, position);
			if (message.getType() == GotyeMessageType.GotyeMessageTypeImage) {
				/* 发送图片信息 */
				holder.iv = ((ImageView) convertView
						.findViewById(R.id.iv_sendPicture));
				holder.head_iv = (ImageView) convertView
						.findViewById(R.id.iv_userhead);
				holder.tv = (TextView) convertView
						.findViewById(R.id.percentage);
				holder.pb = (ProgressBar) convertView
						.findViewById(R.id.progressBar);
				holder.staus_iv = (ImageView) convertView
						.findViewById(R.id.msg_status);
				holder.tv_userId = (TextView) convertView
						.findViewById(R.id.tv_userid);
				holder.tv_delivered = (TextView) convertView
						.findViewById(R.id.tv_delivered);
			} else if (message.getType() == GotyeMessageType.GotyeMessageTypeAudio) {
				/* 发送语音信息 */
				holder.iv_box = ((RelativeLayout) convertView
						.findViewById(R.id.iv_voice_box));
				holder.iv = ((ImageView) convertView
						.findViewById(R.id.iv_voice));
				holder.head_iv = (ImageView) convertView
						.findViewById(R.id.iv_userhead);
				holder.tv = (TextView) convertView.findViewById(R.id.tv_length);
				holder.pb = (ProgressBar) convertView
						.findViewById(R.id.pb_sending);
				holder.staus_iv = (ImageView) convertView
						.findViewById(R.id.msg_status);
				holder.tv_userId = (TextView) convertView
						.findViewById(R.id.tv_userid);
				holder.iv_read_status = (ImageView) convertView
						.findViewById(R.id.iv_unread_voice);
				holder.tv_delivered=(TextView) convertView.findViewById(R.id.tv_delivered);
			} else if (message.getType() == GotyeMessageType.GotyeMessageTypeText) {
				/* 发送文本信息 */
				holder.pb = (ProgressBar) convertView
						.findViewById(R.id.pb_sending);
				holder.staus_iv = (ImageView) convertView
						.findViewById(R.id.msg_status);
				holder.head_iv = (ImageView) convertView
						.findViewById(R.id.iv_userhead);
				holder.tv_ack = (TextView) convertView
						.findViewById(R.id.tv_ack);
				// 这里是文字内容
				holder.tv = (TextView) convertView
						.findViewById(R.id.tv_chatcontent);
				holder.tv_userId = (TextView) convertView
						.findViewById(R.id.tv_userid);
				holder.tv_delivered = (TextView) convertView
						.findViewById(R.id.tv_delivered);
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		switch (message.getType()) {
		// 根据消息type显示item
		case GotyeMessageTypeImage: // 图片
			handleImageMessage(message, holder, position, convertView);
			break;
		case GotyeMessageTypeAudio: // 语音
			handleVoiceMessage(message, holder, position, convertView);
			break;
		case GotyeMessageTypeText: // 文字
			if(message.getExtraData() == null){
				handleTextMessage(message, holder, position);
			}else{
				/** 这里需要作为通知来处理 */
				handleTextMessage(message, holder, position);
			}
			break;
		default:
			break;
		}

		TextView timestamp = (TextView) convertView
				.findViewById(R.id.timestamp);

		if (position == 0) {
			timestamp.setText(TimeUtil.dateToMessageTime(message.getDate() * 1000));
			timestamp.setVisibility(View.VISIBLE);
		} else {
			// 两条消息时间离得如果稍长，显示时间
			if (TimeUtil.needShowTime(message.getDate(),
					messageList.get(position - 1).getDate())) {
				timestamp.setText(TimeUtil.toLocalTimeString(message.getDate() * 1000));
				timestamp.setVisibility(View.VISIBLE);
			} else {
				timestamp.setVisibility(View.GONE);
			}
		}
		holder.head_iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 跳转到查看用户信息
				/*
				 * Intent i=new Intent(chatPage, UserInfoPage.class);
				 * i.putExtra("user", (GotyeUser)message.getSender());
				 * chatPage.startActivity(i);
				 */
			}
		});
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				Toast.makeText(chatPage, "点击整个聊天信息", 0).show();
			}
		});
		setIcon(holder.head_iv, message);
		return convertView;
	}

	private void handleImageMessage(final GotyeMessage message,
			final ViewHolder holder, final int position, View convertView) {
		holder.iv.setImageResource(R.drawable.ic_launcher);
		setImageMessage(holder.iv, message, holder);

		if (getDirect(message) == MESSAGE_DIRECT_SEND) {
			switch (message.getStatus()) {
			case GotyeMessageStatusSent: // 发送成功
				holder.pb.setVisibility(View.GONE);
				holder.staus_iv.setVisibility(View.GONE);
				if (holder.tv_delivered != null) {
					holder.tv_delivered.setVisibility(View.VISIBLE);
				}
				break;
			case GotyeMessageStatusSendingFailed: // 发送失败
				holder.pb.setVisibility(View.GONE);
				holder.staus_iv.setVisibility(View.VISIBLE);
				if (holder.tv_delivered != null) {
					holder.tv_delivered.setVisibility(View.GONE);
				}
				break;
			case GotyeMessageStatusSending: // 发送中
				holder.pb.setVisibility(View.VISIBLE);
				holder.staus_iv.setVisibility(View.GONE);
				if (holder.tv_delivered != null) {
					holder.tv_delivered.setVisibility(View.GONE);
				}
				break;
			default:
				holder.pb.setVisibility(View.GONE);
				holder.staus_iv.setVisibility(View.GONE);
				if (holder.tv_delivered != null) {
					holder.tv_delivered.setVisibility(View.VISIBLE);
				}
			}
		} else {
			GotyeUser user = api.getUserDetail(message.getSender().getName(), false);
			String nickname = user.getNickname();
			if(nickname == null || "".equals(nickname)){
				if(BaseApplication.getInstance().targetUserNick != null){
					nickname = BaseApplication.getInstance().targetUserNick;
				}else{
					nickname = message.getSender().getName();
				}
			}
			holder.tv_userId.setText(nickname);
		}
	}

	/**
	 * 显示文本Text信息
	 * @param message
	 * @param holder
	 * @param position
	 */
	private void handleTextMessage(final GotyeMessage message, ViewHolder holder,
			final int position) {
		// 设置内容
		String extraData = message.getExtraData() == null ? null : new String(
				message.getExtraData());
		if (extraData != null) {
			if (message.getType() == GotyeMessageType.GotyeMessageTypeText) {
				holder.tv.setText(message.getText() + "\n额外数据：" + extraData);
			} else {
				holder.tv.setText("自定义消息：" + new String(message.getUserData())
						+ "\n额外数据：" + extraData);
			}
		} else {
			if (message.getType() == GotyeMessageType.GotyeMessageTypeText) {
				holder.tv.setText(message.getText());
			} else {
				holder.tv.setText("自定义消息：" + new String(message.getUserData()));
			}
		}

		// 设置长按事件监听
		if (getDirect(message) == MESSAGE_DIRECT_SEND) {
			switch (message.getStatus()) {
			case GotyeMessageStatusSent: // 发送成功
				holder.pb.setVisibility(View.GONE);
				holder.staus_iv.setVisibility(View.GONE);
				if (holder.tv_delivered != null) {
					holder.tv_delivered.setVisibility(View.VISIBLE);
				}
				break;
			case GotyeMessageStatusSendingFailed: // 发送失败
				holder.pb.setVisibility(View.GONE);
				holder.staus_iv.setVisibility(View.VISIBLE);
				if (holder.tv_delivered != null) {
					holder.tv_delivered.setVisibility(View.GONE);
				}
				holder.staus_iv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						/** 再次发送重新发送消息 */
						arg0.setTag(message);
						viewClick.commonOnClick(arg0);
					}
				});
				break;
			case GotyeMessageStatusSending: // 发送中
				holder.pb.setVisibility(View.VISIBLE);
				holder.staus_iv.setVisibility(View.GONE);
				if (holder.tv_delivered != null) {
					holder.tv_delivered.setVisibility(View.GONE);
				}
				break;
			case GotyeMessageStatusRead: // 已读
				holder.pb.setVisibility(View.GONE);
				holder.staus_iv.setVisibility(View.GONE);
				if (holder.tv_delivered != null) {
					holder.tv_delivered.setVisibility(View.GONE);
				}
				if (holder.tv_ack != null) {
					holder.tv_ack.setVisibility(View.VISIBLE);
				}
				break;
			default:
				holder.pb.setVisibility(View.GONE);
				holder.staus_iv.setVisibility(View.GONE);
				if (holder.tv_delivered != null) {
					holder.tv_delivered.setVisibility(View.VISIBLE);
				}
			}
		} else {
			
			
			GotyeUser user = api.getUserDetail(message.getSender().getName(), false);
			String nickname = user.getNickname();
			if(nickname == null || "".equals(nickname)){
				if(BaseApplication.getInstance().targetUserNick != null){
					nickname = BaseApplication.getInstance().targetUserNick;
				}else{
					nickname = message.getSender().getName();
				}
			}
			holder.tv_userId.setText(nickname);
		}
	}

	/**
	 * 显示语音voice消息
	 * @param message
	 * @param holder
	 * @param position
	 * @param convertView
	 */
	private void handleVoiceMessage(final GotyeMessage message,
			final ViewHolder holder, final int position, View convertView) {
		holder.tv.setText(TimeUtil.getVoiceTime(message.getMedia()
				.getDuration()));
		int voiceTime = TimeUtil.getVoiceTimeInt(message.getMedia().getDuration());
//		Toast.makeText(chatPage, "语音栏宽度"+holder.iv.getWidth()+"--"+holder.iv.getMeasuredWidth(), 0).show();
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(OtherUtils.dp2px(chatPage, 60+(5*voiceTime)), OtherUtils.dp2px(chatPage, 48));
		android.view.ViewGroup.LayoutParams params = holder.iv_box.getLayoutParams();
		params.width = OtherUtils.dp2px(chatPage, 60+(5*voiceTime));
		params.height = OtherUtils.dp2px(chatPage, 48);
//		params.addRule(RelativeLayout.LEFT_OF, R.id.iv_userhead);
		holder.iv_box.setLayoutParams(params);
//		holder.iv.setBackgroundDrawable(chatPage.getResources().getDrawable(R.drawable.chat_sent_bg));
		holder.iv_box.setOnClickListener(new GotyeVoicePlayClickPlayListener(
				message, holder.iv, this, chatPage));
		boolean isPlaying = isPlaying(message);
		if (isPlaying) {
			AnimationDrawable voiceAnimation;
			if (getDirect(message) == MESSAGE_DIRECT_RECEIVE) {
				holder.iv.setImageResource(R.anim.voice_from_icon);
			} else {
				holder.iv.setImageResource(R.anim.voice_to_icon);
			}
			message.setStatus(GotyeMessageStatus.GotyeMessageStatusRead);
			voiceAnimation = (AnimationDrawable) holder.iv.getDrawable();
			voiceAnimation.start();
		} else {
			if (getDirect(message) == MESSAGE_DIRECT_RECEIVE) {
				holder.iv.setImageResource(R.drawable.chatfrom_voice_playing);
			} else {
				holder.iv.setImageResource(R.drawable.chatto_voice_playing);
			}
		}

		if (getDirect(message) == MESSAGE_DIRECT_RECEIVE) {
			if (message.getStatus() == GotyeMessageStatus.GotyeMessageStatusUnread) {// if
				// holder.iv_read_status.setVisibility(View.INVISIBLE);
				holder.iv_read_status.setVisibility(View.VISIBLE);
			} else {
				holder.iv_read_status.setVisibility(View.INVISIBLE);
			}

			GotyeUser user = api.getUserDetail(message.getSender().getName(), false);
			String nickname = user.getNickname();
			if(nickname == null || "".equals(nickname)){
				if(BaseApplication.getInstance().targetUserNick != null){
					nickname = BaseApplication.getInstance().targetUserNick;
				}else{
					nickname = message.getSender().getName();
				}
			}
			holder.tv_userId.setText(nickname);
			return;
		}

		// until here, deal with send voice msg
		switch (message.getStatus()) {
		case GotyeMessageStatusSent:
			holder.pb.setVisibility(View.GONE);
			holder.staus_iv.setVisibility(View.GONE);
			if (holder.tv_delivered != null) {
				holder.tv_delivered.setVisibility(View.VISIBLE);
			}
			break;
		case GotyeMessageStatusSendingFailed:
			holder.pb.setVisibility(View.GONE);
			holder.staus_iv.setVisibility(View.VISIBLE);
			if (holder.tv_delivered != null) {
				holder.tv_delivered.setVisibility(View.GONE);
			}
			holder.staus_iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
//					Toast.makeText(chatPage, "是否重新发送(未实现)", 0).show();
				}
			});
			break;
		case GotyeMessageStatusSending:
			holder.pb.setVisibility(View.VISIBLE);
			holder.staus_iv.setVisibility(View.GONE);
			if (holder.tv_delivered != null) {
				holder.tv_delivered.setVisibility(View.GONE);
			}
			break;
		default:
			holder.pb.setVisibility(View.GONE);
			holder.staus_iv.setVisibility(View.GONE);
			if (holder.tv_delivered != null) {
				holder.tv_delivered.setVisibility(View.VISIBLE);
			}
		}
		
		switch (message.getMedia().getStatus()) {
		case MEDIA_STATUS_DOWNLOADING:
			holder.pb.setVisibility(View.VISIBLE);
			break;
		default:
			holder.pb.setVisibility(View.GONE);
			break;
		}
	}
	
	private boolean isPlaying(GotyeMessage msg) {
		long id = msg.getDbId();
		long pid = chatPage.getPlayingId();
		if (id == pid) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 根据消息类型记载对应布局
	 * @param message
	 * @param position
	 * @return
	 */
	private View createViewByMessage(GotyeMessage message, int position) {
		switch (message.getType()) {
		case GotyeMessageTypeImage:
			return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? inflater
					.inflate(R.layout.layout_row_received_picture, null)
					: inflater.inflate(R.layout.layout_row_sent_picture, null);

		case GotyeMessageTypeAudio:
			return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? inflater
					.inflate(R.layout.layout_row_received_voice, null)
					: inflater.inflate(R.layout.layout_row_sent_voice, null);
		case GotyeMessageTypeUserData:
			return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? inflater
					.inflate(R.layout.layout_row_received_message, null)
					: inflater.inflate(R.layout.layout_row_sent_message, null);
		case GotyeMessageTypeText:
			if(message.getExtraData() == null){
				return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? inflater
						.inflate(R.layout.layout_row_received_message, null)
						: inflater.inflate(R.layout.layout_row_sent_message, null);
			}else{
				return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? inflater
						.inflate(R.layout.layout_row_received_notify, null)
						: inflater.inflate(R.layout.layout_row_sent_notify, null);
			}
		default:
			return getDirect(message) == MESSAGE_DIRECT_RECEIVE ? inflater
					.inflate(R.layout.layout_row_received_message, null)
					: inflater.inflate(R.layout.layout_row_sent_message, null);
		}
	}

	/**
	 * 显示头像
	 * @param iconView
	 * @param name
	 */
	private void setIcon(ImageView iconView, GotyeMessage message) {
		if (getDirect(message) == MESSAGE_DIRECT_SEND) {
			mBitmapUtil.display(iconView, BaseApplication.getInstance().getLoginUserLogo(), new BitmapLoadCallBack<ImageView>() {
				@Override
				public void onLoadCompleted(ImageView arg0, String arg1,
						Bitmap arg2, BitmapDisplayConfig arg3,
						BitmapLoadFrom arg4) {
					arg0.setImageBitmap(ImageUtils.toRoundCorner(arg2, 180));
				}
				@Override
				public void onLoadFailed(ImageView arg0, String arg1,
						Drawable arg2) {
					arg0.setImageResource(R.drawable.y_me_default_head);
				}
			});
		}else{
			GotyeUser user = api.getUserDetail(message.getSender().getName(), false);
			if (BaseApplication.getInstance().targetUserIcon != null) {
				mBitmapUtil.display(iconView, BaseApplication.getInstance().targetUserIcon, new BitmapLoadCallBack<ImageView>() {
					@Override
					public void onLoadCompleted(ImageView arg0, String arg1,
							Bitmap arg2, BitmapDisplayConfig arg3,
							BitmapLoadFrom arg4) {
						arg0.setImageBitmap(ImageUtils.toRoundCorner(arg2, 180));
					}
					@Override
					public void onLoadFailed(ImageView arg0, String arg1,
							Drawable arg2) {
						arg0.setImageResource(R.drawable.y_me_default_head);
					}
				});
			} else {
				iconView.setImageResource(R.drawable.y_me_default_head);
			}
		}
	}

	
	private void setImageMessage(ImageView msgImageView,
			final GotyeMessage msg, final ViewHolder holder) {
		if (msg.getMedia().getPath() != null) {
			XUtilsBitmapHelp.getBitmapUtilsInstance(chatPage
				, Constants.TAG.TAG_NONE, Constants.TAG.TAG_NONE).display(msgImageView, msg.getMedia().getPath(),
					new BitmapLoadCallBack<ImageView>() {//getPath_ex 为大图(未压缩)
						@Override
						public void onLoadCompleted(ImageView container,
								String uri, Bitmap bitmap,
								BitmapDisplayConfig config, BitmapLoadFrom from) {
							container.setImageBitmap(ImageBitmapCompression.getChangeSizeBitmap(bitmap, 2, 2));
							holder.pb.setVisibility(View.GONE);
						}

						@Override
						public void onLoadFailed(ImageView container,
								String uri, Drawable drawable) {

						}
					});
		}
		msgImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 显示大图片
				ArrayList<String> arrayList = new ArrayList<String>();
				LogUtils.i("大图片路径: "+msg.getMedia().getPathEx());
				arrayList.add(msg.getMedia().getPathEx());
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("arrayList", arrayList);
				bundle.putInt("position", 0);
				Intent intent = new Intent(chatPage, ViewPageImageActivity.class);
				intent.putExtra("bundle", bundle);
				chatPage.startActivity(intent);

				
				
				/*
				 * Intent intent = new Intent(chatPage, ShowBigImage.class);
				 * String path = msg.getMedia().getPath_ex(); if
				 * (!TextUtils.isEmpty(path) && new File(path).exists()) { Uri
				 * uri = Uri.fromFile(new File(path)); intent.putExtra("uri",
				 * uri); intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 * chatPage.startActivity(intent); } else {
				 * ToastUtil.show(chatPage, "正在下载...");
				 * api.downloadMessage(msg); return; }
				 */

			}
		});
		// holder.pb.setVisibility(View.VISIBLE);
	}

	/**
	 * 判断是发送还是接受
	 * 
	 * @param message
	 * @return
	 */
	private int getDirect(GotyeMessage message) {
		if (message.getSender().getName().equals(currentLoginName)) {
			return MESSAGE_DIRECT_SEND;
		} else {
			return MESSAGE_DIRECT_RECEIVE;
		}
	}

	public static class ViewHolder {
		/** 图片-发送的图片|语音-语音图标 */
		RelativeLayout iv_box;
		/** 图片-发送的图片|语音-语音图标 */
		ImageView iv;
		/** 文本-显示文本消息内容|图片-显示发送百分比 */
		TextView tv;
		/** 发送中状态菊花 */
		ProgressBar pb;
		/** 发送失败显示提示 */
		ImageView staus_iv;
		/** 用户头像 */
		ImageView head_iv;
		/** 显示用户名字 */
		TextView tv_userId;
		/** 提示此语音信息未读状态 */
		ImageView iv_read_status;
		/** 显示已读 */
		TextView tv_ack;
		/** 显示已送达 */
		TextView tv_delivered;
	}

	public void refreshData(List<GotyeMessage> list) {
		this.messageList = list;
		notifyDataSetChanged();
	}

	@Override
	public void onGetUserDetail(int arg0, GotyeUser arg1) {
		
	}

	@Override
	public void onModifyUserInfo(int code, GotyeUser user) {
		
	}

	@Override
	public void onSearchUserList(int code, List<GotyeUser> mList, List<GotyeUser> mmList, int pagerIndex) {
		
	}

	@Override
	public void onAddFriend(int code, GotyeUser user) {
		
	}

	@Override
	public void onGetFriendList(int code, List<GotyeUser> mList) {
		
	}

	@Override
	public void onAddBlocked(int code, GotyeUser user) {
		
	}

	@Override
	public void onRemoveFriend(int code, GotyeUser user) {
		
	}

	@Override
	public void onRemoveBlocked(int code, GotyeUser user) {
		
	}

	@Override
	public void onGetBlockedList(int code, List<GotyeUser> mList) {
		
	}

	@Override
	public void onGetProfile(int code, GotyeUser user) {
		
	}

	@Override
	public void onGetCustomerService(int arg0, GotyeUser arg1, int arg2,
			String arg3) {
		// TODO Auto-generated method stub
		
	}


	
	
}
