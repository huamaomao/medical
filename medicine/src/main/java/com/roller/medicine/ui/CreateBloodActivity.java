package com.roller.medicine.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.adapter.YearSpinnerAdpater;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.HomeInfo;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.viewmodel.DataModel;
import com.roller.medicine.widget.TuneWheel;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;

/****
 *  内容
 */
public class CreateBloodActivity extends BaseLoadingToolbarActivity{
	@InjectView(R.id.sp_start)
	Spinner sp_finamy;
	@InjectView(R.id.tv_chat_time)
	CheckedTextView tv_date;
	@InjectView(R.id.sp_doctor)
	Spinner sp_time;
	@InjectView(R.id.tune_wheel)
	TuneWheel tuneWheel;
	@InjectView(R.id.tv_name)
	TextView tv_name;
	@InjectView(R.id.et_yundong)
	EditText et_yundong;
	@InjectView(R.id.et_yonogyao)
	EditText et_yonogyao;
	@InjectView(R.id.et_xinqing)
	EditText et_xinqing;

	private DataModel service;
	private YearSpinnerAdpater finamyAdpater;
	private YearSpinnerAdpater timeAdpater;
	private CalendarPickerView dialogView;
	private AlertDialog theDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_blood);
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
				tv_date.setText(TimeUtil.formmatYmd(date.getTime()));
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.toolbar_save:
				saveBlood();
				setLastClickTime();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_save,menu);
		return super.onCreateOptionsMenu(menu);
	}


	protected void initView(){
		super.initView();
		service=new DataModel();
		List<HomeInfo.Family>  list= getIntent().getParcelableArrayListExtra(Constants.ITEM);
		if (CommonUtil.isNull(list)){
			list=new ArrayList<>();
			HomeInfo.Family family=new HomeInfo.Family();
			family.appellation="本人";
			family.userId=service.getLoginUser().id+"";
			list.add(family);
		}
		int index=getIntent().getIntExtra(Constants.TYPE, 0);
		String date =getIntent().getStringExtra(Constants.DATA_DATE);
		if (CommonUtil.isEmpty(date)){
			date=TimeUtil.currentLocalDateString();
		}
		tv_date.setText(date);
		// valuee*10
		tuneWheel.initViewParam(72, 333, TuneWheel.MOD_TYPE_ONE);
		StringBuilder builder = new StringBuilder();
		builder.append(7.2).append("mmol/L");
		tv_name.setText(builder.toString());
		tuneWheel.setValueChangeListener(new TuneWheel.OnValueChangeListener() {
			@Override
			public void onValueChange(float value) {
				StringBuilder builder = new StringBuilder();
				builder.append(value).append("mmol/L");
				tv_name.setText(builder.toString());
			}
		});

		finamyAdpater=new YearSpinnerAdpater(this,R.layout.sp_check_write_text,list);
		timeAdpater=new YearSpinnerAdpater(this,R.layout.sp_check_write_text,getResources().getStringArray(R.array.lineTime));
		sp_finamy.setAdapter(finamyAdpater);
		sp_time.setAdapter(timeAdpater);
		setBackActivity("录入血糖");
		sp_time.setSelection(index);
		sp_finamy.setSelection(0);
		tv_date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				theDialog.show();
			}
		});
		showCalendarInDialog("选择日期", R.layout.dialog);
	}


	public void saveBlood(){
		showLoading();
		service.requestSaveBlood(tv_date.getText().toString(), "22", "22", new SimpleResponseListener<HomeInfo>() {
			@Override
			public void requestSuccess(HomeInfo info, Response response) {
				showLongMsg("保存成功.....");
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {
				new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
			}
			@Override
			public void requestView() {
				super.requestView();
				hideLoading();
			}
		});
	}


}


