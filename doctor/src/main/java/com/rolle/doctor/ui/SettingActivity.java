package com.rolle.doctor.ui;

import android.os.Bundle;

import com.rolle.doctor.R;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("添加银行卡");
    }
}
