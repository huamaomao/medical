package com.roller.medicine.fragment;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.roller.medicine.R;
import com.roller.medicine.ui.ChangeHouseHoldActivity;
import com.roller.medicine.ui.MyCLFFansActivity;
import com.roller.medicine.ui.NewStaffActivity;
import com.roller.medicine.ui.PersonalInformationActivity;
import com.roller.medicine.ui.RemoveAccountActivity;
import com.roller.medicine.ui.SetUpActivity;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.adapter.PublicViewAdapter.ICommonGetView;
import com.roller.medicine.adapter.PublicViewAdapter.ICommonOnClick;
import com.roller.medicine.adapter.PublicViewHolder;
import com.roller.medicine.base.BaseApplication;
import com.roller.medicine.base.BaseFragment;
import com.roller.medicine.customview.listview.HorizontalListView;
import com.roller.medicine.httpservice.Constants;
import com.roller.medicine.httpservice.DataService;
import com.roller.medicine.info.MyInfo;
import com.roller.medicine.info.MyItemInfo;
import com.roller.medicine.utils.OtherUtils;
import com.roller.medicine.utils.XUtilsBitmapHelp;

@SuppressLint("UseValueOf")
public class HomeTabMyFragment extends BaseFragment implements ICommonGetView<MyItemInfo>,
		ICommonOnClick {
	@ViewInject(R.id.relativelayout_personal_information)
	private RelativeLayout relativelayout_personal_information;
	@ViewInject(R.id.listview)
	private HorizontalListView listview;
	@ViewInject(R.id.text_title)
	private TextView text_title;
	@ViewInject(R.id.text_comments)
	private TextView text_comments;
	@ViewInject(R.id.text_like)
	private TextView text_like;
	@ViewInject(R.id.text_focus)
	private TextView text_focus;
	@ViewInject(R.id.text_fans)
	private TextView text_fans;
	@ViewInject(R.id.text_name)
	private TextView text_name;
	@ViewInject(R.id.text_describe)
	private TextView text_describe;
	@ViewInject(R.id.image_my_head)
	private ImageView image_my_head;
	
	private PublicViewAdapter<MyItemInfo> adapter = null;
	private LinkedList<MyItemInfo> mDatas = new LinkedList<MyItemInfo>();
	private BitmapUtils mBitmapUtils;
	private View view;
	/** 标志位，标志已经初始化完成。 */
	private boolean isPrepared;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_tab, container, false);
		ViewUtils.inject(this, view);
		isPrepared = true;
		lazyLoad(willRefresh);
		return view;
	}

	@OnClick({ R.id.relativelayout_personal_information, R.id.image_setup,
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
	}
}
