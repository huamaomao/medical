package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.Constants;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.DoctorListAdpater;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.presenter.RegisterTitlePresenter;
import com.rolle.doctor.viewmodel.ListModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;
import butterknife.InjectView;

/**
 * 职称
 */
public class RegisterTitleActivity extends BaseActivity implements RegisterTitlePresenter.IRegisterView{

    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    @InjectView(R.id.rv_view)RecyclerView  rv_view;
    private RegisterTitlePresenter presenter;
    private DoctorListAdpater adpater;
    private List<CityResponse.Item> list;
    private UserModel userModel;
    private ListModel listModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycker);
        presenter=new RegisterTitlePresenter(this);
        list=new ArrayList<>();
        userModel=new UserModel(this);
        listModel=new ListModel(this);
    }


    @Override
    protected void initView() {
        super.initView();
        setBackActivity("职称");
        adpater=new DoctorListAdpater(this,list);
        ViewUtil.initRecyclerView(rv_view, this, adpater);
        rv_view.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adpater.setIndex(position);
            }
        }));
        refresh.setRefreshStyle(com.rolle.doctor.util.Constants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doLoad();
            }
        });
        refresh.setRefreshing(true);
        doLoad();



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.toolbar_next:
                doNext();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void doLoad(){
        listModel.requestTitle("65", new SimpleResponseListener<List<CityResponse.Item>>() {
            @Override
            public void requestSuccess(List<CityResponse.Item> info, Response response) {
                list.clear();
                list.addAll(info);
                adpater.notifyDataSetChanged();
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                    msgLongShow("加载数据失败...");
            }

            @Override
            public void requestView() {
                refresh.setRefreshing(false);
            }
        });
    }


    public void doNext(){
        if (CommonUtil.isNull(adpater.getIndex())){
            msgShow("请选择职称");
            return;
        }
        User user=userModel.getLoginUser();
        user.doctorDetail.jobId=adpater.getIndex().id;
        user.doctorTitle=adpater.getIndex().name;
        userModel.saveUser(user);
        ViewUtil.openActivity(RegisterInfoActivity.class,getContext(),true);
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

    }
}
