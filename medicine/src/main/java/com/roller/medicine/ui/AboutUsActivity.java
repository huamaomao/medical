package com.roller.medicine.ui;

import android.os.Bundle;

import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;

/**
 * 设置
 */
public class AboutUsActivity extends BaseLoadingToolbarActivity {


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
