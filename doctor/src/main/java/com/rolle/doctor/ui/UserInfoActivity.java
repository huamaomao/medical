package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.android.common.adapter.QuickAdapter;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.ViewHolderHelp;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.UserDetialAdapater;
import com.rolle.doctor.domain.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class UserInfoActivity extends BaseActivity{

    @InjectView(R.id.rv_view)
    RecyclerView rvView;
    private List<ItemInfo> lsData;
    protected QuickAdapter quickAdapter;

    private UserDetialAdapater adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("资料");
        lsData=new ArrayList<ItemInfo>();
        lsData.add(new ItemInfo());
        lsData.add(new ItemInfo("工作地址","上海市杨浦区人民广场"));
        lsData.add(new ItemInfo("所在医院","上海市人民医院"));
        lsData.add(new ItemInfo("医生职称","副主任医师"));
        lsData.add(new ItemInfo("所在科室","慢性病科"));
        lsData.add(new ItemInfo("专长","糖尿病"));
        rvView.setHasFixedSize(true);
        rvView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        adapater=new UserDetialAdapater(this,lsData);
        rvView.setAdapter(adapater);
    }
}
