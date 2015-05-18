package com.rolle.doctor.ui;

import android.os.Bundle;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.viewmodel.ViewModel;
import com.jungly.gridpasswordview.GridPasswordView;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 支付密码
 */
public class WalletSetPwdActivity extends BaseLoadingActivity {

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
        setBackActivity("设置支付密码");
        userModel=new UserModel(getContext());
        setCommitMessage();
    }

    @OnClick(R.id.btn_next)
    public void onPayPwd(){
        if (CommonUtil.isEmpty(pswView.getPassWord())&&pswView.getPassWord().length()<6){
            msgLongShow("请输入6位数数字支付密码");
            return;
        }
        showLoading();
        userModel.requestPayPassword(pswView.getPassWord(), "", new ViewModel.ModelListener<ResponseMessage>() {
            @Override
            public void model(Response response, ResponseMessage responseMessage) {
                msgLongShow("设置支付密码成功...");
            }

            @Override
            public void errorModel(HttpException e, Response response) {
                msgLongShow("设置支付密码失败...");
            }

            @Override
            public void view() {
                hideLoading();
            }
        });

    }
}
