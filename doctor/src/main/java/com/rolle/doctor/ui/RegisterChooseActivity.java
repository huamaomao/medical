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
import com.rolle.doctor.adapter.DoctorListAdpater;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.presenter.RegisterChoosePresenter;
import com.rolle.doctor.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class RegisterChooseActivity extends BaseActivity implements RegisterChoosePresenter.IRegisterView{

    @InjectView(R.id.rv_view)RecyclerView  rv_view;
    private RegisterChoosePresenter presenter;
    private List<CityResponse.Item> list;
    private DoctorListAdpater adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_choose);
        presenter=new RegisterChoosePresenter(this);
    }


    @Override
    protected void initView() {
        super.initView();
        setBackActivity("我的");
        list=new ArrayList<>();
         list.addAll(Util.getUserTypeList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_view.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv_view.setLayoutManager(layoutManager);
        adpater=new DoctorListAdpater(this,list);
        rv_view.setAdapter(adpater);
        rv_view.addOnItemTouchListener(new RecyclerItemClickListener(this,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adpater.setIndex(position);
            }
        }));

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

    @Override
    public CityResponse.Item getType() {
        return adpater.getIndex();
    }

}
