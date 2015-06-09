package com.rolle.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.UserInfoAdapater;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.presenter.RegisterInfoPresenter;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/****
 * 基本资料
 */
public class RegisterInfoActivity extends BaseLoadingActivity{

    @InjectView(R.id.et_name)
    EditText et_name;
     @InjectView(R.id.et_hospital)
    EditText et_hospital;
    private UserModel userModel;
    @InjectView(R.id.rv_view)RecyclerView  rv_view;
    private UserInfoAdapater adpater;
    private List<ItemInfo> lsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        userModel=new UserModel(getContext());
    }


    @Override
    protected void initView() {
        super.initView();
        setBackActivity("基本资料");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_view.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv_view.setLayoutManager(layoutManager);
        lsData=new ArrayList<>();
        lsData.add(new ItemInfo("工作地址","请选择省份城市"));
        lsData.add(new ItemInfo("  科室","请选择科室"));
        adpater=new UserInfoAdapater(this,lsData);
        rv_view.setAdapter(adpater);
        rv_view.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        startListActivity(0);
                        break;
                    case 1:
                        startListActivity(2);
                        break;
                }
            }
        }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==200){
            ItemInfo city=lsData.get(0);
            User user=userModel.getLoginUser();
            if (CommonUtil.notEmpty(user.workRegion)){
                city.desc=user.workRegion;
                adpater.notifyItemChanged(0);
            }
            ItemInfo department=lsData.get(1);
            if (CommonUtil.notEmpty(user.doctorDetail.department)){
                department.desc=user.doctorDetail.department;
                adpater.notifyItemChanged(1);
            }
        }

    }

    public void doNext(){
        String name=et_name.getText().toString();
        String hospital=et_hospital.getText().toString();
        User user=userModel.getLoginUser();
        String city=user.regionId;
        String title=user.doctorDetail.jobId;
        if (CommonUtil.isEmpty(name)){
            msgShow("请输入真实姓名");
            return;
        }
        if (CommonUtil.isEmpty(hospital)){
            msgShow("请输入工作的医院");
            return;
        }
        if (CommonUtil.isNull(city)){
            msgShow("请选择省份");
            return;
        }
        if (CommonUtil.isNull(title)){
            msgShow("请选择科室");
            return;
        }
        user.nickname=name;
        user.userName=name;
        user.doctorDetail.hospitalName=hospital;
        userModel.db.save(user);
        showLoading();
        userModel.requestUpdateUser(user, new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                ViewUtil.startTopActivity(MainActivity.class, getContext());
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                new AppHttpExceptionHandler().via(getContext()).handleException(e,info);
            }

            @Override
            public void requestView() {
                hideLoading();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.toolbar_register:
                doNext();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void startListActivity(int type){
       Intent intent=new Intent(getContext(),ChooseListActivity.class);
        intent.putExtra("type",type);
       startActivityForResult(intent,200);
   }


}
