package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
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

import java.util.LinkedList;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class LoginActivity extends BaseActivity{

    @InjectView(R.id.iv_photo)ImageView ivLogo;
    @InjectView(R.id.ll_login)InputMethodLinearLayout llLogin;
    @InjectView(R.id.et_pwd)EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @OnClick(R.id.btn_login)
    void doLogin(){
        finish();
        ViewUtil.openActivity(MainActivity.class,this);
    }

    @Override
    protected void initView() {
        super.initView();
        etPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEND){
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
                lp.gravity= Gravity.CENTER_HORIZONTAL;
                if(paramBoolean){
                    lp.setMargins(0,ViewUtil.px2dip(LoginActivity.this,60f),0,0);
                    ivLogo.setLayoutParams(lp);
                }else {
                    lp.setMargins(0,ViewUtil.px2dip(LoginActivity.this,240f),0,0);
                    ivLogo.setLayoutParams(lp);
                }
            }
        });
    }

}
