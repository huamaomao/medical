package com.gotye.api;

import java.util.ArrayList;
import java.util.List;

import com.gotye.api.listener.ChatListener;
import com.gotye.api.listener.DownloadListener;
import com.gotye.api.listener.GotyeListener;
import com.gotye.api.listener.GroupListener;
import com.gotye.api.listener.LoginListener;
import com.gotye.api.listener.NotifyListener;
import com.gotye.api.listener.PlayListener;
import com.gotye.api.listener.RoomListener;
import com.gotye.api.listener.UserListener;

/**
 * 事件具体分发到各个对应监听
 * 
 * @author gotye
 *
 */
public class GotyeListenerImp implements GotyeDelegate {

	GotyeListenerImp() {
	}

	@Override
	public void onLogin(int code, GotyeUser user) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null) {
				if (listener instanceof LoginListener) {
					((LoginListener) listener).onLogin(code, user);
				}
			}
		}
	}

	@Override
	public void onLogout(int code) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof LoginListener) {
				((LoginListener) listener).onLogout(code);
			}
		}
	}

	@Override
	public void onGetUserInfo(int code, GotyeUser user) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onGetUserDetail(code, user);
			}
		}
	}

	@Override
	public void onModifyUserInfo(int code, GotyeUser user) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onModifyUserInfo(code, user);
			}
		}
	}

	@Override
	public void onGetProfile(int code, GotyeUser user) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onGetProfile(code, user);
			}
		}
	}

	@Override
	public void onGetFriendList(int code, List<GotyeUser> mList) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onGetFriendList(code, mList);
			}
		}
	}

	@Override
	public void onGetBlockedList(int code, List<GotyeUser> mList) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onGetBlockedList(code, mList);
			}
		}
	}

	@Override
	public void onAddFriend(int code, GotyeUser user) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onAddFriend(code, user);
			}
		}
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof NotifyListener) {
				((NotifyListener) listener).onFriendChanged(true, user);
			}
		}
	}

	@Override
	public void onAddBlocked(int code, GotyeUser user) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onAddBlocked(code, user);
			}
		}
	}

	@Override
	public void onRemoveFriend(int code, GotyeUser user) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onRemoveFriend(code, user);
			}
		}
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof NotifyListener) {
				((NotifyListener) listener).onFriendChanged(false, user);
			}
		}
	}

	@Override
	public void onRemoveBlocked(int code, GotyeUser user) {

		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onRemoveBlocked(code, user);
			}
		}
	}

	@Override
	public void onGetRoomList(int code, List<GotyeRoom> gotyerooms) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof RoomListener) {
				((RoomListener) listener).onGetRoomList(code, gotyerooms);
			}
		}
	}

	@Override
	public void onEnterRoom(int code, long lastMsgID, GotyeRoom room) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof RoomListener) {
				((RoomListener) listener).onEnterRoom(code, lastMsgID, room);
			}
		}
	}

	@Override
	public void onGetRoomUserList(int code, GotyeRoom room,
			List<GotyeUser> totalMembers, List<GotyeUser> currentPageMembers,
			int pageIndex) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof RoomListener) {
				((RoomListener) listener).onGetRoomMemberList(code, room,
						pageIndex, totalMembers, currentPageMembers);
			}
		}
	}

	@Override
	public void onLeaveRoom(int code, GotyeRoom room) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof RoomListener) {
				((RoomListener) listener).onLeaveRoom(code, room);
			}
		}
	}

	@Override
	public void onSearchGroupList(int code, List<GotyeGroup> mList,
			List<GotyeGroup> curList, int pageIndex) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onSearchGroupList(code, mList,
						curList, pageIndex);
			}
		}
	}

	@Override
	public void onCreateGroup(int code, GotyeGroup group) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onCreateGroup(code, group);
			}
		}
	}

	@Override
	public void onJoinGroup(int code, GotyeGroup group) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onJoinGroup(code, group);
			}
		}
	}

	@Override
	public void onLeaveGroup(int code, GotyeGroup group) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onLeaveGroup(code, group);
			}
		}
	}

	@Override
	public void onDismissGroup(int code, GotyeGroup group) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onDismissGroup(code, group);
			}
		}
	}

	@Override
	public void onKickoutGroupMember(int code, GotyeGroup group,GotyeUser kickedMember) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onKickoutGroupMember(code, group,kickedMember);
			}
		}
	}

	@Override
	public void onGetGroupList(int code, List<GotyeGroup> grouplist) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onGetGroupList(code, grouplist);
			}
		}
	}

	@Override
	public void onChangeGroupOwner(int code, GotyeGroup group,GotyeUser newOwner) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onChangeGroupOwner(code, group,newOwner);
			}
		}
	}

	@Override
	public void onGetGroupDetailList() {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
			}
		}
	}

	@Override
	public void onGetGroupInfo(int code, GotyeGroup group) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onGetGroupDetail(code, group);
			}
		}
	}

	@Override
	public void onGetGroupUserList(int code, List<GotyeUser> allList,
			List<GotyeUser> curList, GotyeGroup group, int pagerIndex) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onGetGroupMemberList(code,group, pagerIndex, allList,
						curList);
			}
		}
	}

	@Override
	public void onReceiveNotify(int code, GotyeNotify notify) {

		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		if (notify.getType() == GotyeNotifyType.GroupInvite) {
			for (GotyeListener listener : listeners) {
				if (listener != null && listener instanceof GroupListener) {
					((GroupListener) listener).onReceiveGroupInvite(code,
							(GotyeGroup) notify.getFrom(),
							(GotyeUser) notify.getSender(), notify.getText());
				}
			}
		} else if (notify.getType() == GotyeNotifyType.JoinGroupReply) {
			if (notify.isAgree()) {
				GotyeAPI.getInstance().joinGroup((GotyeGroup) notify.getFrom());
			}
			for (GotyeListener listener : listeners) {
				if (listener != null && listener instanceof GroupListener) {
					((GroupListener) listener).onReceiveReplayJoinGroup(code,
							(GotyeGroup) notify.getFrom(),
							(GotyeUser) notify.getSender(), notify.getText(),
							notify.isAgree());
				}
			}

		} else if (notify.getType() == GotyeNotifyType.JoinGroupRequest) {
			for (GotyeListener listener : listeners) {
				if (listener != null && listener instanceof GroupListener) {
					((GroupListener) listener).onReceiveRequestJoinGroup(code,
							(GotyeGroup) notify.getFrom(),
							(GotyeUser) notify.getSender(), notify.getText());
				}
			}
		}

		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof NotifyListener) {
				((NotifyListener) listener).onReceiveNotify(notify);
				;
			}
		}
	}

	@Override
	public void onSendMessage(int code, GotyeMessage message) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof ChatListener) {
				((ChatListener) listener).onSendMessage(code, message);
			}
		}
	}

	@Override
	public void onReceiveMessage(int code, GotyeMessage message) {

		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof ChatListener) {
				((ChatListener) listener).onReceiveMessage(message);
			}
		}
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof NotifyListener) {
				((NotifyListener) listener).onReceivePushMessage(message);
			}
		}
	}

	int bPlaying = 0;

	@Override
	public void onDownloadMediaInMessage(int code, GotyeMessage message) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();

		// if(message.getType()==GotyeMessageType.GotyeMessageTypeAudio){
		// if(0==bPlaying){
		// GotyeAPI.getInstance().playMessage(message);
		// }
		// }

		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof ChatListener) {
				((ChatListener) listener).onDownloadMediaInMessage(code, message);
			}
		}
	}

	@Override
	public void onReport(int code, GotyeMessage message) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof ChatListener) {
				((ChatListener) listener).onReport(code, message);
			}
		}
	}

	@Override
	public void onGetMessageList(int code, List<GotyeMessage> list) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof ChatListener) {
				((ChatListener) listener).onGetMessageList(code, list);
			}
		}
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof NotifyListener) {
				((NotifyListener) listener).onNotifyStateChanged();
			}
		}
	}

	// @Override
	// public void onReleaseMessage(int code) {
	// ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
	// .getListeners();
	// for (GotyeListener listener : listeners) {
	// if (listener != null && listener instanceof ChatListener) {
	// ((ChatListener) listener).onReleaseMessage(code);
	// }
	// }
	// }

	@Override
	public void onStartTalk(int code, boolean isRealTime, int targetType,
			GotyeChatTarget target) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof ChatListener) {
				((ChatListener) listener).onStartTalk(code, isRealTime,
						targetType, target);
			}
		}
	}

	@Override
	public void onStopTalk(int code, GotyeMessage message, boolean isVoiceReal) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof ChatListener) {
				((ChatListener) listener)
						.onStopTalk(code, message, isVoiceReal);
			}
		}
	}

	@Override
	public void onDownloadMedia(int code, GotyeMedia media) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof DownloadListener) {
				((DownloadListener) listener).onDownloadMedia(code, media);
			}
		}
	}

	@Override
	public void onPlayStart(int code, GotyeMessage message) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof PlayListener) {
				((PlayListener) listener).onPlayStart(code, message);
			}
		}
	}

	@Override
	public void onPlayStartReal(int code, long roomId, String who) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof PlayListener) {
				((PlayListener) listener).onPlayStartReal(code, roomId, who);
			}
		}
	}

	@Override
	public void onPlaying(int code, int position) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof PlayListener) {
				((PlayListener) listener).onPlaying(code, position);
			}
		}
	}

	@Override
	public void onPlayStop(int code) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof PlayListener) {
				((PlayListener) listener).onPlayStop(code);
			}
		}
	}

	@Override
	public void onSearchUserList(int code, List<GotyeUser> mList,List<GotyeUser> curPageList, int pagerIndex) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onSearchUserList(code, mList,curPageList,
						pagerIndex);
			}
		}
	}

	@Override
	public void onModifyGroupInfo(int code, GotyeGroup gotyeGroup) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onModifyGroupInfo(code, gotyeGroup);
			}
		}
	}

	@Override
	public void onUserJoinGroup(GotyeGroup group, GotyeUser user) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onUserJoinGroup(group, user);
			}
		}
	}

	@Override
	public void onUserLeaveGroup(GotyeGroup group, GotyeUser user) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onUserLeaveGroup(group, user);
			}
		}

	}

	@Override
	public void onUserDismissGroup(GotyeGroup group, GotyeUser user) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onUserDismissGroup(group, user);
			}
		}
	}

	@Override
	public void onUserKickdFromGroup(GotyeGroup group, GotyeUser kicked,
			GotyeUser actor) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onUserKickedFromGroup(group, kicked,
						actor);
			}
		}
	}

	@Override
	public void onDecodeMessage(int code, GotyeMessage message) {
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof ChatListener) {
				((ChatListener) listener).onDecodeMessage(code, message);
			}
		}
	}

	@Override
	public void onSendNotify(int code, GotyeNotify notify) {
		// TODO Auto-generated method stub
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof GroupListener) {
				((GroupListener) listener).onSendNotify(code, notify);
			}
		}
	}

	@Override
	public void onReceiveGroupInvite(int code, GotyeGroup mGroup,
			String message, GotyeUser sender) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReconnecting(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof LoginListener) {
				((LoginListener) listener).onReconnecting(code, user);
			}
		}
	}

	@Override
	public void onOutputAudioData(byte[] datas) {
		// TODO Auto-generated method stub
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof ChatListener) {
				((ChatListener) listener).onOutputAudioData(datas);
			}
		}
	}

	@Override
	public void onGetCustomerService(int code, GotyeUser cs, int onlineStatus,
			String welcomeMsg) {
		// TODO Auto-generated method stub
		ArrayList<GotyeListener> listeners = GotyeAPI.getInstance()
				.getListeners();
		for (GotyeListener listener : listeners) {
			if (listener != null && listener instanceof UserListener) {
				((UserListener) listener).onGetCustomerService(code, cs,
						onlineStatus, welcomeMsg);
			}
		}
	}

}
