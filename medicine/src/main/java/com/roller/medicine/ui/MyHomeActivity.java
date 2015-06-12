package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.FragmentPagerAdapter;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.adapter.FragmentViewPagerAdapter;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.fragment.MyCommentsFragment;
import com.roller.medicine.fragment.MyFansFragment;
import com.roller.medicine.fragment.MyFocusFragment;
import com.roller.medicine.fragment.MyLikeFragment;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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


	@Override
	protected  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_home);
	}

	protected void initView(){
		super.initView();
		setBackActivity("我的主页");
		dataModel=new DataModel();
		userInfo=dataModel.getLoginUser();
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
		fragments.add(new MyCommentsFragment());
		fragments.add(new MyLikeFragment());
		fragments.add(new MyFocusFragment());
		fragments.add(new MyFansFragment());
		FragmentPagerAdapter adapter=new FragmentPagerAdapter(fragments,getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
	    tabLayout.setupWithViewPager(mViewPager);

		//初始化  个人信息
		tv_name.setText(userInfo.nickname);
		if (CommonUtil.notEmpty(userInfo.intro))
			tv_jianjie.setText(userInfo.intro);
		Picasso.with(getContext()).load(DataModel.getImageUrl(userInfo.headImage)).transform(new CircleTransform()).placeholder(R.drawable.icon_default).into(iv_photo);
	}

	@OnClick(R.id.rl_item_0)
	void  doItemClick(View view) {
		setLastClickTime();
		ViewUtil.openActivity(UserInfoActivity.class, getContext());
	}
	
}
