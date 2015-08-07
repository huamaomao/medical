package com.rolle.doctor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 * 邀请说明
 *
 */
public class InviteExPlainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_explain);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("邀请说明");
    }
}
