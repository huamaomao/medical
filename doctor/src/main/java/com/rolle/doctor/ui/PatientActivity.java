package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.android.common.util.DividerItemDecoration;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 患者
 */
public class PatientActivity extends BaseActivity{

    @InjectView(R.id.rv_view) RecyclerView lvView;
    @InjectView(R.id.toolbar_spinner)  Spinner spinner;

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
        setBackActivity("");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lvView.setLayoutManager(layoutManager);
        data=new ArrayList<User>();
        data.add(new User(R.drawable.icon_people_1,"叶子","约不约.....","男","22","5.6","15.6"));
        data.add(new User(R.drawable.icon_people_1,"叶子","约不约.....","男","22","5.6","15.6"));
        data.add(new User(R.drawable.icon_people_1,"叶子","约不约.....","男","22","5.6","15.6"));
        lvView.setLayoutManager(layoutManager);
        lvView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapater=new FriendListAdapater(this,data,FriendListAdapater.TYPE_PATIENT);
        lvView.setAdapter(adapater);
        adapater.setOnItemClickListener(new FriendListAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                ViewUtil.openActivity(PatientHActivity.class,PatientActivity.this);
            }
        });
        SpinnerAdapter mSpinner = ArrayAdapter.createFromResource(this, R.array.spinner_patient, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mSpinner);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seach,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
