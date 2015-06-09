package com.roller.medicine.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.android.common.util.CommonUtil;
import com.roller.medicine.R;
import com.roller.medicine.utils.Constants;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public  class BaseToolbarActivity extends AppCompatActivity{

	@Optional
	@InjectView(R.id.toolbar)
	public Toolbar mToolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		ButterKnife.inject(this);
		if(mToolbar!=null){
			setSupportActionBar(mToolbar);
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}
		initView();
	}



	protected  void initView(){
		if (Constants.ACTIVITY_ACTION.equals(getIntent().getAction())){
			finish();
			android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
			System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
			return;
		}
	}

	public void exitApp(){
		finish();
	}


	public void setBackActivity(String title){
		mToolbar.setNavigationIcon(R.drawable.icon_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackActivty();
			}
		});
		setToolbarTitle(title);
	}

	public void setToolbarTitle(String title){
		if (CommonUtil.notNull(mToolbar)){
			mToolbar.setTitle(title);
		}

	}

	public void setNoBackTitle(String title){
		mToolbar.setTitle(title);
	}

	protected void onBackActivty(){
		finish();
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 提示条
	 *
	 * @param content 提示的内容
	 */
	public void showMsg(String content) {
		Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 提示条
	 *
	 * @param content 提示的内容
	 */
	public void showLongMsg(String content) {
		Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
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
		if(ev.getAction()== KeyEvent.KEYCODE_BACK)
		{
			onBackActivty();
			return true;
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

	public BaseToolbarActivity getContext(){
		return this;
	}
}
