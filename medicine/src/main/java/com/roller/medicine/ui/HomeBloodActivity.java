package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.android.common.util.ViewUtil;
import com.baoyz.widget.PullRefreshLayout;
import com.roller.medicine.R;
import com.roller.medicine.adapter.PatientHListAdapater;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.info.BloodInfo;
import com.roller.medicine.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class HomeBloodActivity extends BaseToolbarActivity {
	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView recyclerView;
	private List<BloodInfo.Item> data;

	private PatientHListAdapater adapater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refresh_recycker);

	}

	@Override
	protected void initView() {
		super.initView();
		setBackActivity("血糖历史");
		refresh.setRefreshStyle(AppConstants.PULL_STYLE);
		data=new ArrayList<>();
		data.add(new BloodInfo.Item());
		data.add(new BloodInfo.Item());
		adapater=new PatientHListAdapater(this,data,refresh);
		adapater.family=getIntent().getParcelableExtra(AppConstants.ITEM);
		ViewUtil.initRecyclerViewDecoration(recyclerView,this,adapater);
	}

}


