package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.EditText;
import com.android.common.adapter.QuickAdapter;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.adapter.MessageListAdapter;
import com.rolle.doctor.domain.User;
import java.util.ArrayList;
import java.util.List;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class TheDoctorActivity extends BaseActivity{

     @InjectView(R.id.rv_view) RecyclerView lvView;
    private List<User> data;
    private FriendListAdapater adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("医生圈");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lvView.setLayoutManager(layoutManager);
        lvView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        data=new ArrayList<User>();
        data.add(new User("主治慢性","23","0",R.drawable.icon_people_1,"叶子","0"));
        data.add(new User("主治慢性","26","1",R.drawable.icon_people_2,"孟龙","0"));
        data.add(new User("主治慢性","23","1",R.drawable.icon_people_3,"萌萌","0"));
        lvView.setLayoutManager(layoutManager);
        adapater=new FriendListAdapater(this,data,FriendListAdapater.TYPE_DOCTOR);
        lvView.setAdapter(adapater);
        adapater.setOnItemClickListener(new FriendListAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                ViewUtil.openActivity(DoctorDetialActivity.class, TheDoctorActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seach,menu);
        return super.onCreateOptionsMenu(menu);
    }


}