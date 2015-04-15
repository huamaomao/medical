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
        setBackActivity("钱包");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_walle,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_walle:
                ViewUtil.openActivity(WalletBillActivity.class,this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
