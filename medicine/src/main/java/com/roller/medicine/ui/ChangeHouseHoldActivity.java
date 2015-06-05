package com.roller.medicine.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.adapter.PublicViewAdapter.ICommonGetView;
import com.roller.medicine.adapter.PublicViewAdapter.ICommonOnClick;
import com.roller.medicine.adapter.PublicViewHolder;
import com.roller.medicine.base.BaseActivity;
import com.roller.medicine.base.BaseApplication;
import com.roller.medicine.httpservice.Constants;
import com.roller.medicine.httpservice.DataService;
import com.roller.medicine.info.MyAccountListInfo;
import com.roller.medicine.info.MyAccountListItemInfo;
import com.roller.medicine.info.PublicOnReturnInfo;
import com.roller.medicine.utils.OtherUtils;
import com.roller.medicine.utils.XUtilsBitmapHelp;

import java.util.LinkedList;

public class ChangeHouseHoldActivity extends BaseActivity implements
ICommonGetView<MyAccountListItemInfo>, ICommonOnClick {

	@ViewInject(R.id.text_title)
	private TextView text_title;
	@ViewInject(R.id.listview)
	private ListView listview;
	@ViewInject(R.id.image_head)
	private ImageView image_head;
	@ViewInject(R.id.text_name)
	private TextView text_name;
	
	private PublicViewAdapter<MyAccountListItemInfo> adapter;
	private LinkedList<MyAccountListItemInfo> mDatas = new LinkedList<MyAccountListItemInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_house_hold);
		ViewUtils.inject(this);
		initView();
	}

	private void initView(){
		text_title.setText("更改户主");
		adapter = new PublicViewAdapter<MyAccountListItemInfo>(
				this, mDatas, R.layout.listview_change_house_hold, this, this, Constants.TAG.TAG_NONE);
		listview.setAdapter(adapter);
		
		getFamilyListByMap();
	}

	/**
	 * 获取成员列表
	 */
	private void getFamilyListByMap(){
		try {
			DataService.getInstance().getFamilyListByMap(this, BaseApplication.TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更改户主
	 * @param item
	 */
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
	}

}
