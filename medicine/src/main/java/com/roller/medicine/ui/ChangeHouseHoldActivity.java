package com.roller.medicine.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.info.MyAccountListItemInfo;

import java.util.LinkedList;

import butterknife.InjectView;

public class ChangeHouseHoldActivity extends BaseToolbarActivity {

	@InjectView(R.id.text_title)
	 TextView text_title;
	@InjectView(R.id.listview)
	 ListView listview;
	@InjectView(R.id.image_head)
	 ImageView image_head;
	@InjectView(R.id.text_name)
	 TextView text_name;
	
	private PublicViewAdapter<MyAccountListItemInfo> adapter;
	private LinkedList<MyAccountListItemInfo> mDatas = new LinkedList<MyAccountListItemInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_house_hold);
	}

	protected void initView(){
		text_title.setText("更改户主");

	}

	/**
	 * 获取成员列表
	 */
	private void getFamilyListByMap(){
	/*	try {
			DataService.getInstance().getFamilyListByMap(this, BaseApplication.TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 更改户主
	 * @param item
	 *//*
	private void updateFamilyGroup(MyAccountListItemInfo item){
		try {
			DataService.getInstance().updateFamilyGroup(this, BaseApplication.TOKEN, item.getGroupId(), item.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onSuccess(String url, String result, int resultCode, Object tag) {
		super.onSuccess(url, result, resultCode, tag);
		if(resultCode != 200){
			PublicOnReturnInfo mInfo = JSON.parseObject(result, PublicOnReturnInfo.class);
			disPlay(mInfo.message);
			return;
		}
		
		if(Constants.URL.GETFAMILYLISTBYMAP.equals(url)){
			MyAccountListInfo mAccountListInfo = JSON.parseObject(result, MyAccountListInfo.class);
			if(mAccountListInfo.getList() != null){
				text_name.setText(mAccountListInfo.getList().get(0).getNickname());
				BitmapUtils mBitmapUtils = XUtilsBitmapHelp.getBitmapUtilsInstance(
						this,R.drawable.public_default_head, R.drawable.public_default_head);
				
				mBitmapUtils.display(image_head, mAccountListInfo.getList().get(0).getHeadImage(),
						OtherUtils.roundBitmapLoadCallBack);
				
				mDatas.clear();
				mDatas.addAll(mAccountListInfo.getList());
				mDatas.removeFirst();
				adapter.notifyDataSetChanged();
			}
		}else if(Constants.URL.UPDATEFAMILYGROUP.equals(url)){
			onReturn();
		}
	}
	
	@Override
	public void commonOnClick(View v) {
		switch (v.getId()) {
		case R.id.text_item_house_hold:
			MyAccountListItemInfo item = (MyAccountListItemInfo) v.getTag();
			updateFamilyGroup(item);
			break;
		}
	}

	@Override
	public void commonGetView(PublicViewHolder helper, MyAccountListItemInfo item,
			OnClickListener onClick, int position, Object tag) {
		TextView text_item_name = helper.getView(R.id.text_item_name);
		TextView text_item_house_hold = helper.getView(R.id.text_item_house_hold);
		
		text_item_name.setText(item.getAppellation());
		
		text_item_house_hold.setTag(item);
		
		text_item_house_hold.setOnClickListener(onClick);
	}*/

}
