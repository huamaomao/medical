package com.roller.medicine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.QuickAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewHolderHelp;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.adapter.PublicViewAdapter;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.customview.listview.HorizontalListView;
import com.roller.medicine.info.HomeInfo;
import com.roller.medicine.info.MyHomeInfo;
import com.roller.medicine.info.MyItemInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.ui.FamilyAddActivity;
import com.roller.medicine.ui.FamilyRemoveActivity;
import com.roller.medicine.ui.FamilyUpdateActivity;
import com.roller.medicine.ui.MyHomeActivity;
import com.roller.medicine.ui.NoticeActivity;
import com.roller.medicine.ui.SettingActivity;
import com.roller.medicine.ui.UserInfoActivity;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class TabMyFragment extends BaseToolbarFragment{

	@InjectView(R.id.iv_photo)
	ImageView iv_photo;
	@InjectView(R.id.tv_name)
	TextView tv_name;
	@InjectView(R.id.tv_jianjie)
	TextView tv_jianjie;
	@InjectView(R.id.tv_item_1)
	TextView tv_comment;
	@InjectView(R.id.tv_item_2)
	TextView tv_love;
	@InjectView(R.id.tv_item_3)
	TextView tv_concern;
	@InjectView(R.id.tv_item_4)
	TextView tv_fans;
	@InjectView(R.id.lv_list)
	HorizontalListView lv_list;

	private View view;
	private DataModel dataModel;
	private UserInfo userInfo;
	private MyHomeInfo homeInfo;

	private List<HomeInfo.Family> mdata;
	private QuickAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayoutId(R.layout.fragment_my_tab);
	}

	@Override
	protected void initView(View view, LayoutInflater inflater) {
		super.initView(view, inflater);
		setTitle("我的");
		dataModel=new DataModel();
		userInfo=dataModel.getLoginUser();
		tv_name.setText(userInfo.nickname);
		if (CommonUtil.notEmpty(userInfo.intro))
			tv_jianjie.setText(userInfo.intro);
		loadHome();
		Picasso.with(getActivity()).load("http://rolle.cn:8080/rolle/upload/20150629/20150629164322033.jpg").
				transform(new CircleTransform()).placeholder(R.drawable.icon_default).into(iv_photo);
		//lv_list
		mdata=new ArrayList();

		adapter=new QuickAdapter<HomeInfo.Family>(getActivity(),R.layout.list_item_grid_family,mdata) {
			@Override
			protected void convert(ViewHolderHelp viewHolderHelp, HomeInfo.Family food) {
				viewHolderHelp.setText(R.id.tv_item_0,food.appellation);
				Util.loadPhoto(getActivity(),DataModel.getImageUrl(food.headImage),(ImageView) viewHolderHelp.getView(R.id.iv_photo));
			}
		};
		lv_list.setAdapter(adapter);
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_my, menu);
	}

	private void loadHome(){
		dataModel.requestUserHome(null, new SimpleResponseListener<MyHomeInfo>() {
			@Override
			public void requestSuccess(MyHomeInfo info, Response response) {
				homeInfo=info;
				tv_name.setText(info.nickname);
				tv_jianjie.setText(info.intro);
				Util.loadPhoto(getActivity(), DataModel.getImageUrl(info.headImage), iv_photo);
				tv_comment.setText(CommonUtil.initTextValue(info.replyCount));
				tv_love.setText(CommonUtil.initTextValue(info.praiseCount));
				tv_fans.setText(CommonUtil.initTextValue(info.fansCount));
				tv_concern.setText(CommonUtil.initTextValue(info.attentCount));
				mdata.clear();
				if (CommonUtil.notNull(info.list)){
					adapter.addAll(info.list);
				}
			}

			@Override
			public void requestError(HttpException e, ResponseMessage info) {

			}
		});
	}


	@OnClick({R.id.rl_item_0,R.id.rl_item_1,R.id.rl_item_2,R.id.rl_item_3,R.id.ll_item_0,R.id.ll_item_1,R.id.ll_item_2,R.id.ll_item_3})
	void  doItemClick(View view){
		switch (view.getId()){
			case R.id.rl_item_0:
			case R.id.ll_item_0:
			case R.id.ll_item_1:
			case R.id.ll_item_2:
			case R.id.ll_item_3:
				setLastClickTime();
				ViewUtil.openActivity(MyHomeActivity.class,getActivity());
				break;
			case R.id.rl_item_1://新建
				setLastClickTime();
				ViewUtil.openActivity(FamilyAddActivity.class,getActivity());
				break;
			case R.id.rl_item_2://移除
				setLastClickTime();
				ViewUtil.openActivity(FamilyRemoveActivity.class,getActivity());
				break;
			case R.id.rl_item_3://更改
				setLastClickTime();
				ViewUtil.openActivity(FamilyUpdateActivity.class,getActivity());
				break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.toolbar_next:
				setLastClickTime();
				ViewUtil.openActivity(NoticeActivity.class,getActivity());
				return true;
			case R.id.toolbar_setting:
				setLastClickTime();
				ViewUtil.openActivity(SettingActivity.class,getActivity());
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
