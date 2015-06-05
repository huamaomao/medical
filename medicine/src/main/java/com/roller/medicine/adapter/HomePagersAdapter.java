package com.roller.medicine.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.util.LogUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class HomePagersAdapter extends FragmentPagerAdapter {

	private List<Fragment> pagers = new ArrayList<Fragment>();

	public HomePagersAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position) {
		return pagers.get(position);
	}

	@Override
	public int getCount() {
		return pagers.size();
	}

	public void addPagers(List<Fragment> pagers) {
		this.pagers.addAll(pagers);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// LogUtils.i("destroyItem"+position);
		// super.destroyItem(container, position, object);//直接不删除item,直接缓存所有的tab
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// LogUtils.i("instantiateItem"+position);
		return super.instantiateItem(container, position);
	}

	@Override
	public int getItemPosition(Object object) {
		// LogUtils.i("getItemPosition"+object);
		// return super.getItemPosition(object);
		// return POSITION_UNCHANGED; //在adapter.notifyDataSetChanged()后不刷新
		return POSITION_NONE; // 在adapter.notifyDataSetChanged()后刷新
	}

}
