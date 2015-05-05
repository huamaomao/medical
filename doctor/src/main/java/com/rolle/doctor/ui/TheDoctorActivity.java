package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import com.android.common.adapter.QuickAdapter;
import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.ViewUtil;
import com.astuetz.PagerSlidingTabStrip;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.adapter.MessageListAdapter;
import com.rolle.doctor.adapter.ViewPagerAdapter;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 医生
 * Created by Hua_ on 2015/3/27.
 */
public class TheDoctorActivity extends BaseActivity{

    private   RecyclerView lvViewAll;
    private   RecyclerView lvViewSame;
    private List<FriendResponse.Item> data;
    private FriendListAdapater adapaterAll;
    private FriendListAdapater adapaterSmae;
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
        setBackActivity("医生圈");
        Util.initTabStrip(tabStrip, getContext());
        data=new ArrayList<FriendResponse.Item>();
        data.addAll(userModel.queryFriendList());

        adapaterSmae=new FriendListAdapater(this,data,FriendListAdapater.TYPE_DOCTOR);
        adapaterAll=new FriendListAdapater(this,data,FriendListAdapater.TYPE_DOCTOR);
        adapaterAll.setOnItemClickListener(new FriendListAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                ViewUtil.openActivity(DoctorDetialActivity.class, TheDoctorActivity.this);
            }
        });
        List<View> views=new ArrayList<>();
        views.add(getLayoutInflater().inflate(R.layout.public_wrap_recycler_view,null));
        views.add(getLayoutInflater().inflate(R.layout.public_wrap_recycler_view,null));
        lvViewAll=(RecyclerView)views.get(0);
        lvViewSame=(RecyclerView)views.get(1);
        ViewUtil.initRecyclerView(lvViewAll,getContext(),adapaterAll);
        ViewUtil.initRecyclerView(lvViewSame,getContext(),adapaterSmae);
        lvViewAll.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                FriendResponse.Item item=data.get(position);
                if (CommonUtil.notNull(item)){
                    Bundle bundle=new Bundle();
                    bundle.putParcelable(Constants.ITEM,item);
                    ViewUtil.openActivity(DoctorDetialActivity.class,bundle, TheDoctorActivity.this, ActivityModel.ACTIVITY_MODEL_1);
                }
            }
        }));
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
