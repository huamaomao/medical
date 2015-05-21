package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.astuetz.PagerSlidingTabStrip;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.adapter.ViewPagerAdapter;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.Constants;
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
        adapaterAll.addCleanItems(userModel.queryPatientList());
        adapaterMax.addCleanItems(userModel.queryPatientList());
        adapaterMin.addCleanItems(userModel.queryPatientList());
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
        adapaterAll=new FriendListAdapater(this,dataAll,FriendListAdapater.TYPE_PATIENT);
        adapaterMin=new FriendListAdapater(this,dataMin,FriendListAdapater.TYPE_PATIENT);
        adapaterMax=new FriendListAdapater(this,dataMax,FriendListAdapater.TYPE_PATIENT);
        adapaterAll.flag=true;
        ViewUtil.initRecyclerView(lvViewAll,getContext(),adapaterAll);
        ViewUtil.initRecyclerView(lvViewMin,getContext(),adapaterMin);
        ViewUtil.initRecyclerView(lvViewMax,getContext(),adapaterMax);
        pagerAdapter=new ViewPagerAdapter(titles,views);
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);
        lvViewAll.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                User item = dataAll.get(position);
                if (CommonUtil.notNull(item)) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.ITEM, item);
                    ViewUtil.openActivity(MessageActivity.class, bundle, getContext(), ActivityModel.ACTIVITY_MODEL_1);
                }
            }
        }));
        lvViewMax.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                User item = dataMax.get(position);
                if (CommonUtil.notNull(item)) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.ITEM, item);
                    ViewUtil.openActivity(MessageActivity.class, bundle, getContext(), ActivityModel.ACTIVITY_MODEL_1);
                }
            }
        }));
        lvViewMin.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                User item=dataMin.get(position);
                if (CommonUtil.notNull(item)){
                    Bundle bundle=new Bundle();
                    bundle.putParcelable(Constants.ITEM,item);
                    ViewUtil.openActivity(MessageActivity.class,bundle,getContext(), ActivityModel.ACTIVITY_MODEL_1);
                }
            }
        }));
        refresh.setRefreshStyle(Constants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userModel.requestFriendList(new ViewModel.ModelListener<List<User>>() {
                    @Override
                    public void model(Response response, List<User> items) {
                        // 数据更改
                        loadList();
                    }

                    @Override
                    public void errorModel(HttpException e, Response response) {

                    }

                    @Override
                    public void view() {
                        refresh.setRefreshing(false);
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

}
