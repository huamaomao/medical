package com.roller.medicine.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gotye.api.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roller.medicine.R;
import com.roller.medicine.adapter.FragmentViewPagerAdapter;
import com.roller.medicine.adapter.FragmentViewPagerAdapter.OnExtraPageChangeListener;
import com.roller.medicine.fragment.MyCommentsFragment;
import com.roller.medicine.fragment.MyFansFragment;
import com.roller.medicine.fragment.MyFocusFragment;
import com.roller.medicine.fragment.MyLikeFragment;

public class MyCLFFansActivity extends FragmentActivity{
	
	@ViewInject(R.id.viewpager)
	private ViewPager mViewPager;
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
	@ViewInject(R.id.image_line)
	private ImageView image_line;
	
	public List<Fragment> fragments = new ArrayList<Fragment>();
	private int currPosition = 0;
	private int indicatorWidth = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_clffans_tab);
		ViewUtils.inject(this);
		initView();
	}
	
	private void initView(){
		text_title.setText("我的主页");
		fragments.add(MyCommentsFragment.newInstance());
		fragments.add(MyLikeFragment.newInstance());
		fragments.add(MyFocusFragment.newInstance());
		fragments.add(MyFansFragment.newInstance());
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		indicatorWidth = dm.widthPixels / 4;
		LayoutParams cursor_Params = image_line.getLayoutParams();
		cursor_Params.width = indicatorWidth;
		image_line.setLayoutParams(cursor_Params);
		
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
