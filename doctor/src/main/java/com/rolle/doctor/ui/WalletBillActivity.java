package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.common.util.DividerItemDecoration;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.WalletDatialListAdapater;
import com.rolle.doctor.domain.WalleDetail;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/4/15.
 */
public class WalletBillActivity extends BaseActivity {

    @InjectView(R.id.rv_view)
    RecyclerView rvView;

    private WalletDatialListAdapater mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_detail);
    }


    @Override
    protected void initView() {
        super.initView();
        setBackActivity("账单详细");
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        mAdapter=new WalletDatialListAdapater(this);
        List<WalleDetail> data=new ArrayList<WalleDetail>();
        data.add(new WalleDetail(1));
        data.add(new WalleDetail(1));
        data.add(new WalleDetail(1));
        data.add(new WalleDetail(1));
        data.add(new WalleDetail(1));
        data.add(new WalleDetail(1));
        data.add(new WalleDetail(1));
        data.add(new WalleDetail(2));
        data.add(new WalleDetail(2));
        data.add(new WalleDetail(2));
        data.add(new WalleDetail(2));
        data.add(new WalleDetail(2));
        mAdapter.addAll(data);
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        rvView.addItemDecoration(headersDecor);
        rvView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvView.setAdapter(mAdapter);

    }
}
