package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyHomeActivity extends BaseToolbarActivity{

	@InjectView(R.id.viewpage)
	 ViewPager mViewPager;
	@InjectView(R.id.tabs)
	TabLayout tabLayout;
	private List<Fragment> fragments=new ArrayList<>();
	private int currPosition = 0;
	private int indicatorWidth = 0;


	TextView tv_comment;
	TextView tv_love;
	TextView tv_focus;
	TextView tv_fans;
	@Override
	protected  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_home);
	}

	protected void initView(){
		super.initView();
		setBackActivity("我的主页");
		View view=getLayoutInflater().inflate(R.layout.tab_my_home,null);
		((TextView)ButterKnife.findById(view,R.id.tv_title)).setText("评论");
		tv_comment=ButterKnife.findById(view,R.id.tv_name);
		tabLayout.addTab(tabLayout.newTab().setCustomView(view));
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

		/*View view=getLayoutInflater().inflate(R.layout.tab_my_home,null);
		View view1=getLayoutInflater().inflate(R.layout.tab_my_home,null);
		View view2=getLayoutInflater().inflate(R.layout.tab_my_home,null);
		tabLayout.addView(view1);
		tabLayout.addView(view1);
		tabLayout.addView(view2);*/
		/*text_title.setText("我的主页");

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		indicatorWidth = dm.widthPixels / 4;
		LayoutParams cursor_Params = image_line.getLayoutParams();
		cursor_Params.width = indicatorWidth;
		image_line.setLayoutParams(cursor_Params);
		fragments.add(new MyCommentsFragment());
		fragments.add(new MyLikeFragment());
		fragments.add(new MyFocusFragment());
		fragments.add(new MyFansFragment());

		FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(
				getSupportFragmentManager(), mViewPager,fragments);
		adapter.setOnExtraPageChangeListener(pageChangeListener);
		mViewPager.setAdapter(adapter);
*/
	}



	
/*	private OnExtraPageChangeListener pageChangeListener = new OnExtraPageChangeListener(){
		
		@Override
		public void onExtraPageSelected(int i) {
			
		}
		
		@Override
		public void onExtraPageScrolled(int arg0, float v, int i2) {
			super.onExtraPageScrolled(arg0, v, i2);
			int position = i2 / 4;
			int indexEnd = 0;
			
			if (arg0 == 0) {
				currPosition = 0 + position;
				indexEnd = text_comments.getLeft() + position;
				initStartAnimation(currPosition, indexEnd);
				
			} else if (arg0 == 1) {
				indexEnd = text_like.getLeft() + position;
				currPosition = indicatorWidth + position;
				initStartAnimation(currPosition, indexEnd);
				
			} else if (arg0 == 2) {
				indexEnd = text_focus.getLeft() + position;
				currPosition = indicatorWidth * 2 + position;
				initStartAnimation(currPosition, indexEnd);
			}
		}
		
	};
	
	private void initStartAnimation(int index, int indexEnd) {
		TranslateAnimation animation = new TranslateAnimation(index, indexEnd,
				0f, 0f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(100);
		animation.setFillAfter(true);
		image_line.startAnimation(animation);
	}
	*/
	
		//case R.id.tAllOrder:
		//	Log.e("info", "点击时的状态:" + Utils.orderFragment);
		//	initStartAnimation(currentIndicatorLeft, tAllOrder.getLeft());
		//	mViewPager.setCurrentItem(0);
		//	currentIndicatorLeft = tAllOrder.getLeft();
		//	break;
		//case R.id.tWinMoney:
		//	initStartAnimation(currentIndicatorLeft, tWinMoney.getLeft());
		//	mViewPager.setCurrentItem(1);
		//	currentIndicatorLeft = tWinMoney.getLeft();
		//	break;
		//case R.id.tRightWinMoney:
		//	initStartAnimation(currentIndicatorLeft, tRightWinMoney.getLeft());
		//	mViewPager.setCurrentItem(2);
		//	currentIndicatorLeft = tRightWinMoney.getLeft();
		//	break;
		//case R.id.tHeMaiOrder:
		//	initStartAnimation(currentIndicatorLeft, tHeMaiOrder.getLeft());
		//	mViewPager.setCurrentItem(4);
		//	currentIndicatorLeft = tHeMaiOrder.getLeft();
		//	break;
		//case R.id.tZhuiHaoOrder:
		//	initStartAnimation(currentIndicatorLeft, tZhuiHaoOrder.getLeft());
		//	mViewPager.setCurrentItem(3);
		//	currentIndicatorLeft = tZhuiHaoOrder.getLeft();
		//	break;
	
	
}
