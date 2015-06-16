package com.roller.medicine.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.adapter.TabHomeAdapater;
import com.roller.medicine.adapter.ViewPagerAdapter;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.info.HomeInfo;
import com.roller.medicine.ui.CreateBloodActivity;
import com.roller.medicine.ui.HomeActivity;
import com.roller.medicine.ui.HomeBloodActivity;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

public class TabHomeFragment extends BaseToolbarFragment{

	@InjectView(R.id.rv_view)
	RecyclerView rv_view;
	TabHomeAdapater recyclerAdapter;
	private List<Object> data;
	@InjectView(R.id.rl_item_0)
	RelativeLayout rl_viewpage;

	@InjectView(R.id.viewpage)
	ViewPager viewpage;

	@InjectView(R.id.rg_group)
	RadioGroup radioGroup;
	private DataModel model;
	private String date=null;
	private ViewPagerAdapter pagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.fragment_home_tab);
	}


	class  ViewHolder{
		View itemView;
		@InjectView(R.id.tv_name)
		TextView tv_name;
		@Optional
		@InjectView(R.id.tv_title)
		TextView tv_title;
		@InjectView(R.id.ibtn_add)
		ImageView ibtn_add;
		@Optional   @InjectView(R.id.ibtn_add_)
		ImageView ibtn_add_;
		int item=0;

		public ViewHolder(View view){
			this.itemView=view;
			ButterKnife.inject(this, view);
		}
		 @OnClick(R.id.ibtn_add)
		void doAddLeft(){
			openCreateBlood(item);
		}
		@Optional  @OnClick(R.id.ibtn_add_)
		void doAdd(){
			 openCreateBlood(++item);
		}

	}

	@Override
	protected void initView(View view, LayoutInflater inflater) {
		super.initView(view, inflater);
		model=new DataModel();
		data=new ArrayList<>();
		recyclerAdapter=new TabHomeAdapater(getActivity(),data);
		ViewUtil.initRecyclerViewDecoration(rv_view, getActivity(), recyclerAdapter);
		initAdd();
		requestData();

	}

	private void initAdd(){
		rl_viewpage.setVisibility(View.VISIBLE);
		List<View> views=new ArrayList<>();
		ViewHolder viewHolder=new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_blood_single,null,false));
		viewHolder.item=0;
		ViewHolder viewHolder1=new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_blood_various,null,false));
		viewHolder.item=1;
		ViewHolder viewHolder2=new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_blood_various,null,false));
		viewHolder2.itemView.setBackgroundResource(R.drawable.icon_blood_noon);
		viewHolder2.tv_name.setText("午餐前");
		viewHolder2.tv_title.setText("午餐后");
		viewHolder2.item=3;
		ViewHolder viewHolder3=new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_blood_various,null,false));
		viewHolder3.itemView.setBackgroundResource(R.drawable.icon_blood_evening);
		viewHolder3.tv_name.setText("晚餐前");
		viewHolder3.tv_title.setText("晚餐后");
		viewHolder3.item=5;
		ViewHolder viewHolder4=new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_blood_single,null,false));
		viewHolder4.itemView.setBackgroundResource(R.drawable.icon_blood_bedtime);
		viewHolder4.tv_name.setText("睡前");
		viewHolder4.item=7;
		views.add(viewHolder.itemView);
		views.add(viewHolder1.itemView);
		views.add(viewHolder2.itemView);
		views.add(viewHolder3.itemView);
		views.add(viewHolder4.itemView);
		pagerAdapter=new ViewPagerAdapter(views);
		viewpage.setAdapter(pagerAdapter);
		viewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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



	public void requestData(){
		model.requestHomeData(date, new SimpleResponseListener<HomeInfo>() {
			@Override
			public void requestSuccess(HomeInfo info, Response response) {
				rl_viewpage.setVisibility(View.GONE);
				data.add(null);
				data.add(null);
				recyclerAdapter.setHomeInfo(info);
			}

			@Override
			public void requestError(com.litesuits.http.exception.HttpException e, ResponseMessage info) {
				// new AppHttpExceptionHandler().via(getActivity()).handleException(e,info);
			}
		});

	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_home, menu);

	}



	public void openCreateBlood(int index){
		Bundle bundle=new Bundle();
		if (CommonUtil.notNull(recyclerAdapter.getHomeInfo())){
			bundle.putParcelableArrayList(Constants.ITEM,recyclerAdapter.getHomeInfo().familyList);
		}
		bundle.putInt(Constants.TYPE, index);
		HomeActivity activity =(HomeActivity)getActivity();
		if (CommonUtil.notNull(activity)){
			bundle.putString(Constants.DATA_DATE, activity.getTitleDate());
		}
		ViewUtil.openActivity(CreateBloodActivity.class,bundle, getActivity(), ActivityModel.ACTIVITY_MODEL_2);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.toolbar_add:
				openCreateBlood(0);
				return true;
			case R.id.toolbar_seach:
				ViewUtil.openActivity(HomeBloodActivity.class,getActivity());
				return true;

		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onStop() {
		super.onStop();
	}

}
