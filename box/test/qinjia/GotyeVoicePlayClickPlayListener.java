/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tryhard.workpai.utils;

import java.io.File;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeMessage;
import com.gotye.api.listener.PlayListener;
import com.tryhard.workpai.R;
import com.tryhard.workpai.activity.PublicChatActivity;
import com.tryhard.workpai.adapter.ChatMessageAdapter;

public class GotyeVoicePlayClickPlayListener implements View.OnClickListener,
		PlayListener {

	GotyeMessage message;
	ImageView voiceIconView;

	private AnimationDrawable voiceAnimation = null;
	ImageView iv_read_status;
	Activity activity;
	private BaseAdapter adapter;

	public static boolean isPlaying = false;
	public static GotyeVoicePlayClickPlayListener currentPlayListener = null;

	/**
	 * 
	 * @param message
	 * @param v
	 * @param iv_read_status
	 * @param context
	 * @param activity
	 * @param user
	 * @param chatType
	 */
	public GotyeVoicePlayClickPlayListener(GotyeMessage message, ImageView v, 
			BaseAdapter adapter, Activity activity) {
		this.message = message;
		this.adapter = adapter;
		voiceIconView = v;
		this.activity = activity;
	}

	public void stopPlayVoice(boolean byclick) {
		voiceAnimation.stop();
		if (getDirect(message) == ChatMessageAdapter.MESSAGE_DIRECT_RECEIVE) {
			voiceIconView.setImageResource(R.drawable.chatfrom_voice_playing);
		} else if (getDirect(message) == ChatMessageAdapter.MESSAGE_DIRECT_SEND) {
			voiceIconView.setImageResource(R.drawable.chatto_voice_playing);
		}
		
		isPlaying = false;
		((PublicChatActivity) activity).setPlayingId(-1);
		adapter.notifyDataSetChanged();
		if(byclick)
			GotyeAPI.getInstance().stopPlay();
	}

	private int getDirect(GotyeMessage message) {
		if (message.getSender().getName().equals(GotyeAPI.getInstance()
				.getLoginUser().getName())) {
			return ChatMessageAdapter.MESSAGE_DIRECT_SEND;
		} else {
			return ChatMessageAdapter.MESSAGE_DIRECT_RECEIVE;
		}
	}

	public void playVoice(String filePath) {
		if (!(new File(filePath).exists())) {
			return;
		}
		
		if(GotyeAPI.getInstance().playMessage(message)!=0){
			return;
		}
		
		((PublicChatActivity) activity).setPlayingId(message.getDbId());
		isPlaying = true;
		currentPlayListener = this;
		GotyeAPI.getInstance().addListener(this);
		//;
		showAnimation();
	}

	// show the voice playing animation
	private void showAnimation() {
		// play voice, and start animation
		if (getDirect(message) == ChatMessageAdapter.MESSAGE_DIRECT_RECEIVE) {
			voiceIconView.setImageResource(R.anim.voice_from_icon);
		} else {
			voiceIconView.setImageResource(R.anim.voice_to_icon);
		}
		voiceAnimation = (AnimationDrawable) voiceIconView.getDrawable();
		voiceAnimation.start();
	}

	@Override
	public void onClick(View v) {
		if (isPlaying) {
			if (((PublicChatActivity) activity).getPlayingId() == message.getDbId()) {
				currentPlayListener.stopPlayVoice(true);
				return;
			}
			currentPlayListener.stopPlayVoice(true);
		}
		String path = message.getMedia().getPath();
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			playVoice(path);
		} else {
			Toast.makeText(activity, "正在下载语音，稍后点击", Toast.LENGTH_SHORT).show();
			GotyeAPI.getInstance().downloadMediaInMessage(message);
		}
	}

	@Override
	public void onPlayStart(int code, GotyeMessage message) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPlaying(int code, int position) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void onPlayStop(int code) {
       stopPlayVoice(false);
       GotyeAPI.getInstance().removeListener(this);
	}

	@Override
	public void onPlayStartReal(int code, long roomId, String who) {
		// TODO Auto-generated method stub
		
	}
}