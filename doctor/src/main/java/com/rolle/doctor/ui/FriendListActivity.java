package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.ContactBean;
import com.rolle.doctor.domain.Recommended;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.viewmodel.ContactQueryHandler;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 * 新朋友   通讯录
 */
public class FriendListActivity extends BaseActivity{
     @InjectView(R.id.rv_view) RecyclerView lvView;
    private List<User> data;
    private FriendListAdapater adapater;
    private UserModel userModel;
    private ContactQueryHandler handler;
    private List<ContactBean> contactBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycker);
        userModel=new UserModel(this);
        handler=new ContactQueryHandler(getContentResolver(), new ContactQueryHandler.HandleListener() {
                @Override
                public void setAdapter(List<ContactBean> list) {
                    contactBeans=list;
                    requestData();
                }
        });
        handler.queryList();
    }

    /****
     *
     */
    private void requestData(){
        userModel.requestNewFriendList(contactBeans, new SimpleResponseListener<List<Recommended.Item>>() {
            @Override
            public void requestSuccess(List<Recommended.Item> info, Response response) {

            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }
        });

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("通讯录好友");
        data=new ArrayList<>();
        adapater=new FriendListAdapater(this,data,lvView,FriendListAdapater.TYPE_FRIEND);
        requestData();
    }

}
