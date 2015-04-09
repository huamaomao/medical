package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.EditText;

import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.ChatListAdapater;
import com.rolle.doctor.domain.ChatMessage;
import com.rolle.doctor.domain.User;

import java.util.LinkedList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class TheDoctorActivity extends BaseActivity{

     @InjectView(R.id.rv_view) RecyclerView lvView;
    @InjectView(R.id.et_message) EditText etMessage;
    private List<User> data;
    private ChatListAdapater adapater;

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


        lvView.setLayoutManager(layoutManager);
        lvView.setAdapter(adapater);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seach,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
