package com.roller.medicine.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.fragment.HomeTabKnowledgeQuizFragment;
import com.roller.medicine.fragment.HomeTabMyFragment;
import com.roller.medicine.fragment.TabHomeFragment;
import com.roller.medicine.fragment.TabMessageFragment;
import com.roller.medicine.utils.TimeUtil;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import butterknife.InjectView;

public class HomeActivity extends BaseToolbarActivity {
	public static HomeActivity activity = null;

	@InjectView(R.id.rg_group)
	RadioGroup rgGroup;
	private TextView subView;
	private CalendarPickerView dialogView;
	private AlertDialog theDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pages_home);
		activity = this;
		showCalendarInDialog("选择日期", R.layout.dialog);
	}

	public String getTitleDate(){
		return subView.getText().toString();
	}

	private void showCalendarInDialog(String title, int layoutResId) {
		final Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, -1);
		final Calendar lastYear = Calendar.getInstance();
		lastYear.setTimeInMillis(System.currentTimeMillis());
		lastYear.add(Calendar.DATE, +1);
		dialogView = (CalendarPickerView) getLayoutInflater().inflate(layoutResId, null, false);
		dialogView.init(nextYear.getTime(), lastYear.getTime()).inMode(CalendarPickerView.SelectionMode.SINGLE)
				.withSelectedDate(new Date());
		Toolbar toolbar =(Toolbar)getLayoutInflater().inflate(R.layout.toolbar, null);
		toolbar.setTitle(title);
		theDialog = new AlertDialog.Builder(this)
				.setCustomTitle(toolbar)
				.setView(dialogView)
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					@Override public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				})
				.create();

		theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialogInterface) {
				dialogView.fixDialogDimens();
			}
		});
		dialogView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
			@Override
			public void onDateSelected(Date date) {
				subView.setText(TimeUtil.formmatYmd(date.getTime()));
				theDialog.dismiss();
			}

			@Override
			public void onDateUnselected(Date date) {

			}
		});
		dialogView.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
			@Override
			public void onInvalidDateSelected(Date date) {

			}
		});


	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	protected void initView() {
		subView = new TextView(this);
		subView.setText(TimeUtil.currentLocalDateString());
		subView.setTextColor(getResources().getColor(R.color.public_white));
		subView.setCompoundDrawablePadding(8);
		subView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.icon_spinner), null);
		Toolbar.LayoutParams params = new Toolbar.LayoutParams(
				Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
		//params.setMargins(3, 3, 3, 4);//设置外边界
		subView.setLayoutParams(params);
		mToolbar.addView(subView);
		subView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				theDialog.show();
			}
		});

		setToolbarTitle("医家");
		rgGroup.check(R.id.tab_home);
		ViewUtil.turnToFragment(getSupportFragmentManager(), TabHomeFragment.class, null, R.id.fl_content);
		rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				setSpinnerHide();
				switch (checkedId) {
					case R.id.tab_home:
						setSpinnerShow();
						setToolbarTitle("医家");
						ViewUtil.turnToFragment(getSupportFragmentManager(), TabHomeFragment.class, null, R.id.fl_content);
						break;
					case R.id.tab_message:
						setToolbarTitle("消息");
						ViewUtil.turnToFragment(getSupportFragmentManager(), TabMessageFragment.class, null, R.id.fl_content);
						break;
					case R.id.tab_knowledge_quiz:
						setToolbarTitle("知识问答");
						ViewUtil.turnToFragment(getSupportFragmentManager(), HomeTabKnowledgeQuizFragment.class, null, R.id.fl_content);
						break;
					case R.id.tab_my:
						setToolbarTitle("我的");
						ViewUtil.turnToFragment(getSupportFragmentManager(), HomeTabMyFragment.class, null, R.id.fl_content);
						break;
				}
			}
		});

	}


	public void  setSpinnerHide(){
		subView.setVisibility(View.GONE);
	}

	public void  setSpinnerShow(){
		subView.setVisibility(View.VISIBLE);
	}



	int index=0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			index++;
			if (index==1){
				showLongMsg("再按一次退出客户端");
			}else if(index==2){
				finish();
				exitApp();
			}
			return true;
		}
		index=0;
		return false;
	}

}
