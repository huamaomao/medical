package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.rolle.doctor.R;
import com.rolle.doctor.presenter.YaoqingPresenter;

import butterknife.InjectView;

/**
 *
 */
public class MyYaoqingActivity extends BaseActivity implements YaoqingPresenter.IYaoqingView{

    @InjectView(R.id.tv_code)
    TextView tv_code;
    @InjectView(R.id.tv_num)
    TextView tv_num;

    private YaoqingPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_yaoqing);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("我的邀请码");
        presenter=new YaoqingPresenter(this);
        presenter.doMyCode();
    }


    @Override
    public void setMyCode(String code) {
        tv_code.setText(code);
    }

    @Override
    public void setMyNum(String num) {
        tv_num.setText(num);
    }
}
