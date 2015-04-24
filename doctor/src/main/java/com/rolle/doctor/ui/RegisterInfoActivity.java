package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.util.Constants;
import com.android.common.util.DividerItemDecoration;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.UserInfoAdapater;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.presenter.RegisterChoosePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/****
 * 基本资料
 */
public class RegisterInfoActivity extends BaseActivity{

    @InjectView(R.id.rv_view)RecyclerView  rv_view;
    private RegisterChoosePresenter presenter;
    private UserInfoAdapater adpater;
    private  String tel;
    private List<ItemInfo> lsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_choose);
        //presenter=new RegisterChoosePresenter(this);
        //tel=getIntent().getStringExtra(Constants.DATA_TEL);
    }


    @Override
    protected void initView() {
        super.initView();
        setBackActivity("基本资料");
        List<String> startData=new ArrayList<String>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_view.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv_view.setLayoutManager(layoutManager);
        lsData=new ArrayList<ItemInfo>();
        lsData.add(new ItemInfo("省份","请选择省份"));
        lsData.add(new ItemInfo("城市","请选择城市"));
        lsData.add(new ItemInfo("医院","请选择医院"));
        lsData.add(new ItemInfo("科室","请选择科室"));
        adpater=new UserInfoAdapater(this,lsData);
        rv_view.setAdapter(adpater);
        rv_view.addOnItemTouchListener(new RecyclerItemClickListener(this,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        }));

    }


    public void doNext(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.toolbar_register:
                presenter.doNext();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
