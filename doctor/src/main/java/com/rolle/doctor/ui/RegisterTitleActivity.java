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
import com.rolle.doctor.presenter.RegisterTitlePresenter;
import java.util.ArrayList;
import java.util.List;
import butterknife.InjectView;

/**
 * 职称
 */
public class RegisterTitleActivity extends BaseActivity implements RegisterTitlePresenter.IRegisterView{

    @InjectView(R.id.rv_view)RecyclerView  rv_view;
    private RegisterTitlePresenter presenter;
    private DoctorListAdpater adpater;
    private  String tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_choose);
        presenter=new RegisterTitlePresenter(this);
        tel=getIntent().getStringExtra(Constants.DATA_TEL);
    }


    @Override
    protected void initView() {
        super.initView();
        setBackActivity("职称");
        List<String> startData=new ArrayList<String>();
        startData.add("住院医师");
        startData.add("主治医师");
        startData.add("主任医师");
        startData.add("副主任医师");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_view.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv_view.setLayoutManager(layoutManager);

        adpater=new DoctorListAdpater(this,startData);
        rv_view.setAdapter(adpater);
        rv_view.addOnItemTouchListener(new RecyclerItemClickListener(this,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adpater.setIndex(position);
            }
        }));

    }


    public void doNext(){
        presenter.doNext();
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
    public String getType() {
        return adpater.getIndex();
    }

}
