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
import com.android.common.util.CommonUtil;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.Log;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.UserInfoAdapater;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.presenter.RegisterInfoPresenter;
import com.rolle.doctor.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/****
 * 基本资料
 */
public class RegisterInfoActivity extends BaseLoadingActivity implements RegisterInfoPresenter.IRegisterView{

    @InjectView(R.id.et_name)
    EditText et_name;
     @InjectView(R.id.et_hospital)
    EditText et_hospital;

    @InjectView(R.id.rv_view)RecyclerView  rv_view;
    private RegisterInfoPresenter presenter;
    private UserInfoAdapater adpater;
    private List<ItemInfo> lsData;
    private ArrayList<CityResponse.Item> visitList;
    private ArrayList<CityResponse.Item> cityList;
    private ArrayList<CityResponse.Item> titleList;
    private CityResponse.Item visit;
    private CityResponse.Item city;
    private CityResponse.Item titleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        presenter=new RegisterInfoPresenter(this);
        visitList=new ArrayList<>();
        cityList=new ArrayList<>();
        titleList=new ArrayList<>();
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
        lsData.add(new ItemInfo("科室","请选择科室"));
        adpater=new UserInfoAdapater(this,lsData);
        rv_view.setAdapter(adpater);
        rv_view.addOnItemTouchListener(new RecyclerItemClickListener(this,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                doChoose(position);
            }
        }));

    }


    private void doChoose(int type){
        switch (type){
            case 0:
                presenter.doVisitList();
                presenter.doCityList();
                break;
            case 1:
                presenter.doTitleList();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==200){
            int position=data.getIntExtra(Constants.POSITION,0);
            switch (data.getIntExtra(Constants.TYPE,-1)){
                   case 0:
                       visit=visitList.get(position);
                       ItemInfo info=lsData.get(0);
                       info.desc=visit.name;
                       lsData.set(0,info);
                       adpater.notifyItemChanged(0);
                       presenter.doCityList();
                       break;
                    case 1:
                        city=cityList.get(position);
                        ItemInfo info1=lsData.get(1);
                        info1.desc=city.name;
                        lsData.set(1,info1);
                        adpater.notifyItemChanged(1);
                        break;
                    case 2:
                        titleItem=titleList.get(position);
                        ItemInfo info2=lsData.get(2);
                        info2.desc=titleItem.name;
                        lsData.set(2,info2);
                        adpater.notifyItemChanged(2);
                        break;
            }
        }

    }

    public void doNext(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.toolbar_register:
                presenter.doNext();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public String getName() {
        return et_name.getText().toString();
    }

    @Override
    public CityResponse.Item getCity() {
        return city;
    }

    @Override
    public CityResponse.Item getVisit() {
       return visit;
    }

    @Override
    public String getHospital() {
        return et_hospital.getText().toString();
    }

    @Override
    public CityResponse.Item getTitleItem() {
        return titleItem;
    }

    @Override
    public void setVisitList(ArrayList<CityResponse.Item> list) {
        visitList.clear();
        visitList.addAll(list);
        startListActivity(list,0);
    }

    @Override
    public void setCityList(ArrayList<CityResponse.Item> list) {
        cityList.clear();
        cityList.addAll(list);
        startListActivity(list,1);
    }

    @Override
    public void setTitleList(ArrayList<CityResponse.Item> list) {
        titleList.clear();
        titleList.addAll(list);
        startListActivity(list,2);
    }

    private void startListActivity(ArrayList<CityResponse.Item> list,int type){
       Intent intent=new Intent(getContext(),ChooseListActivity.class);
       intent.putExtra("type",type);
       intent.putParcelableArrayListExtra(com.rolle.doctor.util.Constants.LIST,list);
       startActivityForResult(intent,200);
   }


}
