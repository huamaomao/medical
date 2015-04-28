package com.rolle.doctor.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.presenter.YaoqingPresenter;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 *
 */
public class MyInviteCodeActivity extends BaseActivity implements YaoqingPresenter.IYaoqingView{

    @InjectView(R.id.tv_code)
    TextView tv_code;
    @InjectView(R.id.tv_num)
    TextView tv_num;

    private YaoqingPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invite_code);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("我的邀请码");
        presenter=new YaoqingPresenter(this);
        presenter.doMyCode();
    }


    @OnClick(R.id.btn_write)
    void  doWrite(){
        ViewUtil.openActivity(WriteInviteCodeActivity.class,getContext(), false);
    }
    @OnClick(R.id.btn_invite)
    void  doInvite(){

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
