package com.rolle.doctor.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.viewmodel.RegisterModel;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class RetrievePwdSettingActivity extends BaseLoadingActivity{

    @InjectView(R.id.et_tel)
    EditText et_tel;
    @InjectView(R.id.et_code)
    EditText et_code;
    @InjectView(R.id.et_pwd)
    EditText et_pwd;
    @InjectView(R.id.btn_send)
    Button btn_send;
    private RegisterModel registerModel;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);

    }

    @Override
       protected void initView() {
        super.initView();
        setBackActivity("找回密码");
        registerModel=new RegisterModel();
        userModel=new UserModel(getContext());
        loadingFragment.setMessage("正在提交数据...");
    }

    /******
     * 发送短信验证码
     */
    @OnClick(R.id.btn_send)
    void onSendSms(){
        if (!CommonUtil.isMobileNO(et_tel.getText().toString())) {
            msgShow("手机号格式错误...");
            return;
        }
        downTimer.start();
        btn_send.setEnabled(false);
        registerModel.requestSendSms(et_tel.getText().toString(), "84", new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                msgShow("发送成功...");
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                msgShow("发送失败...");
            }
        });

    }

    /****
     * 计时器
     */
    private CountDownTimer downTimer=new CountDownTimer(com.rolle.doctor.util.Constants.SMS_SEBD_TIME,1000) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_next:
                doCommit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    void doCommit(){
        if (!CommonUtil.isMobileNO(et_tel.getText().toString())) {
            msgShow("手机号格式错误...");
            return;
        }
        if (CommonUtil.isEmpty(et_code.getText().toString())||et_code.getText().toString().length()<4) {
            msgShow("验证码错误,必须是4位数");
            return;
        }
        if (!CommonUtil.checkPassword(et_pwd.getText().toString())) {
            msgShow("密码必须是6-15位...");
            return;
        }


        showLoading();
        userModel.requestUpdatePassword(et_tel.getText().toString(), et_code.getText().toString(), et_pwd.getText().toString(), new SimpleResponseListener<User>() {
            @Override
            public void requestSuccess(User info, Response response) {
                msgShow("密码修改成功....");
                ViewUtil.openActivity(MainActivity.class, getContext(),true);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                msgShow("密码修改失败....");
            }

            @Override
            public void requestView() {
                hideLoading();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
