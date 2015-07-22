package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.CommonFragmentAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.event.BaseEvent;
import com.roller.medicine.event.UserInfoEvent;
import com.roller.medicine.fragment.MyCommentsFragment;
import com.roller.medicine.fragment.MyFansFragment;
import com.roller.medicine.fragment.MyFocusFragment;
import com.roller.medicine.fragment.MyLikeFragment;
import com.roller.medicine.info.MyHomeInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MyHomeActivity extends BaseLoadingToolbarActivity{

	@InjectView(R.id.viewpage)
	 ViewPager mViewPager;
	@InjectView(R.id.tabs)
	TabLayout tabLayout;

	@InjectView(R.id.iv_photo)
	ImageView iv_photo;
	@InjectView(R.id.tv_name)
	TextView tv_name;
	@InjectView(R.id.tv_jianjie)
	TextView tv_jianjie;

	private List<Fragment> fragments=new ArrayList<>();

	TextView tv_comment;
	TextView tv_love;
	TextView tv_focus;
	TextView tv_fans;
	private DataModel dataModel;
	private UserInfo userInfo;
	public String userId=null;
	private boolean flag=false;

	@Override
	protected  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_home);
		EventBus.getDefault().register(this);
	}

	protected void initView(){
		super.initView();
		userId=getIntent().getStringExtra(AppConstants.ITEM);

		setBackActivity("我的主页");
		dataModel=new DataModel();
		// init tabs
		View view=getLayoutInflater().inflate(R.layout.tab_my_home,null);
		((TextView)ButterKnife.findById(view,R.id.tv_title)).setText("评论");
		tv_comment=ButterKnife.findById(view,R.id.tv_name);
		tabLayout.addTab(tabLayout.newTab().setCustomView(view), true);
		View view1=getLayoutInflater().inflate(R.layout.tab_my_home,null);
		((TextView)ButterKnife.findById(view1,R.id.tv_title)).setText("喜欢");
		tv_love=ButterKnife.findById(view1,R.id.tv_name);
		tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
		View view2=getLayoutInflater().inflate(R.layout.tab_my_home,null);
		((TextView)ButterKnife.findById(view2,R.id.tv_title)).setText("关注");
		tv_focus=ButterKnife.findById(view2,R.id.tv_name);
		tabLayout.addTab(tabLayout.newTab().setCustomView(view2));
		View view3=getLayoutInflater().inflate(R.layout.tab_my_home,null);
		((TextView)ButterKnife.findById(view3,R.id.tv_title)).setText("粉丝");
		tv_fans=ButterKnife.findById(view3,R.id.tv_name);
		tabLayout.addTab(tabLayout.newTab().setCustomView(view3));

		Bundle bundle=new Bundle();
		bundle.putString(AppConstants.ITEM, userId);
		fragments.add(MyCommentsFragment.newInstantiate(bundle));
		fragments.add(MyLikeFragment.newInstantiate( bundle));
		fragments.add(MyFocusFragment.newInstantiate( bundle));
		fragments.add(MyFansFragment.newInstantiate(bundle));
		CommonFragmentAdapter adapter=new CommonFragmentAdapter(getSupportFragmentManager(),fragments);
		mViewPager.setAdapter(adapter);
	    tabLayout.setupWithViewPager(mViewPager);
		if (userId==null||userId.equals(userInfo.id)){
			userInfo=dataModel.getLoginUser();
			flag=true;
			initUserData();

		}
		loadHome();
	}

	private void initUserData(){
		if (CommonUtil.notNull(userInfo)) {
			tv_name.setText(userInfo.nickname);
			if (CommonUtil.notEmpty(userInfo.intro))
				tv_jianjie.setText(userInfo.intro);
			Picasso.with(getContext()).load(DataModel.getImageUrl(userInfo.headImage)).
					transform(new CircleTransform()).placeholder(R.drawable.icon_default).into(iv_photo);
		}
	}
	public void onEvent(BaseEvent event)
	{
		if (event instanceof UserInfoEvent){
			initUserData();
		}
	}



	@OnClick(R.id.rl_item_0)
	void  doItemClick(View view) {
		if (!flag)return;
		if (CommonUtil.isFastClick())return;
		ViewUtil.openActivity(UserInfoActivity.class, getContext());
	}

	private void loadHome(){
		dataModel.requestUserHome(userId, new SimpleResponseListener<MyHomeInfo>() {
			@Override
			public void requestSuccess(MyHomeInfo info, Response response) {
				tv_name.setText(info.nickname);
				tv_jianjie.setText(info.intro);
				Picasso.with(getContext()).load(DataModel.getImageUrl(info.headImage)).
						transform(new CircleTransform()).placeholder(R.drawable.icon_default).into(iv_photo);
				//Util.loadPhoto(getContext(), DataModel.getImageUrl(info.headImage), iv_photo);
				tv_comment.setText(CommonUtil.initTextValue(info.replyCount));
				tv_love.setText(CommonUtil.initTextValue(info.praiseCount));
				tv_fans.setText(CommonUtil.initTextValue(info.fansCount));
				tv_focus.setText(CommonUtil.initTextValue(info.attentCount));

			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {

			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
