package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;

/**
 * Created by Hua_ on 2015/4/15.
 */
public class WalletDetialActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walle);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("提现");

    }


}