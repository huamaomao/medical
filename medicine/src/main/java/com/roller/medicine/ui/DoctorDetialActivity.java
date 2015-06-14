package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.adapter.DoctorCommentAdapater;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.DoctorDetialInfo;
import com.roller.medicine.info.RecommendedInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
/**
 * Created by Hua_ on 2015/3/27.
 */
public class DoctorDetialActivity extends BaseLoadingToolbarActivity{

    @InjectView(R.id.rv_view)
    RecyclerView recyclerView;
    private DoctorCommentAdapater adapater;
    private List<RecommendedInfo.Item> data;
    private DataModel userModel;
    private UserInfo user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detial);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("详细资料");
        userModel=new DataModel();
        user=getIntent().getParcelableExtra(Constants.ITEM);
        if (CommonUtil.isNull(user)){
            finish();
        }
        data=new ArrayList<>();
        data.add(new RecommendedInfo.Item());
        adapater=new DoctorCommentAdapater(data,this,user);
        ViewUtil.initRecyclerView(recyclerView, this, adapater);
        doDoctor();

    }
    @OnClick(R.id.iv_send)
    void toMessageActivity(){
        Bundle bundle=new Bundle();
        bundle.putParcelable(Constants.ITEM, user);
        ViewUtil.openActivity(MessageActivity.class, bundle, this, ActivityModel.ACTIVITY_MODEL_1);
    }


    public void doDoctor(){
        userModel.requestDoctorDetail(user.id + "", new SimpleResponseListener<DoctorDetialInfo>() {
            @Override
            public void requestSuccess(DoctorDetialInfo info, Response response) {
                if (CommonUtil.notNull(info.user)) {
                    adapater.setUserInfo(info.user);
                }
                if (CommonUtil.notNull(info.list)) {
                    data.addAll(info.list);
                }
                adapater.notifyDataSetChanged();
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_interest, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_add:
                doAddFriend();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void doAddFriend() {
        showLoading();
        userModel.requestAddFriendId(user.id + "", user.noteName, new SimpleResponseListener() {
            @Override
            public void requestSuccess(Object info, Response response) {
                showLongMsg("关注成功");
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                new AppHttpExceptionHandler().via(getCurrentFocus()).handleException(e, info);
            }
        });
    }


}
