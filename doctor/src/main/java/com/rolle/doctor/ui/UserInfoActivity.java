package com.rolle.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.UserDetialAdapater;
import com.rolle.doctor.domain.CityResponse;
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
                        ViewUtil.openActivity(UpdateAddressActivity.class, getContext());
                        break;
                    case 2:
                        ViewUtil.openActivity(UpdateHospitalActivity.class, getContext());
                        break;
                    case 3:
                        doSectionList();
                        break;
                    case 4:
                        doTitleList();
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
        lsData.add(new ItemInfo("工作地址", CommonUtil.isEmpty(user.jobAddress)?"无":user.jobAddress));
        lsData.add(new ItemInfo("所在医院",CommonUtil.isEmpty(user.hospitalName)?"无":user.hospitalName));
        lsData.add(new ItemInfo("医生职称", CommonUtil.isEmpty(user.doctorTitle) ? "无" : user.doctorTitle));
        lsData.add(new ItemInfo("所在科室", CommonUtil.isEmpty(user.department) ? "无" : user.department));
        lsData.add(new ItemInfo("专长", CommonUtil.isEmpty(user.specialty) ? "无" : user.specialty));
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

            CityResponse.Item item=null;
            /*switch (data.getIntExtra(Constants.TYPE,-1)){
                case 2:
                    if (CommonUtil.notNull(titleList)){
                        item=titleList.get(position);
                        user.jobId=item.id;
                        ItemInfo info=lsData.get(3);
                        info.desc=item.name;
                        lsData.set(3,info);
                        adapater.notifyDataSetChanged();
                    }
                    break;
                case 3:
                    if (CommonUtil.notNull(sectionList)){
                        item=sectionList.get(position);
                        user.typeId=item.id;
                        ItemInfo info=lsData.get(4);
                        info.desc=item.name;
                        lsData.set(4,info);
                        adapater.notifyDataSetChanged();
                    }
                    break;
            }*/
        }

    }


    public void doSectionList() {
        listModel.requestTitle("44", new ViewModel.ModelListener<List<CityResponse.Item>>() {
            @Override
            public void model(Response response, List<CityResponse.Item> items) {
                startListActivity((ArrayList)items,3);
            }

            @Override
            public void errorModel(ModelEnum modelEnum) {

            }

            @Override
            public void view() {

            }
        });
    }
    public void doTitleList() {
        listModel.requestTitle("65", new ViewModel.ModelListener<List<CityResponse.Item>>() {
            @Override
            public void model(Response response, List<CityResponse.Item> items) {

                startListActivity((ArrayList) items, 2);
            }

            @Override
            public void errorModel(ModelEnum modelEnum) {

            }

            @Override
            public void view() {

            }
        });
    }

    private void startListActivity(ArrayList<CityResponse.Item> list,int type){
        Intent intent=new Intent(getContext(),ChooseListActivity.class);
        intent.putExtra("type",type);
        intent.putParcelableArrayListExtra(com.rolle.doctor.util.Constants.LIST, list);
        startActivityForResult(intent, 200);
    }



}
