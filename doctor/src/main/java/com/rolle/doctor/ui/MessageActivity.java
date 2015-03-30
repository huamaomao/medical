package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.fragment.PatientFragment;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class MessageActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("小叶");
    }
    @OnClick(R.id.action_add)
    void toPatient(){
        ViewUtil.openActivity(PatientHActivity.class,this);
    }


    @Override
    public boolean onMenuItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_add:
                ViewUtil.openActivity(PatientHActivity.class,this);
                break;
        }
        return super.onMenuItemSelected(menuItem);
    }
}
