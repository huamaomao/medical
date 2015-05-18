package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 账单详细
 */
public class WalletDetialActivity extends BaseActivity {

    @InjectView(R.id.tv_amount)
    TextView tv_amount;
    @InjectView(R.id.btn_next)
    Button btn_next;

    private UserModel userModel;

    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walle_detial);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("钱包");
        userModel=new UserModel(getContext());
        wallet=userModel.getWallet();
        tv_amount.setText( CommonUtil.formatMoney(wallet.accountAmount));
        if (!CommonUtil.isCashOutMoney(wallet.accountAmount)){
            btn_next.setText("不可提现");
            btn_next.setEnabled(false);
        }
    }

    @OnClick(R.id.btn_next)
    public void onCashOut(){
        if (!CommonUtil.isCashOutMoney(wallet.accountAmount)){
            btn_next.setEnabled(false);
            return;
        }
        ViewUtil.openActivity(WalletCashoutActivity.class,getContext());

    }

}
