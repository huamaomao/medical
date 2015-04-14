package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class RetrievePwdActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

    }

    @Override
       protected void initView() {
        super.initView();
        setBackActivity("找回密码");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_next:
                ViewUtil.openActivity(RetrievePwdSettingActivity.class, this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
