package com.roller.medicine.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Hua_ on 2015/3/29.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> pageViews;
    private String[] titls;
    public ViewPagerAdapter(List<View> pageViews) {
        this.pageViews = pageViews;
    }

    public ViewPagerAdapter(String[] titls, List<View> pageViews) {
        this.titls = titls;
        this.pageViews = pageViews;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pageViews.get(position));
        return pageViews.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(pageViews.get(position));

    }

    @Override
    public int getCount() {
        return pageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.titls==null?null:titls[position];
    }
}
