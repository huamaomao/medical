package com.rolle.doctor.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.jungly.gridpasswordview.GridPasswordView;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 支付密码
 */
public class WalletCashoutPwdActivity extends BaseActivity {

    @InjectView(R.id.pswView)
    GridPasswordView pswView;

    private UserModel userModel;

    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_pay_pwd);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("提现");
        userModel=new UserModel(getContext());

    }

    @OnClick(R.id.btn_next)
    public void onPayPwd(){
        Log.d(pswView.getPassWord());
       // ViewUtil.openActivity();  WalletResetPwdActivity
    }

}
