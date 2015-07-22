package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.widget.AlertDialogFragment;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.FamilytInfo;
import com.roller.medicine.info.HomeInfo;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class FamilyUpdateActivity extends BaseLoadingToolbarActivity {

	@InjectView(R.id.refresh)
	PullRefreshLayout refresh;
	@InjectView(R.id.rv_view)
	RecyclerView rv_view;

	@InjectView(R.id.iv_photo)
	 ImageView iv_photo;
	@InjectView(R.id.tv_name)
	 TextView tv_name;

	protected AlertDialogFragment dialog;
	private List<HomeInfo.Family> mData;
	private DataModel dataModel;
	private RecyclerAdapter<HomeInfo.Family> adapter;
	private int index=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_house_hold);
	}

	protected void initView(){
		super.initView();
		setBackActivity("更改户主");
		dataModel=new DataModel();
		mData=new ArrayList<>();
		adapter=new RecyclerAdapter(getContext(),mData,rv_view);
		refresh.setRefreshStyle(AppConstants.PULL_STYLE);
		refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				requesFamilyList();
			}
		});
		adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<HomeInfo.Family>() {
			@Override
			public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, final HomeInfo.Family family, final int position) {
				viewHolder.setText(R.id.tv_name, family.appellation);
				viewHolder.getView(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						index = position;
						StringBuilder builder=new StringBuilder("是否提升\"");
						builder.append(family.appellation).append("\"账号?");
						dialog.msg = builder.toString();
						dialog.show((getContext()).getSupportFragmentManager(), "dialog");
					}
				});
			}

			@Override
			public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
				return new RecyclerAdapter.ViewHolder(getLayoutInflater().inflate(R.layout.item_update_account, viewGroup, false));
			}

			@Override
			public int getItemCount() {
				return mData.size();
			}
		});
		ViewUtil.initRecyclerViewDecoration(rv_view, getContext(), adapter);
		refresh.setRefreshing(true);
		requesFamilyList();

		dialog=new AlertDialogFragment();

		dialog.setClickListener(new AlertDialogFragment.OnClickListener() {
			@Override
			public void onCancel() {
				//

			}

			@Override
			public void onConfirm() {
				//index; 更改
				HomeInfo.Family family = mData.get(index);
				updateFamilyGroup(family.groupId, family.id+"",index);
			}
		});

	}

	/***
	 * 获取家庭列表
	 */
	private void requesFamilyList(){
		refresh.setRefreshing(true);
		dataModel.getFamilyListByMap(new SimpleResponseListener<FamilytInfo>() {
			@Override
			public void requestSuccess(FamilytInfo info, Response response) {
				if (CommonUtil.notNull(info.list) && info.list.size() > 0) {
					HomeInfo.Family family = info.list.get(0);
					Util.loadPhoto(getContext(), family.headImage, iv_photo);
					tv_name.setText(family.appellation);
					info.list.remove(0);
					adapter.addItemAll(info.list);
				}

			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {

			}

			@Override
			public void requestView() {
				super.requestView();
				refresh.setRefreshing(false);
				adapter.checkEmpty();
			}
		});
	}


	/**
	 * 更改户主
	 * @param
	 */
	private void updateFamilyGroup(String groupId,String familyGroupId,int position){
		showLoading();
		dataModel.updateFamilyGroup(groupId, familyGroupId, new SimpleResponseListener<ResponseMessage>() {
			@Override
			public void requestSuccess(ResponseMessage info, Response response) {
				showMsg("提升成功");
				requesFamilyList();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapter.onDestroyReceiver();
	}

}
