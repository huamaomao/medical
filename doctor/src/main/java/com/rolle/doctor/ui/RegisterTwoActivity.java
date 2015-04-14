package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class RegisterTwoActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_next:
                finish();
                ViewUtil.openActivity(RegisterThreeActivity.class,this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
