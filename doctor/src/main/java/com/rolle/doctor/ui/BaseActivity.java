package com.rolle.doctor.ui;

import android.app.Activity;
import android.content.Context;
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




    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }


    /***
     * home
     */
    protected void onHomeClick(){

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
}
