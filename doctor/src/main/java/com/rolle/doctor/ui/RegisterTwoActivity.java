package com.rolle.doctor.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rolle.doctor.R;
import com.rolle.doctor.presenter.RegisterTwoPresenter;
import com.rolle.doctor.util.Constants;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class RegisterTwoActivity extends BaseActivity implements RegisterTwoPresenter.IRegisterView{

    @InjectView(R.id.btn_send)Button btn_send;
    @InjectView(R.id.tv_tel_code)TextView tv_tel_code;
    @InjectView(R.id.et_code)EditText et_code;
    private String tel;
    private RegisterTwoPresenter presenter;

    /****
     * 计时器
     */
    private CountDownTimer downTimer=new CountDownTimer(Constants.SMS_SEBD_TIME,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btn_send.setEnabled(false);
            StringBuilder builder=new StringBuilder();
            builder.append(millisUntilFinished/1000);
            builder.append("秒重新获取");
            btn_send.setText(builder.toString());
        }

        @Override
        public void onFinish() {
            btn_send.setText("没有收到?");
            btn_send.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
        tel=getIntent().getStringExtra(com.android.common.util.Constants.DATA_TEL);
        presenter=new RegisterTwoPresenter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_next:
                presenter.doTwoRegister();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("上一步");
        StringBuilder builder=new StringBuilder("验证码短信已发至 +86");
        builder.append(tel);
        tv_tel_code.setText(builder.toString());
        timeSendStart();

    }

    @OnClick(R.id.btn_send)
    void sendSms(){
        presenter.doSendSms();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public String getTel() {
        return tel;
    }

    @Override
    public String getCode() {
        return et_code.getText().toString();
    }

    /***
     * 15m
     */
    public void timeSendStart(){
        downTimer.start();
    }
}
