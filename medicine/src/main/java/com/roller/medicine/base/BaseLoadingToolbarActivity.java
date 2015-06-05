package com.roller.medicine.base;

import com.android.common.fragment.LoadingFragment;

public  class BaseLoadingToolbarActivity extends BaseToolbarActivity{
	protected LoadingFragment loadingFragment;
	protected void initView(){
		loadingFragment=LoadingFragment.newInstance();
		super.initView();
	}

	protected void showLoading() {
		loadingFragment.show(getSupportFragmentManager(),"loading");

	}

	protected void hideLoading() {
		loadingFragment.dismiss();
	}

}
