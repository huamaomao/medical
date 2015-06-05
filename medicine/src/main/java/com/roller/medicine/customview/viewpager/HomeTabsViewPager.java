package com.roller.medicine.customview.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HomeTabsViewPager extends ViewPager {

	public HomeTabsViewPager(Context context) {
		super(context);
	}

	public HomeTabsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private boolean canTouchScroll = false;

	public void setIsTouchScroll(boolean flag) {
		canTouchScroll = flag;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (canTouchScroll) {
			return super.onTouchEvent(ev);
		} else {
			return false;
		}
	}

	@Override
	public void setOffscreenPageLimit(int limit) {
		super.setOffscreenPageLimit(0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (canTouchScroll) {
			return super.onInterceptTouchEvent(ev);
		} else {
			return false;
		}

	}

}
