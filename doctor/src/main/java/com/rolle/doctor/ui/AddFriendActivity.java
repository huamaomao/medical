package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.MotionEvent;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.OnClick;

/**
 *
 */
public class AddFriendActivity extends BaseLoadingActivity {



    private UserModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        model=new UserModel(getContext());

    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        ViewUtil.onHideSoftInput(this,getCurrentFocus(),event);
        return super.onTouchEvent(event);
    }

    @OnClick(R.id.tv_code)
    void doSeachUser(){
        if (CommonUtil.isFastClick())return;
        ViewUtil.openActivity(SeachUserActivity.class,getContext());
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("添加朋友");
        loadingFragment.setRequestMessage();
    }

    @OnClick(R.id.ll_invite)
    void doInvite(){
        if (CommonUtil.isFastClick())return;
        ViewUtil.openActivity(MyInviteCodeActivity.class, this);
    }

    @OnClick(R.id.ll_scanning)
    void doScanning(){
        if (CommonUtil.isFastClick())return;
       ViewUtil.openActivity(CaptureActivity.class, this);
    }

    public void doAdd(){
        showLoading();
        model.requestAddFriend(null,null, new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                msgShow("添加成功...");
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



}
