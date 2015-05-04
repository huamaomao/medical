package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.EditText;

import com.android.common.util.DividerItemDecoration;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 * 新朋友
 */
public class FriendActivity extends BaseActivity{

     @InjectView(R.id.rv_view) RecyclerView lvView;
    private List<User> data;
    private FriendListAdapater adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("添加朋友");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lvView.setLayoutManager(layoutManager);
        lvView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        data=new ArrayList<User>();
        data.add(new User("主治慢性","23","0",R.drawable.icon_people_1,"叶子","0","0"));
        data.add(new User("主治慢性","26","1",R.drawable.icon_people_2,"孟龙","0","1"));
        data.add(new User("主治慢性","23","1",R.drawable.icon_people_3,"萌萌","0","1"));
        lvView.setLayoutManager(layoutManager);
        adapater=new FriendListAdapater(this,data,FriendListAdapater.TYPE_FRIEND);
        lvView.setAdapter(adapater);
        lvView.setItemAnimator(new DefaultItemAnimator());
        adapater.setOnItemClickListener(new FriendListAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                if ("0".equals(user.getType())){
                    ViewUtil.openActivity(PatientHActivity.class, FriendActivity.this);
                }else {
                    ViewUtil.openActivity(DoctorDetialActivity.class, FriendActivity.this);
                }
            }
        });
    }

}
