package com.roller.medicine.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseActivity;

public class SetUpActivity extends BaseActivity{

	@ViewInject(R.id.text_title)
	private TextView text_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup);
		ViewUtils.inject(this);
		initView();
	}
	
	private void initView(){
		text_title.setText("设置");
	}
}
