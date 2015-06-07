package com.roller.medicine.ui;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.info.MyAccountListItemInfo;

import java.util.LinkedList;

import butterknife.InjectView;

public class RemoveAccountActivity extends BaseToolbarActivity{

	@InjectView(R.id.text_title)
	 TextView text_title;
	@InjectView(R.id.listview)
	 ListView listview;
	
	private PublicViewAdapter<MyAccountListItemInfo> adapter;
	private LinkedList<MyAccountListItemInfo> mDatas = new LinkedList<MyAccountListItemInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remove_account);
	}

	/*private void initView(){
		text_title.setText("移除账号");
		adapter = new PublicViewAdapter<MyAccountListItemInfo>(
				this, mDatas, R.layout.listview_remove_account, this, this);
		
		listview.setAdapter(adapter);
		getFamilyListByMap();
	}

	*//**
	 * 获取成员列表
	 *//*
	private void getFamilyListByMap(){
		try {
			DataService.getInstance().getFamilyListByMap(this, BaseApplication.TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 删除家庭成员
	 * @param item
	 *//*
	private void deleteFamilyGroup(MyAccountListItemInfo item){
		try {
			DataService.getInstance().deleteFamilyGroup(this, BaseApplication.TOKEN, item.getGroupId(), item.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void commonOnClick(View v) {
		switch (v.getId()) {
		case R.id.image_item_delete:
			MyAccountListItemInfo item = (MyAccountListItemInfo) v.getTag();
			int position = Integer.valueOf(v.getTag(R.id.position).toString());
			deleteFamilyGroup(item);
			mDatas.remove(position);
			adapter.notifyDataSetChanged();
			break;
		}
	}

	@Override
	public void commonGetView(PublicViewHolder helper, MyAccountListItemInfo item,
			OnClickListener onClick, int position, Object tag) {
		TextView text_item_name = helper.getView(R.id.text_item_name);
		ImageView image_item_delete = helper.getView(R.id.image_item_delete);
		
		text_item_name.setText(item.getAppellation());
		
		image_item_delete.setTag(item);
		image_item_delete.setTag(R.id.position, position);
		
		image_item_delete.setOnClickListener(onClick);
	}
	
	@Override
	public void onSuccess(String url, String result, int resultCode, Object tag) {
		super.onSuccess(url, result, resultCode, tag);
		if(resultCode != 200){
			return;
		}
		
		if(Constants.URL.GETFAMILYLISTBYMAP.equals(url)){
			MyAccountListInfo mAccountListInfo = JSON.parseObject(result, MyAccountListInfo.class);
			if(mAccountListInfo.getList() != null){
				mDatas.clear();
				mDatas.addAll(mAccountListInfo.getList());
				adapter.notifyDataSetChanged();
			}
		}
		
	}*/
}
