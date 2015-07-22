package com.android.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/6/11 - 10:43
 */
public class CommonFragmentAdapter extends FragmentPagerAdapter{
    private List<Fragment> pageViews;
    private FragmentManager fragmentManager;

    public CommonFragmentAdapter(FragmentManager fm,List<Fragment> pageViews) {
        super(fm);
        this.pageViews=pageViews;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = pageViews.get(position);
        return fragment;
    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return pageViews.size();
    }


   /* @Override
    public Object instantiateItem(ViewGroup container, int position) {
       *//* Fragment fragment = pageViews.get(position);
        if (!fragment.isAdded()) { // 如果fragment还没有added
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commit();
            fragmentManager.executePendingTransactions();
        }
        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView());
        }*//*

        //得到缓存的fragment
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//得到tag，这点很重要
        String fragmentTag = fragment.getTag();
        FragmentTransaction ft = fragmentManager.beginTransaction();
//移除旧的fragment
        ft.remove(fragment);
//换成新的fragment
        // fragment = fragments[position % fragments.length];
//添加新fragment时必须用前面获得的tag，这点很重要
        ft.add(container.getId(), fragment, fragmentTag);
        ft.attach(fragment);
        ft.commit();
        return fragment;
    }
*/




    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
