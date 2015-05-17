package com.rolle.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ViewModel;
import com.android.common.widget.InputMethodLinearLayout;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.presenter.ChooseListPresenter;
import com.rolle.doctor.presenter.LoginPresenter;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.ListModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class ChooseListActivity extends BaseLoadingActivity{

    @InjectView(R.id.rv_view)RecyclerView rv_view;
    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    private BaseRecyclerAdapter adapter;
    private ArrayList<CityResponse.Item> items;
    private UserModel userModel;
    private ListModel listModel;
    private User user;
    private CityResponse.Item VItem;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycker);

    }
    /****加载数据***/
    private void requestData(){
        showLoading();
        switch (type){
            case 0:
                doVisitList();
                setBackActivity("选择省份");
                break;
            case 1:
                items.clear();
                doCityList();
                setBackActivity("选择城市");
                break;
            case 2:
                setBackActivity("选择科室");
                doTitleList();
                break;
            case 3:
                doSectionList();
                setBackActivity("选择职称");
                break;
        }
    }

    /*****
     * 省会
     */
    public void doVisitList(){
        listModel.requestCity("1",new ViewModel.ModelListener<List<CityResponse.Item>>() {
            @Override
            public void model(Response response, List<CityResponse.Item> items) {
                Log.d("model"+items.size());
                adapter.addItemAll(items);
            }

            @Override
            public void errorModel(HttpException e, Response response) {
                msgLongShow("加载省份失败....");
            }

            @Override
            public void view() {
                hideLoading();
            }
        });
    }

    /******
     * 科室
     */
    public void doSectionList(){
        listModel.requestTitle("44", new ViewModel.ModelListener<List<CityResponse.Item>>() {
            @Override
            public void model(Response response, List<CityResponse.Item> items) {
                adapter.addItemAll(items);
            }

            @Override
            public void errorModel(HttpException e, Response response) {

            }

            @Override
            public void view() {
                hideLoading();
            }
        });
    }

    /*****
     * 职称
     */
    public void doTitleList() {
        listModel.requestTitle("65", new ViewModel.ModelListener<List<CityResponse.Item>>() {
            @Override
            public void model(Response response, List<CityResponse.Item> items) {
                adapter.addItemAll(items);
            }

            @Override
            public void errorModel(HttpException e, Response response) {

            }

            @Override
            public void view() {
                hideLoading();
            }
        });
    }

    /*******
     * 城市
     */
    public void doCityList(){
        if (CommonUtil.isNull(VItem)){
            setBackActivity("选择省份");
            type=0;
            requestData();
            return;
        }
        listModel.requestCity(VItem.id,new ViewModel.ModelListener<List<CityResponse.Item>>() {
            @Override
            public void model(Response response, List<CityResponse.Item> items) {
                adapter.addItemAll(items);
            }

            @Override
            public void errorModel(HttpException e, Response response) {

            }

            @Override
            public void view() {
                hideLoading();
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
        type=getIntent().getIntExtra(Constants.TYPE,0);
        adapter=new BaseRecyclerAdapter(items);
        adapter.implementRecyclerAdapterMethods(new BaseRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(BaseRecyclerAdapter.ViewHolder viewHolder, int i) {
                viewHolder.setText(R.id.tv_name,items.get(i).name);
            }

            @Override
            public BaseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                final  BaseRecyclerAdapter.ViewHolder holder=new BaseRecyclerAdapter.ViewHolder(getLayoutInflater().inflate(R.layout.item_list_spinner,viewGroup,false)){};
                return holder;
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        });
        ViewUtil.initRecyclerView(rv_view, this, adapter);
        loadingFragment.setMessage("正在加载数据...");
        requestData();
        adapter.setOnClickEvent(new BaseRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent();
                intent.putExtra(Constants.TYPE, type);
                intent.putExtra(Constants.POSITION, position);
                setResult(200, intent);
                User user = userModel.getLoginUser();
                switch (type) {
                    case 0:
                        //省会
                        type = 1;
                        VItem = items.get(position);
                        requestData();
                        break;
                    case 1:
                        CityResponse.Item item = items.get(position);
                        user.workRegionId = item.id;
                        //填写详细地址  UpdateAddressActivity
                        StringBuilder builder=new StringBuilder();
                        builder.append(VItem.name).append(item.name);
                        user.workRegion=builder.toString();
                        userModel.saveUser(user);
                        ViewUtil.openActivity(UpdateAddressActivity.class,getContext(),true);
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
                if (type >1) {
                    userModel.saveUser(user);
                    finish();
                }
            }
        });

        refresh.setRefreshStyle(Constants.PULL_STYLE);

        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
        refresh.setRefreshing(false);
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
}
