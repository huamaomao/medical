package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.common.util.Log;
import com.rolle.doctor.R;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class BaseActivity extends ActionBarActivity {
    protected DoctorApplication application;
    @InjectView(R.id.toolbar) Toolbar mToolbar;

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
        mToolbar.setSubtitleTextColor(getResources().getColor(R.color.title));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
         mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onMenuItemSelected(menuItem);
            }
        });
        initView();
    }



    public void setBackActivity(String title){
       ActionBar actionBar=getSupportActionBar();
       mToolbar.setNavigationIcon(R.drawable.icon_back);
       actionBar.setDisplayHomeAsUpEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              BaseActivity.this.onBackPressed();
          }
      });

       setTitle(title);
    }

    protected void initView(){

    }



    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }


    /***
     * home
     */
    protected void onHomeClick(){

    }
    public boolean onMenuItemSelected(MenuItem menuItem){
        return  false;
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

}
