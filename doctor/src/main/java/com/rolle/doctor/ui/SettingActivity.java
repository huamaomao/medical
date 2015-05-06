package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.SettingAdapater;
import com.rolle.doctor.domain.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity{

    private List<ItemInfo> lsData;
    @InjectView(R.id.rv_view)
    RecyclerView recyclerView;
    private SettingAdapater settingAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("设置");
        lsData=new ArrayList<ItemInfo>();
        lsData.add(new ItemInfo());
        lsData.add(new ItemInfo("意见反馈"));
        lsData.add(new ItemInfo("给我们评分"));
        lsData.add(new ItemInfo("关于我们"));
        settingAdapater=new SettingAdapater(getContext(),lsData);
        ViewUtil.initRecyclerView(recyclerView,getContext(),settingAdapater);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                       ViewUtil.openActivity(AboutUsActivity.class,getContext());
                        break;
                }
            }
        }));
    }
}
