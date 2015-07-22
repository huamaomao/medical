package com.roller.medicine.base;
import android.os.Bundle;

import com.android.common.fragment.LoadingFragment;

public  class BaseLoadingToolbarActivity extends BaseToolbarActivity{
	protected LoadingFragment loadingFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadingFragment=LoadingFragment.newInstance();
	}

	protected void initView(){

		super.initView();
	}

	protected void showLoading() {
		loadingFragment.show(getSupportFragmentManager(),"loading");

	}

	protected void hideLoading() {
		loadingFragment.dismiss();
	}

}
