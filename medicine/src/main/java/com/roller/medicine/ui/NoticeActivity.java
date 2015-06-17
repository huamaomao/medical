package com.roller.medicine.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.util.ViewUtil;
import com.baoyz.widget.PullRefreshLayout;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.InjectView;

/**
 * Created by Administrator on 2015/6/14 0014.
 */
public class NoticeActivity extends BaseToolbarActivity {

    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    @InjectView(R.id.rv_view)
    RecyclerView recyclerView;
    RecyclerAdapter<Objects> adapter;
    List mdata;
    DataModel dataModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycker);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("通知");
        dataModel=new DataModel();
        refresh.setRefreshStyle(Constants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
            }
        });
        mdata=new ArrayList();
        adapter=new RecyclerAdapter(this,mdata,recyclerView);
        adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, Object item, int position) {

            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                return null;
            }

            @Override
            public int getItemCount() {
                return mdata.size();
            }
        });

        ViewUtil.initRecyclerViewDecoration(recyclerView, this, adapter);
        adapter.checkEmpty();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.onDestroyReceiver();
    }
}
