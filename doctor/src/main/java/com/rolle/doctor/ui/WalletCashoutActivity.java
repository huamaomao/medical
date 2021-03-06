package com.rolle.doctor.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 提现金额
 */
public class WalletCashoutActivity extends BaseActivity {

    @InjectView(R.id.et_amount)
    EditText et_amount;
    @InjectView(R.id.btn_next)
    Button btn_next;

    private UserModel userModel;

    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_cashout);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("提现");
        userModel=new UserModel(getContext());
        wallet=userModel.getWallet();
        et_amount.setHint("当前金额"+CommonUtil.formatMoney(wallet.accountAmount));
    }

    @OnClick(R.id.btn_next)
    public void onCashOut(){
        String et=et_amount.getText().toString();
        if (CommonUtil.isCashOutMoney(et,wallet.accountAmount)){
            msgShow("不可提现...");
            return;
        }
       ViewUtil.openActivity(WalletChooseActivity.class, getContext());
    }

}
