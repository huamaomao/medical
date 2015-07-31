package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.widget.InputMethodLinearLayout;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.ChatListAdapater;
import com.rolle.doctor.domain.ChatMessage;
import com.rolle.doctor.presenter.LoginPresenter;

import java.util.LinkedList;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class LoginActivity extends BaseLoadingActivity implements LoginPresenter.ILogin{

    @InjectView(R.id.iv_photo)ImageView ivLogo;
    @InjectView(R.id.ll_login)InputMethodLinearLayout llLogin;
    @InjectView(R.id.et_pwd)EditText etPwd;
    @InjectView(R.id.et_tel)EditText etTel;
    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter=new LoginPresenter(this);

    }

    @OnClick(R.id.btn_login)
    void doLogin(){
        presenter.doLogin();
    }
    @OnClick(R.id.tv_create_account)
    void doRegister(){
        if(CommonUtil.isFastClick())return;
        finish();
        ViewUtil.openActivity(RegisterOneActivity.class,this,true);
    }
    @OnClick(R.id.tv_forgot_pwd)
    void doRetrievePwd(){
        if(CommonUtil.isFastClick())return;
        finish();
        //RetrievePwdSettingActivity
        ViewUtil.openActivity(RetrievePwdSettingActivity.class,this,true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        ViewUtil.onHideSoftInput(this,getCurrentFocus(),event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void initView() {
        super.initView();
        etPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    doLogin();
                    return true;
                }
                return false;
            }
        });


        llLogin.setOnSizeChangedListenner(new InputMethodLinearLayout.OnSizeChangedListenner() {
            @Override
            public void onSizeChange(boolean paramBoolean, int w, int h) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.CENTER_HORIZONTAL;
                if (paramBoolean) {
                    lp.setMargins(0, ViewUtil.px2dip(LoginActivity.this, 80f), 0, 0);
                    ivLogo.setLayoutParams(lp);
                } else {
                    lp.setMargins(0, ViewUtil.px2dip(LoginActivity.this, 640f), 0, 0);
                    ivLogo.setLayoutParams(lp);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.doIsLogin();
    }

    @Override
    public String getTel() {
        return etTel.getText().toString();
    }

    @Override
    public String getPwd() {
        return etPwd.getText().toString();
    }

    @Override
    public void setTel(String tel) {
        if (CommonUtil.notEmpty(tel)){
            etTel.setText(tel);
            etPwd.requestFocus();
        }
    }
}
