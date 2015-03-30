package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;

import com.rolle.doctor.R;

/**
 *
 */
public class MyYaoqingActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_yaoqing);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("我的邀请码");
    }



}
