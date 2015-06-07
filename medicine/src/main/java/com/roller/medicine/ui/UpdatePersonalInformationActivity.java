package com.roller.medicine.ui;

import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseApplication;
import com.roller.medicine.base.BaseToolbarActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class UpdatePersonalInformationActivity extends BaseToolbarActivity{

	@InjectView(R.id.edit_content)
	 EditText edit_content;
	@InjectView(R.id.text_title)
	 TextView text_title;
	@InjectView(R.id.text_ok)
	 TextView text_ok;
	
	private String id = "";
	private String title = "";
	private String content = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_personal_information);

	}

	protected void initView(){
		initTitle();
	}
	
	private void initTitle(){
		/*if(Constants.NICKNAME.equals(id)){
			title = "编辑昵称";
		}else if(Constants.PHONE.equals(id)){
			title = "编辑手机号";
		}else if(Constants.DESCRIBE.equals(id)){
			title = "编辑描述";
		}
		text_title.setText(title);
		edit_content.setText(content);*/
	}
	
	@OnClick({R.id.image_return,R.id.text_ok})
	public void onViewOnClick(View view){
		switch (view.getId()) {
		case R.id.text_ok:
			initData();
			break;
		}
	}
	
	private void initData(){
		content = edit_content.getText().toString();
		
		/*if(Constants.NICKNAME.equals(id)){
			try {
				DataService.getInstance().saveDoctor(this, BaseApplication.TOKEN, null, content,
						null, null, null, null, null, null, null, null, null, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(Constants.PHONE.equals(id)){
			try {
				DataService.getInstance().saveDoctor(this, BaseApplication.TOKEN, null, null, 
						null, content, null, null, null, null, null, null, null, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(Constants.DESCRIBE.equals(id)){
			try {
				DataService.getInstance().saveDoctor(this, BaseApplication.TOKEN, null, null, null, 
						null, null, null, null, null, content, null, null, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}
	
	/*@Override
	public void onSuccess(String url, String result, int resultCode, Object tag) {
		super.onSuccess(url, result, resultCode, tag);
		if(resultCode == 200){
			onReturn();
		}
	}*/
}
