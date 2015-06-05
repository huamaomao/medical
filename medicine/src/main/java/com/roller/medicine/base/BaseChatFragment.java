package com.roller.medicine.base;

import java.util.List;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeMedia;
import com.gotye.api.GotyeUser;
import com.gotye.api.listener.DownloadListener;
import com.gotye.api.listener.LoginListener;
import com.gotye.api.listener.UserListener;

public abstract class BaseChatFragment extends BaseFragment implements UserListener,
		DownloadListener,LoginListener {

	public GotyeAPI api=GotyeAPI.getInstance();

	@Override
	public void onLogin(int arg0, GotyeUser arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLogout(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReconnecting(int arg0, GotyeUser arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDownloadMedia(int arg0, GotyeMedia arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddBlocked(int arg0, GotyeUser arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddFriend(int arg0, GotyeUser arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetBlockedList(int arg0, List<GotyeUser> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetCustomerService(int arg0, GotyeUser arg1, int arg2,
			String arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetFriendList(int arg0, List<GotyeUser> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetProfile(int arg0, GotyeUser arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetUserDetail(int arg0, GotyeUser arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onModifyUserInfo(int arg0, GotyeUser arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveBlocked(int arg0, GotyeUser arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveFriend(int arg0, GotyeUser arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSearchUserList(int arg0, List<GotyeUser> arg1,
			List<GotyeUser> arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected abstract void lazyLoad(boolean willRefresh) ;

	@Override
	protected abstract void fragmentHide();



}
