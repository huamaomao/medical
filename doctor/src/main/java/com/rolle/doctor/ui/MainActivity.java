package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import com.android.common.util.ViewUtil;
import com.gotye.api.listener.LoginListener;
import com.rolle.doctor.R;
import com.rolle.doctor.fragment.AddressListFragment;
import com.rolle.doctor.fragment.FriendFragment;
import com.rolle.doctor.fragment.MessageFragment;
import com.rolle.doctor.fragment.MyFragment;
import com.rolle.doctor.presenter.FriendListPresenter;
import com.rolle.doctor.presenter.FriendPresenter;

import butterknife.InjectView;


public class MainActivity extends BaseActivity implements FriendListPresenter.IFriendView{

    @InjectView(R.id.rg_group) RadioGroup rgGroup;
    private int index=0;
    private FriendListPresenter presente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presente=new FriendListPresenter(this);
        presente.initFriendList();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            index++;
            if (index==1){
                msgShow("再按一次退出客户端");
            }else if(index==2){
                exitApp();
            }
            return true;
        }
        index=0;
        return false;
    }

    @Override
    protected void onBackActivty() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
