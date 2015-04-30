package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import com.android.common.util.ViewUtil;
import com.astuetz.PagerSlidingTabStrip;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.adapter.ViewPagerAdapter;
import com.rolle.doctor.domain.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 患者
 */
public class PatientActivity extends BaseActivity{

    private   RecyclerView lvViewAll;
    private   RecyclerView lvViewMin;
    private   RecyclerView lvViewMax;
    private List<User> data;
    private FriendListAdapater adapaterAll;
    private FriendListAdapater adapaterMin;
    private FriendListAdapater adapaterMax;
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabStrip;
    @InjectView(R.id.viewpage)
    ViewPager viewPager;

    private ViewPagerAdapter pagerAdapter;
    private final String[] titles={"全部","轻微","严重"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("患者");
        tabStrip.setIndicatorColor(getResources().getColor(R.color.title));
        tabStrip.setShouldExpand(true);
        tabStrip.setTextSize(getResources().getDimensionPixelSize(R.dimen.font_size_17));
        tabStrip.setIndicatorHeight(3);
        data=new ArrayList<User>();
        data.add(new User("主治慢性","23","0",R.drawable.icon_people_1,"叶子","0"));
        data.add(new User("主治慢性","26","1",R.drawable.icon_people_2,"孟龙","0"));
        data.add(new User("主治慢性", "23", "1", R.drawable.icon_people_3, "萌萌", "0"));
        List<View> views=new ArrayList<>();
        views.add(getLayoutInflater().inflate(R.layout.public_wrap_recycler_view,null));
        views.add(getLayoutInflater().inflate(R.layout.public_wrap_recycler_view,null));
        views.add(getLayoutInflater().inflate(R.layout.public_wrap_recycler_view,null));
        lvViewAll=(RecyclerView)views.get(0);
        lvViewMin=(RecyclerView)views.get(1);
        lvViewMax=(RecyclerView)views.get(2);
        adapaterAll=new FriendListAdapater(this,data,FriendListAdapater.TYPE_PATIENT);
        adapaterMin=new FriendListAdapater(this,data,FriendListAdapater.TYPE_PATIENT);
        adapaterMax=new FriendListAdapater(this,data,FriendListAdapater.TYPE_PATIENT);
        ViewUtil.initRecyclerView(lvViewAll,getContext(),adapaterAll);
        ViewUtil.initRecyclerView(lvViewMin,getContext(),adapaterMin);
        ViewUtil.initRecyclerView(lvViewMax,getContext(),adapaterMax);
        pagerAdapter=new ViewPagerAdapter(titles,views);
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seach,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
