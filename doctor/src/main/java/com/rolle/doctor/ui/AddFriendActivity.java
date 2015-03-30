package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;

import butterknife.OnClick;

/**
 *
 */
public class AddFriendActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("添加朋友");
    }

    @OnClick(R.id.ll_yaoqing)
    void toYaoqing(){
        ViewUtil.openActivity(MyYaoqingActivity.class, this);
    }

}
