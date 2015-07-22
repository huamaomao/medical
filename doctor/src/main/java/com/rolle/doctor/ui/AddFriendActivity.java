package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.presenter.AddFriendPresenter;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 *
 */
public class AddFriendActivity extends BaseLoadingActivity {


    @InjectView(R.id.et_tel)
    EditText et_tel;

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


    @Override
    protected void initView() {
        super.initView();
        setBackActivity("添加朋友");
        loadingFragment.setRequestMessage();
    }

    @OnClick(R.id.ll_invite)
    void doInvite(){
        ViewUtil.openActivity(MyInviteCodeActivity.class, this);
    }

    @OnClick(R.id.ll_scanning)
    void doScanning(){
       ViewUtil.openActivity(CaptureActivity.class, this);
    }

    public void doAdd(){
        String tel=et_tel.getText().toString();
        if (CommonUtil.isEmpty(tel)){
            msgShow("请填写手机号...");
            return;
        }
        if (!CommonUtil.isMobileNO(tel)){
            msgShow("手机格式错误...");
            return;
        }
        showLoading();
        model.requestAddFriend(tel,null, new SimpleResponseListener<ResponseMessage>() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_add:
                doAdd();
                break;
        }

        return true;
    }

}
