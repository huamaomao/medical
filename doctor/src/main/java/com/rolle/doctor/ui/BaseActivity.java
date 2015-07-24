package com.rolle.doctor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.android.common.view.IView;
import com.rolle.doctor.R;
import com.rolle.doctor.util.AppConstants;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


public class BaseActivity extends AppCompatActivity implements IView{
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
        if (AppConstants.ACTIVITY_ACTION.equals(getIntent().getAction())){
            finish();
            android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
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


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void setBackActivity(String title){
       ActionBar actionBar=getSupportActionBar();
       mToolbar.setNavigationIcon(R.mipmap.icon_back);
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
    }


    public void exitApp(){
        Intent intent = new Intent(getContext(),BaseActivity.class);
        intent.setAction(AppConstants.ACTIVITY_ACTION);
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
        switch (keyCode){
            case KeyEvent.KEYCODE_MENU:
                return true;
            case KeyEvent.KEYCODE_BACK:
                onBackActivty();
                return true;

        }
        return super.onKeyDown(keyCode,event);
    }


    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
