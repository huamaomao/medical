package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
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
import com.rolle.doctor.event.BaseEvent;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 患者
 */
public class PatientActivity extends BaseActivity{

    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;

    private   RecyclerView lvViewAll;
    private   RecyclerView lvViewMin;
    private   RecyclerView lvViewMax;
    private List<User> dataAll;
    private List<User> dataMin;
    private List<User> dataMax;
    private FriendListAdapater adapaterAll;
    private FriendListAdapater adapaterMin;
    private FriendListAdapater adapaterMax;
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabStrip;
    @InjectView(R.id.viewpage)
    ViewPager viewPager;

    private ViewPagerAdapter pagerAdapter;
    private final String[] titles={"全部","轻微","严重"};

    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        userModel=new UserModel(getContext());
    }

    private void loadList(){
        adapaterAll.addItemAll(userModel.queryPatientList());
        adapaterMax.addItemAll(userModel.queryPatientList());
        adapaterMin.addItemAll(userModel.queryPatientList());
        adapaterAll.checkEmpty();
        adapaterMax.checkEmpty();
        adapaterMin.checkEmpty();
    }

    public void onEvent(BaseEvent event){
        if (CommonUtil.notNull(event)&&event.type==BaseEvent.EV_USER_FRIEND){
            loadList();
        }
    }


    @Override
    protected void initView() {
        super.initView();
        setBackActivity("患者");
        dataAll=new ArrayList<>();
        dataMax=new ArrayList<>();
        dataMin=new ArrayList<>();

        Util.initTabStrip(tabStrip, getContext());
        List<View> views=new ArrayList<>();
        views.add(getLayoutInflater().inflate(R.layout.public_wrap_recycler_view,null));
        views.add(getLayoutInflater().inflate(R.layout.public_wrap_recycler_view,null));
        views.add(getLayoutInflater().inflate(R.layout.public_wrap_recycler_view,null));
        lvViewAll=(RecyclerView)views.get(0);
        lvViewMin=(RecyclerView)views.get(1);
        lvViewMax=(RecyclerView)views.get(2);
        adapaterAll=new FriendListAdapater(this,dataAll,lvViewAll,FriendListAdapater.TYPE_PATIENT);
        adapaterMin=new FriendListAdapater(this,dataMin,lvViewMin,FriendListAdapater.TYPE_PATIENT);
        adapaterMax=new FriendListAdapater(this,dataMax,lvViewMax,FriendListAdapater.TYPE_PATIENT);
        adapaterAll.flag=true;

        pagerAdapter=new ViewPagerAdapter(titles,views);
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);
        refresh.setRefreshStyle(AppConstants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userModel.requestFriendList(new SimpleResponseListener<List<User>>() {
                    @Override
                    public void requestSuccess(List<User> info, Response response) {
                        // 数据更改
                        loadList();
                    }

                    @Override
                    public void requestError(HttpException e, ResponseMessage info) {

                    }

                    @Override
                    public void requestView() {
                        refresh.setRefreshing(false);
                        adapaterAll.checkEmpty();
                        adapaterMax.checkEmpty();
                        adapaterMin.checkEmpty();
                    }
                });
            }
        });
        loadList();
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
            adapaterAll.onDestroyReceiver();
            adapaterMin.onDestroyReceiver();
            adapaterMax.onDestroyReceiver();
        }catch (Exception e){}
    }
}
