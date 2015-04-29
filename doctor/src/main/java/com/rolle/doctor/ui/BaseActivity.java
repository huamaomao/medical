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
        ButterKnife.inject(this);
        if(mToolbar!=null){
            mToolbar.setSubtitleTextColor(getResources().getColor(R.color.title));
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTIVITY_ACTION);
        registerReceiver(this.broadcastReceiver, filter); // 注册
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
        BaseActivity.this.onBackPressed();
    }

    // 写一个广播的内部类，当收到动作时，结束activity
    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            exitApp();
            unregisterReceiver(this);
        }
    };


    public void exitApp(){
        Intent intent = new Intent();
        intent.setAction(Constants.ACTIVITY_ACTION); // 说明动作
        sendBroadcast(intent);// 该函数用于发送广播
        android.os.Process.killProcess(android.os.Process.myPid());
        finish();
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
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
