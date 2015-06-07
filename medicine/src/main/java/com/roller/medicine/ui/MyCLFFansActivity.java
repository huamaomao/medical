package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.adapter.FragmentViewPagerAdapter;
import com.roller.medicine.adapter.FragmentViewPagerAdapter.OnExtraPageChangeListener;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.fragment.MyCommentsFragment;
import com.roller.medicine.fragment.MyFansFragment;
import com.roller.medicine.fragment.MyFocusFragment;
import com.roller.medicine.fragment.MyLikeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class MyCLFFansActivity extends BaseToolbarActivity{

	@InjectView(R.id.viewpager)
	 ViewPager mViewPager;
	@InjectView(R.id.text_title)
	 TextView text_title;
	@InjectView(R.id.text_comments)
	 TextView text_comments;
	@InjectView(R.id.text_like)
	 TextView text_like;
	@InjectView(R.id.text_focus)
	 TextView text_focus;
	@InjectView(R.id.text_fans)
	 TextView text_fans;
	@InjectView(R.id.image_line)
	 ImageView image_line;


	private List<Fragment> fragments=new ArrayList<>();

	private int currPosition = 0;
	private int indicatorWidth = 0;
	@Override
	protected  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_clffans_tab);
	}

	protected void initView(){
		text_title.setText("我的主页");

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

	}



	
	private OnExtraPageChangeListener pageChangeListener = new OnExtraPageChangeListener(){
		
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
