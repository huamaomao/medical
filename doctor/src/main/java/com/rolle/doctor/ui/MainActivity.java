package com.rolle.doctor.ui;

import android.os.Bundle;
import android.widget.RadioGroup;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.fragment.AddressListFragment;
import com.rolle.doctor.fragment.FriendFragment;
import com.rolle.doctor.fragment.MessageFragment;
import com.rolle.doctor.fragment.MyFragment;
import butterknife.InjectView;


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
                        setTitle("医家");
                        ViewUtil.turnToFragment(getSupportFragmentManager(), MessageFragment.class,null,R.id.fl_content);
                        break;
                    case R.id.rb_tab_2:
                        setTitle("通讯录");
                        ViewUtil.turnToFragment(getSupportFragmentManager(), FriendFragment.class,null,R.id.fl_content);
                        break;
                    case R.id.rb_tab_3:
                        setTitle("我的");
                        ViewUtil.turnToFragment(getSupportFragmentManager(), MyFragment.class,null,R.id.fl_content);
                        break;

                }
            }
        });
    }
}
