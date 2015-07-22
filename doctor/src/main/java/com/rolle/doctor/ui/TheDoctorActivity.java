package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.astuetz.PagerSlidingTabStrip;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.adapter.ViewPagerAdapter;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 医生
 * Created by Hua_ on 2015/3/27.
 */
public class TheDoctorActivity extends BaseActivity{

    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    private   RecyclerView lvViewAll;
    private   RecyclerView lvViewSame;
    private List<User> data;
    private List<User> dataSame;
    private FriendListAdapater adapaterAll;
    private FriendListAdapater adapaterSame;
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabStrip;
    @InjectView(R.id.viewpage)
    ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private final String[] titles={"全部","同科室"};
    private UserModel userModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        userModel=new UserModel(getContext());
    }


    @Override
    protected void initView() {
        super.initView();
        refresh.setRefreshStyle(AppConstants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //
                userModel.requestFriendList(new SimpleResponseListener<List<User>>() {
                    @Override
                    public void requestSuccess(List<User> info, Response response) {
                        adapaterSame.addItemAll(userModel.querySameFriendList());
                        adapaterAll.addItemAll(userModel.queryFriendList());
                    }

                    @Override
                    public void requestError(HttpException e, ResponseMessage info) {

                    }

                    @Override
                    public void requestView() {
                        refresh.setRefreshing(false);
                    }
                });

            }
        });
        setBackActivity("医生圈");
        Util.initTabStrip(tabStrip, getContext());
        data=new ArrayList<>();
        data.addAll(userModel.queryFriendList());
        dataSame=new ArrayList<>();
        dataSame.addAll(userModel.querySameFriendList());
        List<View> views=new ArrayList<>();
        views.add(getLayoutInflater().inflate(R.layout.public_wrap_recycler_view,null));
        views.add(getLayoutInflater().inflate(R.layout.public_wrap_recycler_view, null));
        lvViewAll=(RecyclerView)views.get(0);
        lvViewSame=(RecyclerView)views.get(1);
        adapaterSame=new FriendListAdapater(this,dataSame,lvViewAll,FriendListAdapater.TYPE_DOCTOR);
        adapaterAll=new FriendListAdapater(this,data,lvViewSame,FriendListAdapater.TYPE_DOCTOR);
        pagerAdapter=new ViewPagerAdapter(titles,views);
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seach,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_seach:
                ViewUtil.openActivity(SeachActivity.class,getContext());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            adapaterSame.onDestroyReceiver();
            adapaterAll.onDestroyReceiver();
        }catch (Exception e){}
    }

}
