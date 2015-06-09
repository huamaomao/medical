package com.rolle.doctor.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.common.util.Log;
import com.android.common.view.IView;
import com.rolle.doctor.R;
import com.rolle.doctor.util.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


public class BaseActivity extends ActionBarActivity implements IView{
    protected DoctorApplication application;
    @Optional @InjectView(R.id.toolbar) Toolbar mToolbar;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        application=(DoctorApplication)getApplication();
	}

    public Toolbar getToolbar(){
        return this.mToolbar;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Constants.ACTIVITY_ACTION.equals(getIntent().getAction())){
            finish();
            android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
            System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
            return;
        }
        ButterKnife.inject(this);
        if(mToolbar!=null){
            mToolbar.setSubtitleTextColor(getResources().getColor(R.color.title));
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        initView();

    }



    public void setBackActivity(String title){
       ActionBar actionBar=getSupportActionBar();
       mToolbar.setNavigationIcon(R.drawable.icon_back);
       actionBar.setDisplayHomeAsUpEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackActivty();
            }
        });

       setTitle(title);
    }

    protected void initView(){

    }


    protected void onBackActivty(){
        finish();
        BaseActivity.this.onBackPressed();
    }



    public void exitApp(){
        Intent intent = new Intent(getContext(),BaseActivity.class);
        intent.setAction(Constants.ACTIVITY_ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }


    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 提示条
     *
     * @param content 提示的内容
     */
    public void msgShow(String content) {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示条
     *
     * @param content 提示的内容
     */
    public void msgLongShow(String content) {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            onBackActivty();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected long lastClickTime;
    public boolean flagClick=false;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (flagClick&&isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /****
     *  设置最后触发时间
     */
    public void setLastClickTime(){
        flagClick=true;
        lastClickTime=System.currentTimeMillis();
    }

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        flagClick=false;
        if (timeD >= 0 && timeD <= 500) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }
}
