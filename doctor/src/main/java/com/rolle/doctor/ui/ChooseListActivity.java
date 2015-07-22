package com.rolle.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.viewmodel.ListModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 *   城市   医科   职称
 */
public class ChooseListActivity extends BaseActivity{

    @InjectView(R.id.rv_view)RecyclerView rv_view;
    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    private RecyclerAdapter<CityResponse.Item> adapter;
    private ArrayList<CityResponse.Item> items;
    private UserModel userModel;
    private ListModel listModel;
    private User user;
    private CityResponse.Item VItem;
    private CityResponse.Item QItem;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycker);

    }
    /****加载数据***/
    private void requestData(){
        refresh.setRefreshing(true);
        switch (type){
            case 0:
                doVisitList();
                setBackActivity("选择省份");
                break;
            case 1:
                doCityList();
                setBackActivity("选择城市");
                break;
            case 2:
                setBackActivity("选择科室");
                doSectionList();
                break;
            case 3:
                doTitleList();
                setBackActivity("选择职称");
                break;
            case 4:
                doCityList();
                setBackActivity("选择区县");
                break;
        }
    }

    /*****
     * 省会
     */
    public void doVisitList(){
        listModel.requestCity("1", new SimpleResponseListener<List<CityResponse.Item>>() {
            @Override
            public void requestSuccess(List<CityResponse.Item> info, Response response) {
                adapter.addItemAll(info);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                msgLongShow("加载省份失败....");
            }

            @Override
            public void requestView() {
                refresh.setRefreshing(false);
            }
        });

    }

    /******
     * 科室
     */
    public void doSectionList(){
        listModel.requestTitle("44", new SimpleResponseListener<List<CityResponse.Item>>() {
            @Override
            public void requestSuccess(List<CityResponse.Item> info, Response response) {
                adapter.addItemAll(info);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }

            @Override
            public void requestView() {
                refresh.setRefreshing(false);
            }
        });
    }

    /*****
     * 职称
     */
    public void doTitleList() {
        listModel.requestTitle("65", new SimpleResponseListener<List<CityResponse.Item>>() {
            @Override
            public void requestSuccess(List<CityResponse.Item> info, Response response) {
                adapter.addItemAll(info);

            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }

            @Override
            public void requestView() {
                refresh.setRefreshing(false);
            }
        });
    }

    /*******
     * 城市  区县
     */
    public void doCityList(){
        String cityId=VItem.id;
        if (type==4){
            cityId=QItem.id;
        }
        listModel.requestCity(cityId, new SimpleResponseListener<List<CityResponse.Item>>() {
            @Override
            public void requestSuccess(List<CityResponse.Item> info, Response response) {
                adapter.addItemAll(info);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }

            @Override
            public void requestView() {
                refresh.setRefreshing(false);
            }
        });
    }



    @Override
    protected void initView() {
        super.initView();
        items=new ArrayList<>();
        userModel=new UserModel((BaseActivity)getContext());
        listModel=new ListModel(getContext());
        user=userModel.getLoginUser();
        type=getIntent().getIntExtra(AppConstants.TYPE, 0);

        adapter=new RecyclerAdapter(getContext(),items,rv_view);

        adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<CityResponse.Item>() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, CityResponse.Item item, int position) {
                viewHolder.setText(R.id.tv_name, item.name);
            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                final RecyclerAdapter.ViewHolder holder = new RecyclerAdapter.ViewHolder(
                        getLayoutInflater().inflate(R.layout.item_list_spinner, viewGroup, false)) {
                };
                return holder;
            }

            @Override
               public int getItemCount() {
                return items.size();
            }

        });
        ViewUtil.initRecyclerViewDecoration(rv_view, this, adapter);
        requestData();
        rv_view.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra(AppConstants.TYPE, type);
                intent.putExtra(AppConstants.POSITION, position);
                setResult(200, intent);
                User user = userModel.getLoginUser();
                switch (type) {
                    case 0:
                        //省会
                        type = 1;
                        VItem = items.get(position);
                        requestData();
                        return;
                    case 1:
                        CityResponse.Item item = items.get(position);
                        QItem = item;
                        user.workRegionId = item.id;
                        type = 4;
                        requestData();
                        return;
                    case 4:
                        CityResponse.Item item1 = items.get(position);
                        ///填写详细地址  UpdateAddressActivity
                        StringBuilder builder = new StringBuilder();
                        builder.append(VItem.name.equals("上海")?"":VItem.name).
                                append(QItem.name).
                                append(item1.name);
                        user.workRegion = builder.toString();
                        user.regionId = item1.id;
                        userModel.saveUser(user);
                        ViewUtil.openActivity(UpdateAddressActivity.class, getContext(), true);
                        return;
                    case 2:
                        user.doctorDetail.departmentsId = items.get(position).id;
                        user.doctorDetail.department = items.get(position).name;
                        user.setUpdateStatus();
                        break;
                    case 3:
                        user.doctorDetail.jobId = items.get(position).id;
                        user.doctorDetail.doctorTitle = items.get(position).name;
                        user.setUpdateStatus();
                        break;
                }
                userModel.saveUser(user);
                finish();
            }
        }));


        refresh.setRefreshStyle(AppConstants.PULL_STYLE);
        refresh.setOnRefreshListener(
                new PullRefreshLayout.OnRefreshListener() {
                                         @Override
                                         public void onRefresh () {
                                             requestData();
                                         }
                                     }

        );
    }

    @Override
    protected void onBackActivty() {
        if (type==1){
            type=0;
            requestData();
            return;
        }
        super.onBackActivty();
    }

    @Override
    protected void onDestroy() {
        adapter.onDestroyReceiver();
        super.onDestroy();
    }
}
