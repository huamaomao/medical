package com.roller.medicine.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.widget.InputMethodLinearLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.adapter.ViewPagerAdapter;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.info.UserResponseInfo;
import com.roller.medicine.service.MedicineGotyeService;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import java.util.ArrayList;
import java.util.List;
import butterknife.InjectView;
import butterknife.OnClick;


public class WelcomeActivity extends BaseLoadingToolbarActivity{

	@InjectView(R.id.viewpage)
	ViewPager viewpage;
	@InjectView(R.id.rg_group)
	RadioGroup radioGroup;
	private ViewPagerAdapter pagerAdapter;
	public static SharedPreferences sp;
	private  int version=3;

	private Button btnNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		sp=getSharedPreferences("rolle_medicine", Context.MODE_PRIVATE);
		if (sp.getInt(AppConstants.VERSION,0)==version){
			ViewUtil.openActivity(LoginActivity.class,getContext(),true);
			return;
		}
	}

	protected void initView() {
		super.initView();
		List<View> views=new ArrayList();
		views.add(getLayoutInflater().inflate(R.layout.welcome_one,null));
		views.add(getLayoutInflater().inflate(R.layout.welcome_two, null));
		views.add(getLayoutInflater().inflate(R.layout.welcome_three,null));
		btnNext=(Button)views.get(2).findViewById(R.id.btn_next);
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sp.edit().putInt(AppConstants.VERSION,version).commit();
				ViewUtil.openActivity(LoginActivity.class, getContext(), true);
			}
		});
		pagerAdapter=new ViewPagerAdapter(views);
		viewpage.setAdapter(pagerAdapter);
		viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				radioGroup.check(radioGroup.getChildAt(position).getId());
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}
}


