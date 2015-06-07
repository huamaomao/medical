package com.roller.medicine.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarActivity;

import butterknife.InjectView;

public class SetUpActivity extends BaseToolbarActivity{

	@InjectView(R.id.text_title)
	 TextView text_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup);

	}

	@Override
	protected void initView() {
		super.initView();
		setToolbarTitle("设置");
	}
}
