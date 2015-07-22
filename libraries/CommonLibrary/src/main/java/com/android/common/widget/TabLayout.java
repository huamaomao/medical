package com.android.common.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

/**
 * @author Hua_
 * @Description:
 * @date 2015/6/11 - 11:07
 */
public class TabLayout extends android.support.design.widget.TabLayout {

    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTabsFromPagerAdapter(PagerAdapter adapter) {
        if (adapter.getPageTitle(0)==null)return;
        super.setTabsFromPagerAdapter(adapter);

    }

}
