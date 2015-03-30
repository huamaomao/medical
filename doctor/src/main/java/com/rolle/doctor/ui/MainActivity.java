package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.fragment.DoctorsCircleFragment;
import com.rolle.doctor.fragment.MessageFragment;
import com.rolle.doctor.fragment.MyFragment;
import com.rolle.doctor.fragment.PatientFragment;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;


public class MainActivity extends BaseActivity {

     @InjectView(R.id.rg_group) RadioGroup rgGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void initView() {
        setTitle("消息");
        rgGroup.check(R.id.rb_tab_1);
        ViewUtil.turnToFragment(getSupportFragmentManager(), MessageFragment.class,null,R.id.fl_content);
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_tab_1:
                        setTitle("消息");
                        ViewUtil.turnToFragment(getSupportFragmentManager(), MessageFragment.class,null,R.id.fl_content);
                        break;
                    case R.id.rb_tab_2:
                        setTitle("全部患者(3)");
                        ViewUtil.turnToFragment(getSupportFragmentManager(), PatientFragment.class,null,R.id.fl_content);
                        break;
                    case R.id.rb_tab_3:
                        setTitle("医生圈");
                        ViewUtil.turnToFragment(getSupportFragmentManager(), DoctorsCircleFragment.class,null,R.id.fl_content);
                        break;
                    case R.id.rb_tab_4:
                        setTitle("我的");
                        ViewUtil.turnToFragment(getSupportFragmentManager(), MyFragment.class,null,R.id.fl_content);
                        break;

                }
            }
        });
    }
}
