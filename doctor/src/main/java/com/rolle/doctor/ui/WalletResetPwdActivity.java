package com.rolle.doctor.ui;

import android.os.Bundle;

import com.android.common.util.Log;
import com.jungly.gridpasswordview.GridPasswordView;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 重置支付密码
 */
public class WalletResetPwdActivity extends BaseActivity {

    @InjectView(R.id.pswView)
    GridPasswordView pswView;

    private UserModel userModel;

    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_reset_pwd);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("重置支付密码");
        userModel=new UserModel(getContext());

    }


}
