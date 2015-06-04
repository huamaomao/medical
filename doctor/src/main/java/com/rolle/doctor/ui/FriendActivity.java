package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.ContactBean;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.Recommended;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.viewmodel.ContactQueryHandler;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 * 新朋友
 */
public class FriendActivity extends BaseActivity{
     @InjectView(R.id.rv_view) RecyclerView lvView;
    private List<User> data;
    private FriendListAdapater adapater;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        userModel=new UserModel(this);

    }

    /****
     *
     */
    private void requestData(){
        userModel.requestNewFriendList(null, new ViewModel.ModelListener<List<Recommended.Item>>() {
            @Override
            public void model(Response response, List<Recommended.Item> items) {
                Log.d(response.getString());
            }

            @Override
            public void errorModel(HttpException e, Response response) {
                
            }

            @Override
            public void view() {

            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("新朋友");
        data=new ArrayList<>();
        adapater=new FriendListAdapater(this,data,lvView,FriendListAdapater.TYPE_FRIEND);
        requestData();
    }

    @OnClick(R.id.ll_item_2)
    void onFriendList(){
        ViewUtil.openActivity(FriendListActivity.class, FriendActivity.this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            adapater.onDestroyReceiver();
        }catch (Exception e){}
    }
}
