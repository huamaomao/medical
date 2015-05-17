package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.DividerItemDecoration;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.WalletDatialListAdapater;
import com.rolle.doctor.domain.WalleDetail;
import com.rolle.doctor.domain.WalletBill;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.UserModel;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 账单详细
 */
public class WalletBillActivity extends BaseActivity {

    @InjectView(R.id.rv_view)
    RecyclerView rvView;
    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    private WalletDatialListAdapater mAdapter;
    private UserModel userModel;
    private List<WalletBill.Item> lsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycker);
    }


    @Override
    protected void initView() {
        super.initView();
        setBackActivity("账单详细");
        userModel=new UserModel(getContext());
        refresh.setRefreshStyle(Constants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        flagClick=false;
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        lsData=new ArrayList<>();

        mAdapter=new WalletDatialListAdapater(this);
        mAdapter.addAll(lsData);
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        rvView.addItemDecoration(headersDecor);
        rvView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvView.setAdapter(mAdapter);
        loadData();

    }

    private void loadData(){
        userModel.requestAddWalletBill(new ViewModel.ModelListener<List<WalletBill.Item>>() {
            @Override
            public void model(Response response, List<WalletBill.Item> item) {
                mAdapter.clear();
                mAdapter.addAll(item);
            }

            @Override
            public void errorModel(HttpException e, Response response) {

            }

            @Override
            public void view() {
                refresh.setRefreshing(false);
            }
        });
    }
}
