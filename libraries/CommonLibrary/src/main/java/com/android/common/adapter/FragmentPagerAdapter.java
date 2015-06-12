package com.android.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/6/11 - 10:43
 */
public class FragmentPagerAdapter extends PagerAdapter {
    private List<Fragment> pageViews;
    private FragmentManager fragmentManager;
    public FragmentPagerAdapter(List<Fragment> pageViews,FragmentManager fragmentManager){
        this.pageViews=pageViews;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public int getCount() {
        return pageViews.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pageViews.get(position).getView()); // 移出viewpager两边之外的page布局
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = pageViews.get(position);
        if (!fragment.isAdded()) { // 如果fragment还没有added
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commit();
            fragmentManager.executePendingTransactions();
        }
        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView());
        }
        return fragment.getView();
    }




    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
