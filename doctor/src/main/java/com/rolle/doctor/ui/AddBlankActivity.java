package com.rolle.doctor.ui;

import android.os.Bundle;

import com.rolle.doctor.R;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class AddBlankActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_add);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("添加银行卡");
    }
}
