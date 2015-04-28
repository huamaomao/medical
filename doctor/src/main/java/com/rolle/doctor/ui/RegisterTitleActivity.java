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
    private List<CityResponse.Item> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_choose);
        presenter=new RegisterTitlePresenter(this);
        list=new ArrayList<>();
    }


    @Override
    protected void initView() {
        super.initView();
        setBackActivity("职称");
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
        presenter.doLoad();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.toolbar_next:
                presenter.doNext();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public CityResponse.Item getTitleItem() {
        return adpater.getIndex();
    }

    @Override
    public void setTitleList(ArrayList<CityResponse.Item> list) {
       this.list.clear();
       this.list.addAll(list);
       adpater.notifyDataSetChanged();
    }
}
