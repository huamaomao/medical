package com.roller.medicine.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import com.android.common.adapter.CommonFragmentAdapter;
import com.android.common.util.Log;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class TabCommentFragment extends BaseToolbarFragment{

	@InjectView(R.id.viewpage)
	ViewPager mViewPager;
	@InjectView(R.id.tabs)
	TabLayout tabLayout;

	private List<Fragment>  views;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.fragment_comment);
	}

	@Override
	protected void initView(View view, LayoutInflater inflater) {
		super.initView(view, inflater);
		setTitle("健康社区");
		tabLayout.addTab(tabLayout.newTab().setText("热点"));
		tabLayout.addTab(tabLayout.newTab().setText("饮食"));
		tabLayout.addTab(tabLayout.newTab().setText("运动"));
		tabLayout.addTab(tabLayout.newTab().setText("慢病"));
		tabLayout.addTab(tabLayout.newTab().setText("趣闻"));
		views=new ArrayList();
		Bundle bundle=new Bundle();
		bundle.putString(AppConstants.DATA,"1");
		views.add(Comment1Fragment.newInstantiate(bundle));
		bundle=new Bundle();
		bundle.putString(AppConstants.DATA,"2");
		views.add(Comment1Fragment.newInstantiate(bundle));
		bundle=new Bundle();
		bundle.putString(AppConstants.DATA,"3");
		views.add(Comment1Fragment.newInstantiate(bundle));
		bundle=new Bundle();
		bundle.putString(AppConstants.DATA,"4");
		views.add(Comment1Fragment.newInstantiate(bundle));
		bundle=new Bundle();
		bundle.putString(AppConstants.DATA,"5");
		views.add(Comment1Fragment.newInstantiate(bundle));
		CommonFragmentAdapter pagerAdapter=new CommonFragmentAdapter(getChildFragmentManager(),views);
		mViewPager.setAdapter(pagerAdapter);
		tabLayout.setupWithViewPager(mViewPager);
		//Log.d("initView:" + mViewPager.getCurrentItem());
		//mViewPager.setCurrentItem(0);
		//pagerAdapter.notifyDataSetChanged();


	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
