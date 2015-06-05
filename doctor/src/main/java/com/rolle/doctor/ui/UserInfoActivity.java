package com.rolle.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.UserDetialAdapater;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.ListModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 用户信息
 */
public class UserInfoActivity extends BaseActivity{

    @InjectView(R.id.rv_view)
    RecyclerView rvView;
    private List<ItemInfo> lsData;
    private UserDetialAdapater adapater;
    private UserModel userModel;
    private User user;
    private ListModel listModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
    }

    @Override
    protected void initView() {
        super.initView();
        listModel=new ListModel(getContext());
        setBackActivity("资料");
        userModel=new UserModel(getContext());
        lsData=new ArrayList<>();
        adapater=new UserDetialAdapater(this,lsData);
        ViewUtil.initRecyclerView(rvView, getContext(), adapater);
        rvView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 1:
                        //UpdateAddressActivity
                       startListActivity(0);
                        break;
                    case 2:
                        ViewUtil.openActivity(UpdateHospitalActivity.class, getContext());
                        break;
                    case 3:
                        //职称
                        startListActivity(3);
                        break;
                    case 4:
                        startListActivity(2);
                        break;
                    case 5:
                        ViewUtil.openActivity(UpdateSpecialityActivity.class, getContext());
                        break;
                }
            }
        }));
        loadData();
    }

    private void loadData(){
        user=userModel.getLoginUser();
        lsData.clear();
        lsData.add(new ItemInfo());
        lsData.add(new ItemInfo("工作地址",CommonUtil.initTextNull(user.doctorDetail.workAddress)));
        lsData.add(new ItemInfo("所在医院",CommonUtil.initTextNull(user.doctorDetail.hospitalName)));
        lsData.add(new ItemInfo("医生职称",CommonUtil.initTextNull(user.doctorDetail.doctorTitle)));
        lsData.add(new ItemInfo("所在科室",CommonUtil.initTextNull(user.doctorDetail.department)));
        lsData.add(new ItemInfo("专长", CommonUtil.initTextNull(user.doctorDetail.speciality)));
        adapater.setUserDetail(user);
        adapater.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==200){
            int position=data.getIntExtra(Constants.POSITION,-1);
        }

    }

    private void startListActivity(int type){
        Intent intent=new Intent(getContext(),ChooseListActivity.class);
        intent.putExtra("type",type);
        startActivityForResult(intent, 200);
    }



}
