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
public class AboutUsActivity extends BaseActivity{

    private List<ItemInfo> lsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("关于我们");

    }
}
