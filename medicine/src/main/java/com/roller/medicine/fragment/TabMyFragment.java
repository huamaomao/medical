package com.roller.medicine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.info.MyItemInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.ui.FamilyAddActivity;
import com.roller.medicine.ui.FamilyRemoveActivity;
import com.roller.medicine.ui.FamilyUpdateActivity;
import com.roller.medicine.ui.UserInfoActivity;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import butterknife.InjectView;
import butterknife.OnClick;

public class TabMyFragment extends BaseToolbarFragment{

	@InjectView(R.id.iv_photo)
	ImageView iv_photo;
	@InjectView(R.id.tv_name)
	TextView tv_name;
	@InjectView(R.id.tv_jianjie)
	TextView tv_jianjie;
	@InjectView(R.id.tv_item_1)
	TextView tv_comment;
	@InjectView(R.id.tv_item_2)
	TextView tv_love;
	@InjectView(R.id.tv_item_3)
	TextView tv_concern;
	@InjectView(R.id.tv_item_4)
	TextView tv_fans;

	private PublicViewAdapter<MyItemInfo> adapter = null;
	private LinkedList<MyItemInfo> mDatas = new LinkedList<>();
	private View view;
	/** 标志位，标志已经初始化完成。 */
	private boolean isPrepared;

	private DataModel dataModel;
	private UserInfo userInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.fragment_my_tab);
	}

	@Override
	protected void initView(View view, LayoutInflater inflater) {
		super.initView(view, inflater);
		setTitle("我的");
		dataModel=new DataModel();
		userInfo=dataModel.getLoginUser();
		tv_name.setText(userInfo.nickname);
		if (CommonUtil.notEmpty(userInfo.intro))
			tv_jianjie.setText(userInfo.intro);
		Picasso.with(getActivity()).load(DataModel.getImageUrl(userInfo.headImage)).transform(new CircleTransform()).placeholder(R.drawable.icon_default).into(iv_photo);
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_my, menu);
	}

	@OnClick({R.id.rl_item_0,R.id.rl_item_1,R.id.rl_item_2,R.id.rl_item_3})
	void  doItemClick(View view){
		switch (view.getId()){
			case R.id.rl_item_0://info
				setLastClickTime();
				ViewUtil.openActivity(UserInfoActivity.class,getActivity());
				break;
			case R.id.rl_item_1://新建
				setLastClickTime();
				ViewUtil.openActivity(FamilyAddActivity.class,getActivity());
				break;
			case R.id.rl_item_2://移除
				setLastClickTime();
				ViewUtil.openActivity(FamilyRemoveActivity.class,getActivity());
				break;
			case R.id.rl_item_3://更改
				setLastClickTime();
				ViewUtil.openActivity(FamilyUpdateActivity.class,getActivity());
				break;
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.toolbar_next:

				return true;
			case R.id.toolbar_setting:

				return true;
		}
		return super.onOptionsItemSelected(item);
	}


	/*	@OnClick({ R.id.relativelayout_personal_information, R.id.image_setup,
			R.id.text_comments, R.id.text_like, R.id.text_focus, R.id.text_fans,
			R.id.relativelayout_new_family_member,R.id.relativelayout_remove_account,
			R.id.relativelayout_change_household})
	public void onViewClick(View view) {
		switch (view.getId()) {
		case R.id.relativelayout_change_household:
			openActivity(ChangeHouseHoldActivity.class);
			break;
			
		case R.id.relativelayout_remove_account:
			openActivity(RemoveAccountActivity.class);
			break;
			
		case R.id.relativelayout_new_family_member:
			openActivity(NewStaffActivity.class);
			break;
			
		case R.id.relativelayout_personal_information:
			openActivity(PersonalInformationActivity.class);
			break;

		case R.id.image_setup:
			openActivity(SetUpActivity.class);
			break;

		case R.id.text_comments:
			openActivity(MyCLFFansActivity.class);
			break;

		case R.id.text_like:
			openActivity(MyCLFFansActivity.class);
			break;

		case R.id.text_focus:
			openActivity(MyCLFFansActivity.class);
			break;

		case R.id.text_fans:
			openActivity(MyCLFFansActivity.class);
			break;
		}
	}

	@Override
	protected void lazyLoad(boolean willRefresh) {
		if (!isPrepared || !isVisible) {
			return;
		}
		if (willRefresh) {
			initView();
			setVisibleToRefresh(false);
		}
	}

	@Override
	protected void fragmentHide() {
		
	}

	private void initView() {
		text_title.setText("我");
		mBitmapUtils = XUtilsBitmapHelp.getBitmapUtilsInstance(
				getActivity(),R.drawable.public_default_head, R.drawable.public_default_head);
		
		adapter = new PublicViewAdapter<MyItemInfo>(
				getActivity(), mDatas, R.layout.listview_head, this, this, Constants.TAG.TAG_NONE);
		
		listview.setAdapter(adapter);
	}

	private void initData(){
		try {
			DataService.getInstance().getUserHome(this, BaseApplication.TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onStart(String url, Object tag) {
		super.onStart(url, tag);
	}

	@Override
	public void onSuccess(String url, String result, int resultCode, Object tag) {
		super.onSuccess(url, result, resultCode, tag);
		MyInfo mInfo = JSON.parseObject(result, MyInfo.class);
		text_comments.setText("评论\n"+mInfo.getReplyCount());
		text_fans.setText("粉丝\n"+mInfo.getFansCount());
		text_like.setText("喜欢\n"+mInfo.getPraiseCount());
		text_focus.setText("关注\n"+mInfo.getAttentCount());
		if(!"".equals(mInfo.getNickname()))text_name.setText(mInfo.getNickname());
		if(!"".equals(mInfo.getDescribe()))text_describe.setText(mInfo.getDescribe());
		mBitmapUtils.display(image_my_head, mInfo.getHeadImage(),OtherUtils.roundBitmapLoadCallBack);
		if(mDatas == null){
			mDatas = new LinkedList<MyItemInfo>();
		}
		mDatas.clear();
		if(mInfo.getList() != null){
			mDatas.addAll(mInfo.getList());
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onFailure(String url, HttpException error, String msg,
			Object tag) {
		LogUtils.i("ERROR:"+msg);
		super.onFailure(url, error, msg, tag);
	}

	public static HomeTabMyFragment newInstance() {
		return new HomeTabMyFragment();
	}

	@Override
	public void commonOnClick(View v) {

	}

	@Override
	public void onResume() {
		super.onResume();
		if(adapter != null){
			initData();
		}
	}

	@Override
	public void commonGetView(PublicViewHolder helper, MyItemInfo item,
			OnClickListener onClick, int position, Object tag) {
		ImageView image_head = helper.getView(R.id.image_head);
		TextView text_name = helper.getView(R.id.text_name);
		mBitmapUtils.display(image_head, item.getHeadImage(),OtherUtils.roundBitmapLoadCallBack);
		text_name.setText(item.getAppellation());
	}*/
}
