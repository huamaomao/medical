package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.android.common.adapter.QuickAdapter;
import com.android.common.util.CommonUtil;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.ViewHolderHelp;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.UserDetialAdapater;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class UserInfoActivity extends BaseActivity{

    @InjectView(R.id.rv_view)
    RecyclerView rvView;
    private List<ItemInfo> lsData;

    private UserDetialAdapater adapater;
    private UserModel userModel;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("资料");
        userModel=new UserModel(getContext());
        user=userModel.getLoginUser();
        lsData=new ArrayList<>();
        lsData.add(new ItemInfo());
        lsData.add(new ItemInfo("工作地址", CommonUtil.isEmpty(user.jobAddress)?"无":user.jobAddress));
        lsData.add(new ItemInfo("所在医院",CommonUtil.isEmpty(user.hospitalName)?"无":user.hospitalName));
        lsData.add(new ItemInfo("医生职称", CommonUtil.isEmpty(user.doctorTitle) ? "无" : user.doctorTitle));
        lsData.add(new ItemInfo("所在科室", CommonUtil.isEmpty(user.department) ? "无" : user.department));
        lsData.add(new ItemInfo("专长", CommonUtil.isEmpty(user.specialty) ? "无" : user.specialty));
        adapater=new UserDetialAdapater(this,lsData);
        adapater.setUserDetail(user);
        ViewUtil.initRecyclerView(rvView, getContext(), adapater);


    }
}
